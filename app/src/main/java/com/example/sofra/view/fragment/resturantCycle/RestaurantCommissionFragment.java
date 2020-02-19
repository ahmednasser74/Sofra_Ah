package com.example.sofra.view.fragment.resturantCycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sofra.R;
import com.example.sofra.data.model.restaurantCommission.RestaurantCommission;
import com.example.sofra.data.model.restaurantCommission.RestaurantCommissionData;
import com.example.sofra.view.fragment.untitledFolder.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.data.local.SharedPreference.LoadData;
import static com.example.sofra.data.local.SharedPreference.RESTAURANT_API_TOKEN;
import static com.example.sofra.data.local.SharedPreference.loadRestaurantData;


public class RestaurantCommissionFragment extends BaseFragment {

    @BindView(R.id.commission_fragment_restaurant_sales)
    TextView commissionFragmentRestaurantSales;
    @BindView(R.id.commission_fragment_app_commission)
    TextView commissionFragmentAppCommission;
    @BindView(R.id.commission_fragment_paid)
    TextView commissionFragmentPaid;
    @BindView(R.id.commission_fragment_remaining_amount)
    TextView commissionFragmentRemainingAmount;

    RestaurantCommissionData restaurantCommissionData;

    public RestaurantCommissionFragment() {
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

        View view = inflater.inflate(R.layout.fragment_commission, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {

        getClient().getRestaurantCommission(LoadData(getActivity(),RESTAURANT_API_TOKEN)).enqueue(new Callback<RestaurantCommission>() {
            @Override
            public void onResponse(Call<RestaurantCommission> call, Response<RestaurantCommission> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        restaurantCommissionData = response.body().getData();
                        commissionFragmentAppCommission.setText(restaurantCommissionData.getCommission());
                        commissionFragmentPaid.setText(restaurantCommissionData.getPayments());
                        commissionFragmentRestaurantSales.setText(restaurantCommissionData.getTotal());
                        commissionFragmentRemainingAmount.setText(restaurantCommissionData.getCommissions());
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<RestaurantCommission> call, Throwable t) {

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
