package cloud.safe.com.kuchmynda.mark.safecloud.Presenters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.concurrent.ExecutionException;

import cloud.safe.com.kuchmynda.mark.safecloud.Common.ApiConnection;
import cloud.safe.com.kuchmynda.mark.safecloud.Common.CommonData;
import cloud.safe.com.kuchmynda.mark.safecloud.Infrastructure.Helpers.DialogInitializator;
import cloud.safe.com.kuchmynda.mark.safecloud.Infrastructure.Services.AuthorizationService;
import cloud.safe.com.kuchmynda.mark.safecloud.Infrastructure.Token;
import cloud.safe.com.kuchmynda.mark.safecloud.Infrastructure.UserAccount;
import cloud.safe.com.kuchmynda.mark.safecloud.R;
import cloud.safe.com.kuchmynda.mark.safecloud.Views.Activities.NavigationDrawerActivity;
import cloud.safe.com.kuchmynda.mark.safecloud.Views.Fragments.GalleryFragment;
import cloud.safe.com.kuchmynda.mark.safecloud.Views.Fragments.LoginFragment;
import cloud.safe.com.kuchmynda.mark.safecloud.Models.Authorization.LoginModel;

/**
 * Created by MARKAN on 15.09.2017.
 */

public class LoginPresenter extends PresenterBase<LoginFragment> {
    LoginModel loginModel;

    @Override
    protected void execute() {

    }

    @Override
    protected void initModel() {
        if (loginModel == null) {
            loginModel = new LoginModel();
        }
    }

    @Override
    protected void errorHandler(final String message) {
        view.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(view.getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setLogin(String text) {
        initModel();
        loginModel.setEmail(text);
    }

    public void setPassword(String text) {
        initModel();
        loginModel.setPassword(text);
    }

    public void signIn() {
        if (loginModel == null || TextUtils.isEmpty(loginModel.getEmail()) || TextUtils.isEmpty(loginModel.getPassword())) {
            errorHandler("Empty fields");
        } else {
            SignInAsyncTask signInAsyncTask = new SignInAsyncTask(view.getActivity());
            signInAsyncTask.execute(loginModel);
        }
    }

    private class SignInAsyncTask extends AsyncTask<LoginModel, Void, Token> {
        private final Activity activity;
        AuthorizationService service;

        SignInAsyncTask(Activity activity) {
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
        protected Token doInBackground(LoginModel... params) {
            service = new AuthorizationService(ApiConnection.ServerAdress);
            if (service.signIn(ApiConnection.LoginAdress, params[0])) {
                Gson gson = new Gson();
                String body=service.getLastResponseBody();
                Token token = gson.fromJson(body, new TypeToken<Token>(){}.getType());
                return token;
            }
            return null;
        }
    }
}
