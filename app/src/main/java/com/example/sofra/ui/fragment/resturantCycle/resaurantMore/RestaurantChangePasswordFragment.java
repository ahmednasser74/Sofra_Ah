package com.example.sofra.ui.fragment.resturantCycle.resaurantMore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sofra.R;
import com.example.sofra.data.model.restaurantChangePassword.RestaurantChangePassword;
import com.example.sofra.ui.fragment.resturantCycle.restaurantLogin.RestaurantLoginFragment;
import com.example.sofra.ui.fragment.untitledFolder.BaseFragment;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.data.local.SharedPreference.LoadData;
import static com.example.sofra.data.local.SharedPreference.RESTAURANT_API_TOKEN;
import static com.example.sofra.helper.HelperMethod.dismissProgressDialog;
import static com.example.sofra.helper.HelperMethod.showProgressDialog;


public class RestaurantChangePasswordFragment extends BaseFragment {

    @BindView(R.id.restaurant_change_password_fragment_et_old_password)
    TextInputLayout restaurantChangePasswordFragmentEtOldPassword;
    @BindView(R.id.restaurant_change_password_fragment_et_new_password)
    TextInputLayout restaurantChangePasswordFragmentEtNewPassword;
    @BindView(R.id.restaurant_change_password_fragment_et_confirmation_password)
    TextInputLayout restaurantChangePasswordFragmentEtConfirmationPassword;
    @BindView(R.id.restaurant_change_password_fragment_btn_change_password)
    Button restaurantChangePasswordFragmentBtnChangePassword;

    public RestaurantChangePasswordFragment() {
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

        View view = inflater.inflate(R.layout.fragment_restaurant_change_password, container, false);
        ButterKnife.bind(this, view);


        return view;

    }


    @OnClick(R.id.restaurant_change_password_fragment_btn_change_password)
    public void onViewClicked() {
        changePassword();
    }

    private void changePassword() {

        String oldPassword = restaurantChangePasswordFragmentEtOldPassword.getEditText().getText().toString().trim();
        String newPassword = restaurantChangePasswordFragmentEtNewPassword.getEditText().getText().toString().trim();
        String confirmationNewPassword = restaurantChangePasswordFragmentEtConfirmationPassword.getEditText().getText().toString().trim();

        if (oldPassword.isEmpty()) {
            Toast.makeText(baseActivity, "please enter old password", Toast.LENGTH_SHORT).show();
        } else if (newPassword.isEmpty()) {
            Toast.makeText(baseActivity, "please enter new password", Toast.LENGTH_SHORT).show();
        } else if (confirmationNewPassword.isEmpty()) {
            Toast.makeText(baseActivity, "please enter confirmation password", Toast.LENGTH_SHORT).show();
        } else if (!newPassword.equals(confirmationNewPassword)) {
            Toast.makeText(baseActivity, "Confirmation password didn't match", Toast.LENGTH_SHORT).show();
        } else if (!oldPassword.equals(RestaurantLoginFragment.password)) {
            Toast.makeText(baseActivity, "Old password didn't match", Toast.LENGTH_SHORT).show();
        } else {
            showProgressDialog(getActivity(), "Please Wait...");
        }

        getRestaurantChangePassword(oldPassword, newPassword, confirmationNewPassword);

    }

    private void getRestaurantChangePassword(String oldPassword, String newPassword, String confirmationPassword) {

        getClient().getRestaurantChangePassword(LoadData(getActivity(), RESTAURANT_API_TOKEN), oldPassword,
                newPassword, confirmationPassword).enqueue(new Callback<RestaurantChangePassword>() {

            @Override
            public void onResponse(Call<RestaurantChangePassword> call, Response<RestaurantChangePassword> response) {
                dismissProgressDialog();
                try {

                    if (response.body().getStatus() == 1) {

                        Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<RestaurantChangePassword> call, Throwable t) {

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
