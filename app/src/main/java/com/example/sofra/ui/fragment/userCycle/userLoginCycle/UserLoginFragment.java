package com.example.sofra.ui.fragment.userCycle.userLoginCycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sofra.R;
import com.example.sofra.data.model.userLogin.UserLogin;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.ui.fragment.untitledFolder.BaseFragment;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;


public class UserLoginFragment extends BaseFragment {

    @BindView(R.id.user_login_fragment_et_mail)
    TextInputLayout userLoginFragmentEtMail;
    @BindView(R.id.user_login_fragment_et_password)
    TextInputLayout userLoginFragmentEtPassword;
    @BindView(R.id.user_login_fragment_btn_register)
    Button userLoginFragmentBtnRegister;
    public String password;

    public UserLoginFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        setUpActivity();
        getClient();

        View view = inflater.inflate(R.layout.fragment_user_login, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    private void getLogin() {

        String email = userLoginFragmentEtMail.getEditText().getText().toString().trim();
        password = userLoginFragmentEtPassword.getEditText().getText().toString().trim();

        if (email.isEmpty()) {
            userLoginFragmentEtMail.setError("please enter email");
        } else if (password.isEmpty()) {
            userLoginFragmentEtPassword.setError("Please Enter Password");
        } else {
            userLoginFragmentEtMail.setError(null);
            userLoginFragmentEtPassword.setError(null);
            HelperMethod.showProgressDialog(getActivity(), "Please Wait...");
        }

        init(email, password);
    }

    private void init(String email, String password) {

        getClient().getUserLogin(email, password).enqueue(new Callback<UserLogin>() {
            @Override
            public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                try {
                    if (response.body().getStatus() == 1) {

                        HelperMethod.dismissProgressDialog();
                        Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<UserLogin> call, Throwable t) {

            }
        });
    }

    @OnClick({R.id.user_login_fragment_tv_forget_password, R.id.user_login_fragment_btn_register, R.id.user_login_fragment_btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_login_fragment_tv_forget_password:

                HelperMethod.replace(new UserForgetPasswordFragment1(), getActivity().getSupportFragmentManager(),
                        R.id.user_cycle_activity_fl_container, null, null);

                break;

            case R.id.user_login_fragment_btn_register:
                HelperMethod.replace(new UserRegisterFragment(), getActivity().getSupportFragmentManager(),
                        R.id.user_cycle_activity_fl_container, null, null);
                break;

            case R.id.user_login_fragment_btn_login:

                getLogin();
                break;
        }
    }


    @Override
    public void onBack() {
        super.onBack();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
