package com.example.sofra.ui.fragment.resturantCycle.restaurantLogin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.agrawalsuneet.dotsloader.utils.Helper;
import com.example.sofra.R;
import com.example.sofra.adapter.SpinnersAdapter;
import com.example.sofra.data.model.GeneralRequestSpinner;
import com.example.sofra.data.model.restaurantRegister.RestaurantRegister;
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


public class RestaurantRegisterFragment1 extends BaseFragment {


    @BindView(R.id.restaurant_register1_fragment_et_restaurant_name)
    TextInputLayout restaurantRegister1FragmentEtRestaurantName;
    @BindView(R.id.restaurant_register1_fragment_et_mail)
    TextInputLayout restaurantRegister1FragmentEtMail;
    @BindView(R.id.restaurant_register1_fragment_et_delivery_duration)
    TextInputLayout restaurantRegister1FragmentEtDeliveryDuration;
    @BindView(R.id.restaurant_register1_fragment_sp_city)
    Spinner restaurantRegister1FragmentSpCity;
    @BindView(R.id.register_fragment_sp_blood_type)
    ImageView registerFragmentSpBloodType;
    @BindView(R.id.restaurant_register1_fragment_sp_town)
    Spinner restaurantRegister1FragmentSpTown;
    @BindView(R.id.restaurant_register1_fragment_et_password)
    TextInputLayout restaurantRegister1FragmentEtPassword;
    @BindView(R.id.restaurant_register1_fragment_et_password_confirmation)
    TextInputLayout restaurantRegister1FragmentEtPasswordConfirmation;
    @BindView(R.id.restaurant_register1_fragment_et_minimum_delivery)
    TextInputLayout restaurantRegister1FragmentEtMinimumDelivery;
    @BindView(R.id.restaurant_register1_fragment_et_delivery_cost)
    TextInputLayout restaurantRegister1FragmentEtDeliveryCost;
    @BindView(R.id.restaurant_register1_fragment_btn_continue)
    Button restaurantRegister1FragmentBtnContinue;

    private SpinnersAdapter cityAdapter, townAdapter;

    public RestaurantRegisterFragment1() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        setUpActivity();
        View view = inflater.inflate(R.layout.fragment_restaurant_register1, container, false);
        ButterKnife.bind(this, view);

        townAdapter = new SpinnersAdapter(getActivity());
        cityAdapter = new SpinnersAdapter(getActivity());
        GeneralRequestSpinner.getSpinnerCityData(getClient().getCity(), cityAdapter
                , restaurantRegister1FragmentSpCity, "City", new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i != 0) {
                            GeneralRequestSpinner.getTownSpinnerData(getClient().getTown(cityAdapter.selectedId), townAdapter
                                    , restaurantRegister1FragmentSpTown, "Town");
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

        return view;
    }

    private void getRegister1() {

        String name = restaurantRegister1FragmentEtRestaurantName.getEditText().getText().toString();
        String email = restaurantRegister1FragmentEtPassword.getEditText().getText().toString();
        String password = restaurantRegister1FragmentEtPasswordConfirmation.getEditText().getText().toString();
        String passwordConfirmation = restaurantRegister1FragmentEtMail.getEditText().getText().toString();
        int regionId = restaurantRegister1FragmentSpTown.getId();
        String deliveryCost = restaurantRegister1FragmentEtDeliveryCost.getEditText().getText().toString();
        String minimumCharger = restaurantRegister1FragmentEtMinimumDelivery.getEditText().getText().toString();
        String deliveryTime = restaurantRegister1FragmentEtDeliveryDuration.getEditText().getText().toString();

        if (name.isEmpty() && email.isEmpty() && password.isEmpty() && passwordConfirmation.isEmpty()
                && deliveryCost.isEmpty() && minimumCharger.isEmpty() && deliveryTime.isEmpty()) {
            Toast.makeText(baseActivity, "Please Complete Register Information", Toast.LENGTH_SHORT).show();
        } else if (regionId == 0) {
            Toast.makeText(baseActivity, "Please Complete Register Information", Toast.LENGTH_SHORT).show();
        } else if (restaurantRegister1FragmentEtPasswordConfirmation.getEditText().getText().toString().
                equals(restaurantRegister1FragmentEtPassword.getEditText().getText().toString())) {
            Toast.makeText(baseActivity, "Password confirmation not matched", Toast.LENGTH_SHORT).show();
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

    @OnClick(R.id.restaurant_register1_fragment_btn_continue)
    public void onViewClicked() {

        HelperMethod.replace(new RestaurantRegisterFragment2(), getActivity().getSupportFragmentManager(),
                R.id.restaurant_cycle_fl_fragment_container, null, null);

    }
}
