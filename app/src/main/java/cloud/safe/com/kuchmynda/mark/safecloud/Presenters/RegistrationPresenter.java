package cloud.safe.com.kuchmynda.mark.safecloud.Presenters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cloud.safe.com.kuchmynda.mark.safecloud.Common.ApiConnection;
import cloud.safe.com.kuchmynda.mark.safecloud.Common.CommonData;
import cloud.safe.com.kuchmynda.mark.safecloud.Infrastructure.Helpers.DialogInitializator;
import cloud.safe.com.kuchmynda.mark.safecloud.Infrastructure.Services.SafeCloudServices.AuthorizationService;
import cloud.safe.com.kuchmynda.mark.safecloud.Infrastructure.Token;
import cloud.safe.com.kuchmynda.mark.safecloud.Infrastructure.UserAccount;
import cloud.safe.com.kuchmynda.mark.safecloud.Models.Authorization.LoginModel;
import cloud.safe.com.kuchmynda.mark.safecloud.Views.Activities.NavigationDrawerActivity;
import cloud.safe.com.kuchmynda.mark.safecloud.Views.Fragments.StructureFragment;
import cloud.safe.com.kuchmynda.mark.safecloud.Views.Fragments.RegisterFragment;
import cloud.safe.com.kuchmynda.mark.safecloud.Models.Authorization.RegisterModel;

/**
 * Created by Markiian Kuchmynda on 15.09.2017.
 */

public class RegistrationPresenter extends PresenterBase<RegisterFragment> {
    private RegisterModel registerModel;

    @Override
    protected void initModel() {
        if (registerModel == null) {
            registerModel = new RegisterModel();
        }
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
            messageHandler("Empty data or picture isn`t selected");
        }
        else if(!registerModel.getPassword().contentEquals(registerModel.getConfirmPassword())){
            messageHandler("Password and confirm password aren`t identical");
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
            dialog = new Dialog(activity.getApplicationContext());
            DialogInitializator.initializeLoadingDialog(dialog);
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.show();
                }
            });
        }

        @Override
        protected void onPostExecute(Token token) {
            super.onPostExecute(token);
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                }
            });
            if (token == null) {
                messageHandler(service.getError());
            } else {
                UserAccount.getCurrentAccount().setToken(token);
                UserAccount.save(view.getActivity());
                Intent intent=new Intent(activity, NavigationDrawerActivity.class);
                intent.putExtra(CommonData.FRAGMENT_ID_EXTRA, StructureFragment.ID);
                activity.startActivity(intent);
                activity.finish();
            }
        }

        @Override
        protected Token doInBackground(RegisterModel... params) {
            service = new AuthorizationService(ApiConnection.ServerAddress);
            if(service.signUp(ApiConnection.RegisterAddress, params[0])) {
                LoginModel loginModel=new LoginModel();
                loginModel.setEmail(registerModel.getEmail());
                loginModel.setPassword(registerModel.getPassword());
                if (service.signIn(ApiConnection.LoginAddress, loginModel)) {
                    Gson gson = new Gson();
                    String body = service.getLastResponseBody();
                    return gson.fromJson(body, new TypeToken<Token>(){}.getType());
                }
            }
            return null;
        }
    }

}
