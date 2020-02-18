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

    @BindView(R.id.restaurant_order_container_fragment_rv_orders)
    RecyclerView restaurantOrderContainerFragmentRvOrders;
    @BindView(R.id.restaurant_category_fragment_rv_category)
    RecyclerView restaurantCategoryFragmentRvCategory;

    int maxPage = 0;
    public String State;

    RestaurantOrdersAdapter restaurantOrdersAdapter;
    RestaurantCategoryAdapter restaurantCategoryAdapter;
    private List<UserOrdersData> userOrdersData = new ArrayList<>();
    private List<CategoryData> listOfCategoryDatalist = new ArrayList<>();

    public static void getSpinnerCityData(Call<GeneralResponse> call, SpinnersAdapter spinnerAdapter, Spinner spinner,
                                          String hint, AdapterView.OnItemSelectedListener listener) {

        call.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        spinnerAdapter.setData(response.body().getData(), hint);
                        spinner.setAdapter(spinnerAdapter);
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

    public static void getTownSpinnerData(Call<GeneralResponse> call, SpinnersAdapter spinnerAdapter, Spinner spinner, String hint) {

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

//    public void getRestaurantOrders(int page) {
//        getClient().getRestaurantOrders("Jptu3JVmDXGpJEaQO9ZrjRg5RuAVCo45OC2AcOKqbVZPmu0ZJPN3T1sm0cWx", State, page).enqueue(new Callback<UserOrders>() {
//            @Override
//            public void onResponse(Call<UserOrders> call, Response<UserOrders> response) {
//                try {
//                    if (response.body().getStatus() == 1) {
//                        maxPage = response.body().getData().getLastPage();
//                        restaurantOrderContainerFragmentRvOrders.setVisibility(View.VISIBLE);
//                        userOrdersData.addAll(response.body().getData().getData());
//                        restaurantOrdersAdapter.notifyDataSetChanged();
//                    }
//                } catch (Exception e) {
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<UserOrders> call, Throwable t) {
//
//            }
//        });
//    }

//    public void getRestaurantCategory(int page) {
////        String apiToken = SharedPreference.LoadData(getActivity(),API_TOKEN);
//
//        getClient().getRestaurantCategory("Jptu3JVmDXGpJEaQO9ZrjRg5RuAVCo45OC2AcOKqbVZPmu0ZJPN3T1sm0cWx", page).enqueue(new Callback<CategoriesPaginated>() {
//            @Override
//            public void onResponse(Call<CategoriesPaginated> call, Response<CategoriesPaginated> response) {
//                try {
//                    if (response.body().getStatus() == 1) {
//                        maxPage = response.body().getData().getLastPage();
//                        listOfCategoryDatalist.addAll(response.body().getData().getData());
//                        restaurantCategoryFragmentRvCategory.setVisibility(View.VISIBLE);
//                        restaurantCategoryAdapter.notifyDataSetChanged();
//                    }
//                } catch (Exception e) {
//                    Log.d(TAG, "onResponse: ");
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CategoriesPaginated> call, Throwable t) {
//                Log.d(TAG, "onFailure: ");
//            }
//        });
//    }

//    public static void getSpinnerDataTown(Call<GeneralRequestSpinner> call, SpinnersAdapter spinnerAdapter, Spinner spinner, String hint) {
//
//        call.enqueue(new Callback<GeneralRequestSpinner>() {
//            @Override
//            public void onResponse(Call<GeneralRequestSpinner> call, Response<GeneralRequestSpinner> response) {
//
//                try {
//                    if (response.body().getStatus() == 1) {
//                        spinnerAdapter.setDataTown(response.body().getData().getData(), hint);
//                        spinner.setAdapter(spinnerAdapter);
//                    }
//                } catch (Exception e) {
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GeneralRequestSpinner> call, Throwable t) {
//
//            }
//        });
//    }

}
