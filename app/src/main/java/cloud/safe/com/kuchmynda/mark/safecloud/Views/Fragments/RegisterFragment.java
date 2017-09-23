package cloud.safe.com.kuchmynda.mark.safecloud.Views.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.mvc.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;

import cloud.safe.com.kuchmynda.mark.safecloud.Presenters.RegistrationPresenter;
import cloud.safe.com.kuchmynda.mark.safecloud.R;

import static cloud.safe.com.kuchmynda.mark.safecloud.Common.CommonData.IMAGE_PICK ;

public class RegisterFragment extends Fragment implements FragmentBase {
    private RegistrationPresenter presenter;

    public RegisterFragment() {
        super();
    }

    private final View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            presenter.signUp();
        }
    };
    private final View.OnClickListener imagePick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ImagePicker.pickImage(getActivity(), "Pick your image:", IMAGE_PICK, false);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap picked = ImagePicker.getImageFromResult(getActivity(), requestCode, resultCode, data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        assert picked != null;
        picked.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        presenter.setAvatar(outputStream.toByteArray());
        ImageView imageView = (ImageView) this.getView().findViewById(R.id.registration_image);
        imageView.setImageBitmap(picked);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (presenter == null) {
            presenter = new RegistrationPresenter();
        }
        presenter.takeView(this);
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        initView(view);
        return view;
    }

    @Override
    public void initView(View view) {
        ImagePicker.setMinQuality(300, 300);
        view.findViewById(R.id.register_button).setOnClickListener(clickListener);
        view.findViewById(R.id.registration_image).setOnClickListener(imagePick);
        //region EditText events
        ((EditText) view.findViewById(R.id.register_firstname)).addTextChangedListener(new TextWatcher() {
            void setData(String text) {
                presenter.setFirstName(text);
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
        ((EditText) view.findViewById(R.id.register_lastname)).addTextChangedListener(new TextWatcher() {
            void setData(String text) {
                presenter.setLastName(text);
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
        ((EditText) view.findViewById(R.id.register_username)).addTextChangedListener(new TextWatcher() {
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
        ((EditText) view.findViewById(R.id.register_password)).addTextChangedListener(new TextWatcher() {
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
        ((EditText) view.findViewById(R.id.register_confpassword)).addTextChangedListener(new TextWatcher() {
            void setData(String text) {
                presenter.setConfirmPassword(text);
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