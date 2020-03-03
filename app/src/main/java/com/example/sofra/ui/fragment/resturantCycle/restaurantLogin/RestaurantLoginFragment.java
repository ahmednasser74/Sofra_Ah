package com.example.sofra.ui.fragment.resturantCycle.restaurantLogin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sofra.R;
import com.example.sofra.data.local.SharedPreference;
import com.example.sofra.data.model.restaurantLogin.AuthRestaurant;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.ui.activity.RestaurantCycleActivity;
import com.example.sofra.ui.fragment.resturantCycle.restaurantHome.RestaurantCategoryFragment;
import com.example.sofra.ui.fragment.untitledFolder.BaseFragment;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.data.local.SharedPreference.RESTAURANT_DATA;
import static com.example.sofra.data.local.SharedPreference.RESTAURANT_API_TOKEN;


public class RestaurantLoginFragment extends BaseFragment {

    @BindView(R.id.restaurant_login_fragment_tv_forget_password)
    TextView restaurantLoginFragmentTvForgetPassword;
    @BindView(R.id.restaurant_login_fragment_btn_login)
    LinearLayout restaurantLoginFragmentBtnLogin;
    @BindView(R.id.restaurant_login_fragment_btn_register)
    Button restaurantLoginFragmentBtnRegister;
    @BindView(R.id.restaurant_login_fragment_et_mail)
    TextInputLayout restaurantLoginFragmentEtMail;
    @BindView(R.id.restaurant_login_fragment_et_password)
    TextInputLayout restaurantLoginFragmentEtPassword;
    public static String password;

    public RestaurantLoginFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        setUpActivity();
        View view = inflater.inflate(R.layout.fragment_restaurant_login_, container, false);
        ButterKnife.bind(this, view);

        RestaurantCycleActivity restaurantCycleActivity = (RestaurantCycleActivity) getActivity();
        restaurantCycleActivity.setVisibilityBottomNavBar(View.GONE);
        restaurantCycleActivity.setVisibilityToolBar(View.GONE);

        return view;
    }

    private void login() {
        String email = restaurantLoginFragmentEtMail.getEditText().getText().toString();
        password = restaurantLoginFragmentEtPassword.getEditText().getText().toString();

        if (email.isEmpty()) {
            restaurantLoginFragmentEtMail.setError("please enter email");
        } else if (password.isEmpty()) {
            restaurantLoginFragmentEtPassword.setError("Please Enter Password");
        } else {
            restaurantLoginFragmentEtMail.setError(null);
            restaurantLoginFragmentEtPassword.setError(null);
            HelperMethod.showProgressDialog(getActivity(), "Please Wait...");
        }
        getLogin(email, password);
    }

    private void getLogin(String email, String password) {
        getClient().getRestaurantLogin(email, password).enqueue(new Callback<AuthRestaurant>() {
            @Override
            public void onResponse(Call<AuthRestaurant> call, Response<AuthRestaurant> response) {
                HelperMethod.dismissProgressDialog();
                try {
                    if (response.body().getStatus() == 1) {
                        SharedPreference.SaveData(getActivity(), RESTAURANT_API_TOKEN, response.body().getData().getApiToken());
                        SharedPreference.SaveData(getActivity(), RESTAURANT_DATA, response.body().getData());

                        HelperMethod.replace(new RestaurantCategoryFragment(), getActivity().getSupportFragmentManager(),
                                R.id.restaurant_cycle_fl_fragment_container, null, null);

                        Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(baseActivity, "User or Password Incorrect", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<AuthRestaurant> call, Throwable t) {
            }
        });
    }

    @OnClick({R.id.restaurant_login_fragment_btn_login, R.id.restaurant_login_fragment_btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.restaurant_login_fragment_btn_login:
                login();
                break;
            case R.id.restaurant_login_fragment_btn_register:
                HelperMethod.replaceFragmentWithAnimation(getActivity().getSupportFragmentManager(),
                        R.id.restaurant_cycle_fl_fragment_container, new RestaurantRegisterFragment1(), "b");
                break;
        }
    }

    @Override
    public void onBack() {
        getActivity().finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
