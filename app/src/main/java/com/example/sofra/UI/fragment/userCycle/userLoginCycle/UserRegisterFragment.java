package com.example.sofra.UI.fragment.userCycle.userLoginCycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sofra.R;
import com.example.sofra.UI.fragment.untitledFolder.BaseFragment;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.sofra.data.api.ApiClient.getClient;


public class UserRegisterFragment extends BaseFragment {

    @BindView(R.id.user_register_fragment_et_restaurant_name)
    TextInputLayout userRegisterFragmentEtRestaurantName;
    @BindView(R.id.user_register_fragment_et_mail)
    TextInputLayout userRegisterFragmentEtMail;
    @BindView(R.id.user_register_fragment_et_phone)
    TextInputLayout userRegisterFragmentEtPhone;
    @BindView(R.id.user_register_fragment_sp_city)
    Spinner userRegisterFragmentSpCity;
    @BindView(R.id.register_fragment_sp_blood_type)
    ImageView registerFragmentSpBloodType;
    @BindView(R.id.user_register_fragment_sp_town)
    Spinner userRegisterFragmentSpTown;
    @BindView(R.id.user_register_fragment_et_password)
    TextInputLayout userRegisterFragmentEtPassword;
    @BindView(R.id.user_register_fragment_et_password_confirmation)
    TextInputLayout userRegisterFragmentEtPasswordConfirmation;
    @BindView(R.id.user_register_fragment_btn_register)
    Button userRegisterFragmentBtnRegister;

    public UserRegisterFragment() {
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

        View view = inflater.inflate(R.layout.fragment_user_register, container, false);
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

    @OnClick(R.id.user_register_fragment_btn_register)
    public void onViewClicked() {
    }
}
