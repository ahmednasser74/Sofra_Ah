package com.example.sofra.UI.fragment.resturantCycle.restaurantOrders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.example.sofra.R;
import com.example.sofra.adapter.ViewPagerFragmentAdapter;
import com.example.sofra.UI.fragment.untitledFolder.BaseFragment;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RestaurantOrderContainerFragment extends BaseFragment {

    @BindView(R.id.restaurant_order_container_fragment_tl)
    TabLayout restaurantOrderContainerFragmentTl;
    @BindView(R.id.restaurant_order_container_fragment_vp)
    ViewPager restaurantOrderContainerFragmentVp;

    RestaurantOrdersFragment restaurantNewOrdersFragment, restaurantCurrentOrdersFragment, restaurantCompletedOrdersFragment;

    public RestaurantOrderContainerFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        setUpActivity();
        View view = inflater.inflate(R.layout.fragment_restaurant_order_container, container, false);
        ButterKnife.bind(this, view);

        initViewPager();
        return view;
    }

    private void initViewPager() {

        restaurantNewOrdersFragment = new RestaurantOrdersFragment();
        restaurantCurrentOrdersFragment = new RestaurantOrdersFragment();
        restaurantCompletedOrdersFragment = new RestaurantOrdersFragment();
        restaurantNewOrdersFragment.State = "pending";
        restaurantCurrentOrdersFragment.State = "current";
        restaurantCompletedOrdersFragment.State = "completed";

        ViewPagerFragmentAdapter viewPagerFragmentAdapter = new ViewPagerFragmentAdapter(getChildFragmentManager());
        viewPagerFragmentAdapter.addPager(restaurantNewOrdersFragment, "new order");
        viewPagerFragmentAdapter.addPager(restaurantCurrentOrdersFragment, "current order");
        viewPagerFragmentAdapter.addPager(restaurantCompletedOrdersFragment, "previous order");
        restaurantOrderContainerFragmentVp.setAdapter(viewPagerFragmentAdapter);
        restaurantOrderContainerFragmentTl.setupWithViewPager(restaurantOrderContainerFragmentVp);
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
