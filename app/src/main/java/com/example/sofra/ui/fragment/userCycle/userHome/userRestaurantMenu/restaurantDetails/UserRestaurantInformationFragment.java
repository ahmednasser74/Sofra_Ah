package com.example.sofra.ui.fragment.userCycle.userHome.userRestaurantMenu.restaurantDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sofra.R;
import com.example.sofra.data.model.restaurantLogin.Restaurant;
import com.example.sofra.ui.fragment.untitledFolder.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.sofra.data.api.ApiClient.getClient;


public class UserRestaurantInformationFragment extends BaseFragment {

    @BindView(R.id.restaurant_information_fragment_tv_case)
    TextView restaurantInformationFragmentTvCase;
    @BindView(R.id.restaurant_information_fragment_tv_city)
    TextView restaurantInformationFragmentTvCity;
    @BindView(R.id.restaurant_information_fragment_tv_town)
    TextView restaurantInformationFragmentTvTown;
    @BindView(R.id.restaurant_information_fragment_tv_minimum_cost)
    TextView restaurantInformationFragmentTvMinimumCost;
    @BindView(R.id.restaurant_information_fragment_tv_delivery_cost)
    TextView restaurantInformationFragmentTvDeliveryCost;

    public Restaurant restaurantData;


    public UserRestaurantInformationFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        setUpActivity();
        View view = inflater.inflate(R.layout.fragment_user_restaurant_information, container, false);
        ButterKnife.bind(this, view);
        getClient();

        init();
        return view;
    }

    private void init() {
        restaurantInformationFragmentTvCase.setText(restaurantData.getAvailability());
        restaurantInformationFragmentTvTown.setText(restaurantData.getRegion().getName());
        restaurantInformationFragmentTvCity.setText(restaurantData.getRegion().getCity().getName());
        restaurantInformationFragmentTvMinimumCost.setText(restaurantData.getMinimumCharger());
        restaurantInformationFragmentTvDeliveryCost.setText(restaurantData.getDeliveryCost());

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
