package com.example.sofra.view.fragment.userCycle.userOrders;

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
import com.example.sofra.adapter.UserOrdersAdapter;
import com.example.sofra.data.model.userOrders.UserOrders;
import com.example.sofra.data.model.userOrders.UserOrdersData;
import com.example.sofra.helper.OnEndLess;
import com.example.sofra.view.activity.BaseActivity;
import com.example.sofra.view.fragment.untitledFolder.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.*;
import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.data.local.SharedPreference.loadRestaurantData;


public class UserOrdersFragment extends BaseFragment {

    @BindView(R.id.user_order_fragment_rv)
    RecyclerView userOrderFragmentRv;

    public String State;
    @BindView(R.id.user_order_fragment_pb_loading)
    ProgressBar userOrderFragmentPbLoading;

    private LinearLayoutManager linearLayoutManager;
    private OnEndLess onEndLess;
    private int maxPage = 0;
    private UserOrdersAdapter userOrdersAdapter;
    private List<UserOrdersData> userOrdersData = new ArrayList<>();

    public UserOrdersFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        setUpActivity();
        View view = inflater.inflate(R.layout.fragment_user_orders, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    public void init() {

        linearLayoutManager = new LinearLayoutManager(getActivity());
        userOrderFragmentRv.setLayoutManager(linearLayoutManager);

        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;
                        getOrderList(current_page);
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                }
            }
        };
        userOrderFragmentRv.addOnScrollListener(onEndLess);

        userOrdersAdapter = new UserOrdersAdapter((BaseActivity) getActivity(), userOrdersData, State);
        userOrderFragmentRv.setAdapter(userOrdersAdapter);
        getOrderList(1);
    }

    private void getOrderList(int page) {

        getClient().getUserOrders("HRbqKFSaq5ZpsOKITYoztpFZNylmzL9elnlAThxZSZ52QWqVBIj8Rdq7RhoB", State, page).enqueue(new Callback<UserOrders>() {
            @Override
            public void onResponse(Call<UserOrders> call, Response<UserOrders> response) {
                try {

                    if (response.body().getStatus() == 1) {
                        maxPage = response.body().getData().getLastPage();
                        userOrderFragmentRv.setVisibility(VISIBLE);
                        userOrderFragmentPbLoading.setVisibility(GONE);
                        userOrdersData.addAll(response.body().getData().getData());
                        userOrdersAdapter.notifyDataSetChanged();
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
