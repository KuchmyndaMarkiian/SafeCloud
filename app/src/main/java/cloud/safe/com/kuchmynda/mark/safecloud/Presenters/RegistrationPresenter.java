package cloud.safe.com.kuchmynda.mark.safecloud.Presenters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;

import cloud.safe.com.kuchmynda.mark.safecloud.Common.ApiConnection;
import cloud.safe.com.kuchmynda.mark.safecloud.Common.CommonData;
import cloud.safe.com.kuchmynda.mark.safecloud.Infrastructure.Helpers.DialogInitializator;
import cloud.safe.com.kuchmynda.mark.safecloud.Infrastructure.Services.AuthorizationService;
import cloud.safe.com.kuchmynda.mark.safecloud.Infrastructure.Token;
import cloud.safe.com.kuchmynda.mark.safecloud.Infrastructure.UserAccount;
import cloud.safe.com.kuchmynda.mark.safecloud.Models.Authorization.LoginModel;
import cloud.safe.com.kuchmynda.mark.safecloud.Views.Activities.NavigationDrawerActivity;
import cloud.safe.com.kuchmynda.mark.safecloud.Views.Fragments.GalleryFragment;
import cloud.safe.com.kuchmynda.mark.safecloud.Views.Fragments.RegisterFragment;
import cloud.safe.com.kuchmynda.mark.safecloud.Models.Authorization.RegisterModel;

/**
 * Created by MARKAN on 15.09.2017.
 */

public class RegistrationPresenter extends PresenterBase<RegisterFragment> {
    RegisterModel registerModel;

    @Override
    protected void execute() {
    }

    @Override
    protected void initModel() {
        if (registerModel == null) {
            registerModel = new RegisterModel();
        }
    }

    @Override
    protected void errorHandler(String message) {
        Toast.makeText(view.getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public void setLogin(String text) {
        initModel();
        registerModel.setEmail(text);
    }

    public void setPassword(String text) {
        initModel();
        registerModel.setPassword(text);
    }

    public void setFirstName(String text) {
        initModel();
        registerModel.setFirstName(text);
    }

    public void setLastName(String text) {
        initModel();
        registerModel.setLastName(text);
    }

    public void setConfirmPassword(String text) {
        initModel();
        registerModel.setConfirmPassword(text);
    }

    public void signUp() {
        if (registerModel == null ||
                TextUtils.isEmpty(registerModel.getFirstName()) ||
                TextUtils.isEmpty(registerModel.getLastName()) ||
                TextUtils.isEmpty(registerModel.getEmail()) ||
                TextUtils.isEmpty(registerModel.getPassword()) ||
                TextUtils.isEmpty(registerModel.getConfirmPassword()) ||
                (registerModel.getAvatar() == null || registerModel.getAvatar().length <= 0)
                ) {
            errorHandler("Empty data or picture isn`t selected");
        }
        else if(!registerModel.getPassword().contentEquals(registerModel.getConfirmPassword())){
            errorHandler("Password and confirm password aren`t identical");
        }
        else {
            RegisterAsyncTask registerAsyncTask=new RegisterAsyncTask(view.getActivity());
            registerAsyncTask.execute(registerModel);
        }
    }

    public void setAvatar(byte[] avatar) {
        initModel();
        registerModel.setAvatar(avatar);
    }

    class RegisterAsyncTask extends AsyncTask<RegisterModel,Void,Token>{
        private final Activity activity;
        AuthorizationService service;

        RegisterAsyncTask(Activity activity) {
            this.activity = activity;
        }

        Dialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new Dialog(activity);
            DialogInitializator.initializeLoadingDialog(dialog);
            dialog.show();
        }

        @Override
        protected void onPostExecute(Token token) {
            super.onPostExecute(token);
            dialog.dismiss();
            if (token == null) {
                errorHandler(service.getError());
            } else {
                UserAccount.getCurrentAccount().setToken(token);
                Intent intent=new Intent(activity, NavigationDrawerActivity.class);
                intent.putExtra(CommonData.FRAGMENT_EXTRA, GalleryFragment.ID);
                activity.startActivity(intent);
                activity.finish();
            }
        }

        @Override
        protected Token doInBackground(RegisterModel... params) {
            service = new AuthorizationService(ApiConnection.ServerAdress);
            if(service.signUp(ApiConnection.RegisterAdress, params[0])) {
                LoginModel loginModel=new LoginModel();
                loginModel.setEmail(registerModel.getEmail());
                loginModel.setPassword(registerModel.getPassword());
                if (service.signIn(ApiConnection.LoginAdress, loginModel)) {
                    Gson gson = new Gson();
                    String body = service.getLastResponseBody();
                    Token token = gson.fromJson(body, Token.class);
                    return token;
                }
            }
            return null;
        }
    }

}
