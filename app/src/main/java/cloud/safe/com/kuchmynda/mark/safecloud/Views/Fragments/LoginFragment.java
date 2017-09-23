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

import java.io.IOException;

import cloud.safe.com.kuchmynda.mark.safecloud.Infrastructure.Database.SqliteManager;
import cloud.safe.com.kuchmynda.mark.safecloud.Presenters.LoginPresenter;
import cloud.safe.com.kuchmynda.mark.safecloud.R;

public class LoginFragment extends Fragment implements FragmentBase {
    private LoginPresenter presenter;

    public LoginFragment() {
        super();
    }

    private final View.OnClickListener clickListener = new View.OnClickListener() {
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
            SqliteManager sqliteManager = new SqliteManager(getContext());
            try {
                sqliteManager.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        presenter.takeView(this);
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initView(view);
        return view;
    }

    @Override
    public void initView(View view) {
        view.findViewById(R.id.login_button).setOnClickListener(clickListener);
        EditText login=(EditText) view.findViewById(R.id.login_username);
        EditText password= (EditText) view.findViewById(R.id.login_password);
        login.setText("harrisonford@gmil.com");
        password.setText("Mark95!");
        //region EditText events
        login.addTextChangedListener(new TextWatcher() {
            void setData(String text) {
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
        password.addTextChangedListener(new TextWatcher() {
            void setData(String text) {
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
    }

    @Override
    public void initArguments() {

    }
}