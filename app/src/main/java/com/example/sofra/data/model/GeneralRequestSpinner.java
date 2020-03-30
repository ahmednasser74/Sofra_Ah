package com.example.sofra.data.model;

import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sofra.R;
import com.example.sofra.adapter.RestaurantCategoryAdapter;
import com.example.sofra.adapter.RestaurantOrdersAdapter;
import com.example.sofra.adapter.SpinnersAdapter;
import com.example.sofra.data.model.listOfTown.GeneralResponse;
import com.example.sofra.data.model.restaurantCategory.CategoryData;
import com.example.sofra.data.model.userOrders.UserOrdersData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GeneralRequestSpinner {
    public static void getSpinnerCityData(Call<GeneralResponse> call, SpinnersAdapter spinnerAdapter, Spinner spinner,
                                          String hint, AdapterView.OnItemSelectedListener listener, int selectedId) {

        call.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        spinnerAdapter.setData(response.body().getData(), hint);
                        spinner.setAdapter(spinnerAdapter);

                        int pos = 0;
                        for (int i = 0; i < spinnerAdapter.generalResponseData.size(); i++) {
                            if (selectedId == spinnerAdapter.generalResponseData.get(i).getId()) {
                                pos = i;
                                break;
                            }
                        }
                        spinner.setSelection(pos);

                        spinner.setOnItemSelectedListener(listener);
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {

            }
        });
    }

    public static void getSpinnerCityData2(Call<GeneralResponse> call, SpinnersAdapter spinnerAdapter, Spinner spinner,
                                           String hint) {

        call.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        spinnerAdapter.setData(response.body().getData(), hint);

                        spinner.setAdapter(spinnerAdapter);


                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {

            }
        });
    }

    public static void getTownSpinnerData(Call<GeneralResponse> call, SpinnersAdapter spinnerAdapter, Spinner spinner, String hint, int selectedId) {

        call.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                try {
                    if (response.body().getStatus() == 1) {

                        spinnerAdapter.setData(response.body().getData(), hint);
                        spinner.setAdapter(spinnerAdapter);

                        int pos = 0;
                        for (int i = 0; i < spinnerAdapter.generalResponseData.size(); i++) {
                            if (selectedId == spinnerAdapter.generalResponseData.get(i).getId()) {
                                pos = i;
                                break;
                            }
                        }
                        spinner.setSelection(pos);

                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {

            }
        });
    }
}
