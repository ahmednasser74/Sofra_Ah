package com.example.sofra.ui.fragment.resturantCycle.restaurantLogin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sofra.R;
import com.example.sofra.adapter.SpinnersAdapter;
import com.example.sofra.data.model.GeneralRequestSpinner;
import com.example.sofra.data.model.StepOne;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.ui.fragment.untitledFolder.BaseFragment;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;

import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.helper.HelperMethod.convertToRequestBody;


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
    private StepOne stepOne;

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
                                    , restaurantRegister1FragmentSpTown, "Town", 0);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                }, 0);

        return view;
    }

    public void getRegisterData() {

        RequestBody name = convertToRequestBody(restaurantRegister1FragmentEtRestaurantName.getEditText().getText().toString());
        RequestBody email = convertToRequestBody(restaurantRegister1FragmentEtPassword.getEditText().getText().toString());
        RequestBody password = convertToRequestBody(restaurantRegister1FragmentEtPasswordConfirmation.getEditText().getText().toString());
        RequestBody passwordConfirmation = convertToRequestBody(restaurantRegister1FragmentEtMail.getEditText().getText().toString());
        RequestBody deliveryCost = convertToRequestBody(restaurantRegister1FragmentEtDeliveryCost.getEditText().getText().toString());
        RequestBody minimumCharger = convertToRequestBody(restaurantRegister1FragmentEtMinimumDelivery.getEditText().getText().toString());
        RequestBody deliveryTime = convertToRequestBody(restaurantRegister1FragmentEtDeliveryDuration.getEditText().getText().toString());
        RequestBody regionId = convertToRequestBody(String.valueOf(restaurantRegister1FragmentSpTown.getId()));

        stepOne = new StepOne(name, email, password, passwordConfirmation, deliveryCost, minimumCharger, deliveryTime, regionId);


//        if (name && email && password && passwordConfirmation && deliveryCost && minimumCharger && deliveryTime.equals()) {
//            Toast.makeText(baseActivity, "Please Complete Register Information", Toast.LENGTH_SHORT).show();
//        } else if (regionId == 0) {
//            Toast.makeText(baseActivity, "Please Complete Register Information", Toast.LENGTH_SHORT).show();
//        } else if (!restaurantRegister1FragmentEtPasswordConfirmation.getEditText().getText().toString().
//                equals(restaurantRegister1FragmentEtPassword.getEditText().getText().toString())) {
//            Toast.makeText(baseActivity, "Password confirmation not matched", Toast.LENGTH_SHORT).show();
//        }

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

        RestaurantRegisterFragment2 restaurantRegisterFragment2 = new RestaurantRegisterFragment2();
        restaurantRegisterFragment2.stepOne = stepOne;
        HelperMethod.replace(restaurantRegisterFragment2, getActivity().getSupportFragmentManager(),
                R.id.restaurant_cycle_fl_fragment_container, null, null);

    }
}
