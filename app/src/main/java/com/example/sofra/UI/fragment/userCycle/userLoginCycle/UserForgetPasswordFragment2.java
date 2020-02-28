package com.example.sofra.UI.fragment.userCycle.userLoginCycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sofra.R;
import com.example.sofra.UI.fragment.untitledFolder.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.sofra.data.api.ApiClient.getClient;


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


    @Override
    public void onBack() {
        super.onBack();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.user_forget_password_fragment2_btn_send)
    public void onViewClicked() {
    }
}
