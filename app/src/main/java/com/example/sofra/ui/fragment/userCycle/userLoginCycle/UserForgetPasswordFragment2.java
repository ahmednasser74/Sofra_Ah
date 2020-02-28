package com.example.sofra.ui.fragment.userCycle.userLoginCycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sofra.R;
import com.example.sofra.data.model.userNewPassword.UserNewPassword;
import com.example.sofra.data.model.userResetPassword.UserResetPassword;
import com.example.sofra.ui.fragment.resturantCycle.restaurantLogin.RestaurantLoginFragment;
import com.example.sofra.ui.fragment.untitledFolder.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.helper.HelperMethod.dismissProgressDialog;
import static com.example.sofra.helper.HelperMethod.showProgressDialog;


public class UserForgetPasswordFragment2 extends BaseFragment {

    @BindView(R.id.user_forget_password_fragment2_et_verify_code)
    EditText userForgetPasswordFragment2EtVerifyCode;
    @BindView(R.id.user_forget_password_fragment2_et_password)
    EditText userForgetPasswordFragment2EtPassword;
    @BindView(R.id.user_forget_password_fragment2_et_password_confirmation)
    EditText userForgetPasswordFragment2EtPasswordConfirmation;
    @BindView(R.id.user_forget_password_fragment2_btn_send)
    Button userForgetPasswordFragment2BtnSend;

    public UserForgetPasswordFragment2() {
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

        View view = inflater.inflate(R.layout.fragment_user_forget_password2, container, false);
        ButterKnife.bind(this, view);


        return view;
    }


    @OnClick(R.id.user_forget_password_fragment2_btn_send)
    public void onViewClicked() {
        getResetPassword();
    }

    private void getResetPassword() {

        String code = userForgetPasswordFragment2EtVerifyCode.getText().toString();
        String password = userForgetPasswordFragment2EtPassword.getText().toString();
        String passwordConfirmation = userForgetPasswordFragment2EtPasswordConfirmation.getText().toString();
        UserResetPassword userResetPassword = new UserResetPassword();

        if (code.isEmpty() && password.isEmpty() && passwordConfirmation.isEmpty()) {
            Toast.makeText(baseActivity, "please complete the field", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(passwordConfirmation)) {
            Toast.makeText(baseActivity, "Confirmation password didn't match", Toast.LENGTH_SHORT).show();
        } else if (!code.equals(userResetPassword.getData().getCode())) {
            Toast.makeText(baseActivity, "Code isn't correct", Toast.LENGTH_SHORT).show();
        } else {
            showProgressDialog(getActivity(), "Please Wait...");
        }

        init(code, password, passwordConfirmation);

    }

    private void init(String code, String password, String passwordConfirmation) {

        getClient().getUserNewPassword(code, password, passwordConfirmation).enqueue(new Callback<UserNewPassword>() {
            @Override
            public void onResponse(Call<UserNewPassword> call, Response<UserNewPassword> response) {
                dismissProgressDialog();
                try {
                    if (response.body().getStatus() == 1) {

                        Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<UserNewPassword> call, Throwable t) {

            }
        });
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
