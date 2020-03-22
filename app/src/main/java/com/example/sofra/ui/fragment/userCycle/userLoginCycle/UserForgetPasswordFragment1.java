package com.example.sofra.ui.fragment.userCycle.userLoginCycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sofra.R;
import com.example.sofra.data.local.SharedPreference;
import com.example.sofra.data.model.userLogin.UserLogin;
import com.example.sofra.data.model.userResetPassword.UserResetPassword;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.ui.fragment.untitledFolder.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.data.local.SharedPreference.USER_API_TOKEN;


public class UserForgetPasswordFragment1 extends BaseFragment {

    @BindView(R.id.user_forget_password_fragment1_et_email)
    EditText userForgetPasswordFragment1EtEmail;

    private String apiToken,email ;

    public UserForgetPasswordFragment1() {
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

        View view = inflater.inflate(R.layout.fragment_user_forget_password1, container, false);
        ButterKnife.bind(this, view);

        apiToken = SharedPreference.LoadData(getActivity(), USER_API_TOKEN);

        return view;
    }


    private void getEmail() {
        if (apiToken.equals("client")) {

            email = userForgetPasswordFragment1EtEmail.getText().toString().trim();
            UserLogin userLogin = new UserLogin();
            if (email.isEmpty()) {
                Toast.makeText(baseActivity, R.string.please_enter_your_mail, Toast.LENGTH_SHORT).show();
            }  else {
                HelperMethod.showProgressDialog(getActivity(), "Please wait...");
            }
            init(email);
        }
    }

    private void init(String email) {
        getClient().getUserResetPassword(email).enqueue(new Callback<UserResetPassword>() {
            @Override
            public void onResponse(Call<UserResetPassword> call, Response<UserResetPassword> response) {
                try {
                    if (response.body().getStatus() == 1) {

                        HelperMethod.dismissProgressDialog();
                        Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();

                        HelperMethod.replace(new UserForgetPasswordFragment2(), getActivity().getSupportFragmentManager(),
                                R.id.user_cycle_activity_fl_container, null, null);
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<UserResetPassword> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.user_forget_password_fragment1_btn_send)
    public void onViewClicked() {
        getEmail();
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
