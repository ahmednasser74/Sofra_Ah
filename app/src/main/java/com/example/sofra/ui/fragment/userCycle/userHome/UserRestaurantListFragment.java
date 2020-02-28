package com.example.sofra.ui.fragment.userCycle.userHome;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.sofra.R;
import com.example.sofra.adapter.RestaurantListAdapter;
import com.example.sofra.adapter.SpinnersAdapter;
import com.example.sofra.data.model.GeneralRequestSpinner;
import com.example.sofra.data.model.restaurantList.RestaurantList;
import com.example.sofra.data.model.restaurantListWithFilter.RestaurantListWithFilter;
import com.example.sofra.data.model.restaurantLogin.Restaurant;
import com.example.sofra.helper.InternetState;
import com.example.sofra.helper.OnEndLess;
import com.example.sofra.ui.activity.BaseActivity;
import com.example.sofra.ui.fragment.untitledFolder.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;
import static com.example.sofra.data.api.ApiClient.getClient;


public class UserRestaurantListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.user_restaurant_list_fragment_sp_city)
    Spinner restaurantListFragmentSpCity;
    @BindView(R.id.user_restaurant_list_fragment_rv_restaurant)
    RecyclerView restaurantListFragmentRvRestaurant;
    @BindView(R.id.user_restaurant_list_fragment_pb_loading)
    ProgressBar restaurantListFragmentPbLoading;
    @BindView(R.id.user_restaurant_list_fragment_swipe_refresh)
    SwipeRefreshLayout restaurantListFragmentSwipeRefresh;
    @BindView(R.id.user_restaurant_list_fragment_pagination_progress)
    ProgressBar userRestaurantListFragmentPaginationProgress;
    @BindView(R.id.user_restaurant_list_fragment_no_result)
    TextView userRestaurantListFragmentNoResult;
    @BindView(R.id.user_restaurant_list_fragment_img_search)
    ImageView userRestaurantListFragmentImgSearch;
    @BindView(R.id.user_restaurant_list_fragment_et_search)
    EditText userRestaurantListFragmentEtSearch;

    private RestaurantListAdapter restaurantListAdapter;
    private List<Restaurant> ListRestaurantData = new ArrayList<>();
    private OnEndLess onEndLess;
    private int maxPage = 0;
    private boolean FILTER = false;
    private SpinnersAdapter citySpinnerAdapter;
    private LinearLayoutManager linearLayoutManager;


    public UserRestaurantListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setUpActivity();
        View view = inflater.inflate(R.layout.fragment_user_restaurant_list, container, false);
        ButterKnife.bind(this, view);

        init();
        return view;
    }

    private void initFilter() {

        String search = userRestaurantListFragmentEtSearch.getText().toString().trim();

        getClient().getRestaurantListWithFilter(search, citySpinnerAdapter.selectedId).enqueue(new Callback<RestaurantListWithFilter>() {
            @Override
            public void onResponse(Call<RestaurantListWithFilter> call, Response<RestaurantListWithFilter> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        maxPage = response.body().getData().getLastPage();
                        ListRestaurantData.clear();
                        restaurantListFragmentSwipeRefresh.setRefreshing(false);
                        ListRestaurantData.addAll(response.body().getData().getData());
                        restaurantListAdapter.notifyDataSetChanged();

                        if (Objects.requireNonNull(restaurantListFragmentRvRestaurant.getAdapter()).getItemCount() == 0) {
                            userRestaurantListFragmentNoResult.setVisibility(View.VISIBLE);
                            restaurantListFragmentRvRestaurant.setVisibility(View.GONE);
                        }

                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<RestaurantListWithFilter> call, Throwable t) {
            }
        });
    }

    public void init() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        restaurantListFragmentRvRestaurant.setLayoutManager(linearLayoutManager);

        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        if ((ListRestaurantData.size() + 1) / 10 != current_page) {
                            if (FILTER) {
//                                onFilter(current_page);
                                onEndLess.previous_page = current_page;
                            } else {
                                getRestaurantList(current_page);
                                onEndLess.previous_page = current_page;
                                userRestaurantListFragmentPaginationProgress.setVisibility(View.VISIBLE);
                            }
                        }
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                }
            }
        };

        restaurantListFragmentRvRestaurant.addOnScrollListener(onEndLess);

        citySpinnerAdapter = new SpinnersAdapter(getActivity());
        GeneralRequestSpinner.getSpinnerCityData2(getClient().getCity(), citySpinnerAdapter
                , restaurantListFragmentSpCity, "City");

        if (ListRestaurantData.size() == 0) {
            restaurantListAdapter = new RestaurantListAdapter((BaseActivity) getActivity(), ListRestaurantData);
            restaurantListFragmentRvRestaurant.setAdapter(restaurantListAdapter);
            getRestaurantList(1);
        } else {
            restaurantListFragmentRvRestaurant.setAdapter(restaurantListAdapter);
            restaurantListFragmentRvRestaurant.setVisibility(View.VISIBLE);
        }
        restaurantListFragmentSwipeRefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        getRestaurantList(1);
                    }
                }
        );
    }

//    private void onFilter(int page) {
//        keyword = restaurantListFragmentEtCity.getText().toString().trim();
//
//        FILTER = true;
//
//        Call<RestaurantList> call = getClient().getRestaurantListWithFilet(keyword, citySpinnerAdapter.selectedId);
//
//        startCall(call, page);
//    }
//
//    private void startCall(Call<RestaurantList> call, int page) {
//        if (page == 1) {
//            reInit();
//            userRestaurantListFragmentNoResult.setVisibility(View.GONE);
//        } else {
//            userRestaurantListFragmentNoResult.setVisibility(View.VISIBLE);
//        }
//    }
//
//    private void reInit() {
//        onEndLess.previousTotal = 0;
//        onEndLess.current_page = 1;
//        onEndLess.previous_page = 1;
//        restaurantListAdapter = new RestaurantListAdapter((BaseActivity) getActivity(), ListRestaurantData);
//        restaurantListFragmentRvRestaurant.setAdapter(restaurantListAdapter);
//    }

    private void getRestaurantList(int page) {
        if (InternetState.isConnected(getActivity())) {
            getClient().getRestaurant(page).enqueue(new Callback<RestaurantList>() {
                @Override
                public void onResponse(Call<RestaurantList> call, Response<RestaurantList> response) {
                    try {
                        if (response.body().getStatus() == 1) {
                            maxPage = response.body().getData().getLastPage();
                            ListRestaurantData.addAll(response.body().getData().getData());
                            restaurantListFragmentSwipeRefresh.setRefreshing(false);
                            restaurantListFragmentRvRestaurant.setVisibility(View.VISIBLE);
                            restaurantListFragmentPbLoading.setVisibility(View.GONE);
                            restaurantListAdapter.notifyDataSetChanged();
                            userRestaurantListFragmentPaginationProgress.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                    }
                }

                @Override
                public void onFailure(Call<RestaurantList> call, Throwable t) {
                    Log.d(TAG, "onFailure: ");
                }
            });
        } else {
            Toast.makeText(baseActivity, "Check internet connection", Toast.LENGTH_LONG).show();
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

    @Override
    public void onRefresh() {

    }

    @OnClick(R.id.user_restaurant_list_fragment_img_search)
    public void onViewClicked() {
        initFilter();
    }
}
