package com.example.sofra.UI.fragment.resturantCycle.restaurantOrders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofra.R;
import com.example.sofra.adapter.RestaurantOrdersAdapter;
import com.example.sofra.data.model.userOrders.UserOrders;
import com.example.sofra.data.model.userOrders.UserOrdersData;
import com.example.sofra.helper.OnEndLess;
import com.example.sofra.UI.activity.BaseActivity;
import com.example.sofra.UI.fragment.untitledFolder.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;


public class RestaurantOrdersFragment extends BaseFragment {

    @BindView(R.id.restaurant_order_container_fragment_rv_orders)
    RecyclerView restaurantOrderContainerFragmentRvOrders;
    @BindView(R.id.restaurant_order_container_fragment_progress)
    ProgressBar restaurantOrderContainerFragmentProgress;

    private OnEndLess onEndLess;
    private int maxPage = 0;
    public String State;
    private List<UserOrdersData> userOrdersData = new ArrayList<>();
    private RestaurantOrdersAdapter restaurantOrdersAdapter;

    public RestaurantOrdersFragment() {
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

        View view = inflater.inflate(R.layout.fragment_restaurant_orders, container, false);
        ButterKnife.bind(this, view);

        init();
        return view;
    }

    private void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        restaurantOrderContainerFragmentRvOrders.setLayoutManager(linearLayoutManager);

        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;
                        getRestaurantOrders(current_page);
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                }
            }
        };
        restaurantOrderContainerFragmentRvOrders.addOnScrollListener(onEndLess);

        restaurantOrdersAdapter = new RestaurantOrdersAdapter((BaseActivity) getActivity(), userOrdersData, State);
        restaurantOrderContainerFragmentRvOrders.setAdapter(restaurantOrdersAdapter);
        getRestaurantOrders(1);
    }


    private void getRestaurantOrders(int page) {

// LoadData(getActivity(), RESTAURANT_API_TOKEN)

        getClient().getRestaurantOrders("Jptu3JVmDXGpJEaQO9ZrjRg5RuAVCo45OC2AcOKqbVZPmu0ZJPN3T1sm0cWx", State, page).enqueue(new Callback<UserOrders>() {
            @Override
            public void onResponse(Call<UserOrders> call, Response<UserOrders> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        maxPage = response.body().getData().getLastPage();
                        restaurantOrderContainerFragmentProgress.setVisibility(View.GONE);
                        userOrdersData.addAll(response.body().getData().getData());
                        restaurantOrdersAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<UserOrders> call, Throwable t) {

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
