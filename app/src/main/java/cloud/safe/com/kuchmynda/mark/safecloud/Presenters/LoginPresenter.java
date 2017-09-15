package cloud.safe.com.kuchmynda.mark.safecloud.Presenters;

import android.text.TextUtils;
import android.widget.Toast;

import cloud.safe.com.kuchmynda.mark.safecloud.Fragments.LoginFragment;
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
    protected void errorHandler() {
        Toast.makeText(view.getActivity(), "Empty fields", Toast.LENGTH_SHORT).show();
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
            errorHandler();
        } else {
            Toast.makeText(view.getActivity(), "Login test", Toast.LENGTH_SHORT).show();
        }
    }
}
