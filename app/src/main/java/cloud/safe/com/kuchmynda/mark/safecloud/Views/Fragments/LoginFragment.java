package cloud.safe.com.kuchmynda.mark.safecloud.Views.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import cloud.safe.com.kuchmynda.mark.safecloud.Presenters.LoginPresenter;
import cloud.safe.com.kuchmynda.mark.safecloud.R;

public class LoginFragment extends Fragment {
    LoginPresenter presenter;

    public LoginFragment() {
        super();
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            presenter.signIn();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (presenter == null) {
            presenter = new LoginPresenter();
        }
        presenter.takeView(this);

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        view.findViewById(R.id.login_button).setOnClickListener(clickListener);
        //region EditText events
        ((EditText)view.findViewById(R.id.login_username)).addTextChangedListener(new TextWatcher() {
            void setData(String text){
                presenter.setLogin(text);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                setData(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                setData(s.toString());
            }
        });
        ((EditText)view.findViewById(R.id.login_password)).addTextChangedListener(new TextWatcher() {
            void setData(String text){
                presenter.setPassword(text);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                setData(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                setData(s.toString());
            }
        });
        //endregion
        return view;
    }
}
