package cloud.safe.com.kuchmynda.mark.safecloud.Presenters;

import android.text.TextUtils;
import android.widget.Toast;

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
        } else {
            Toast.makeText(view.getActivity(), "Register test", Toast.LENGTH_SHORT).show();
        }
    }

    public void setAvatar(byte[] avatar) {
        initModel();
        registerModel.setAvatar(avatar);
    }
}
