package com.example.sofra.UI.fragment.userCycle.userOrders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.example.sofra.R;
import com.example.sofra.adapter.ViewPagerFragmentAdapter;
import com.example.sofra.UI.activity.UserCycleActivity;
import com.example.sofra.UI.fragment.untitledFolder.BaseFragment;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;


public class UserOrdersContainerFragment extends BaseFragment {


    @BindView(R.id.orders_conatiner_fragment_tl)
    TabLayout ordersConatinerFragmentTl;
    @BindView(R.id.orders_conatiner_fragment_vp)
    ViewPager ordersContainerFragmentVp;

    private UserCycleActivity userCycleActivity = new UserCycleActivity();
    UserOrdersFragment userOrdersFragment, userCurrentOrdersFragment, userCompletedOrdersFragment;

    public UserOrdersContainerFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        setUpActivity();
        View view = inflater.inflate(R.layout.fragment_user_orders_container, container, false);
        ButterKnife.bind(this, view);
        userCycleActivity = (UserCycleActivity) getActivity();

        initViewPager();
        return view;
    }

    private void initViewPager() {

        userOrdersFragment = new UserOrdersFragment();
        userOrdersFragment.State = "pending";
        userCurrentOrdersFragment = new UserOrdersFragment();
        userCurrentOrdersFragment.State = "current";
        userCompletedOrdersFragment = new UserOrdersFragment();
        userCompletedOrdersFragment.State = "completed";

        ViewPagerFragmentAdapter viewPagerFragmentAdapter = new ViewPagerFragmentAdapter(getChildFragmentManager());
        viewPagerFragmentAdapter.addPager(userOrdersFragment, "new Orders");
        viewPagerFragmentAdapter.addPager(userCurrentOrdersFragment, "Current Orders");
        viewPagerFragmentAdapter.addPager(userCompletedOrdersFragment, "Previous Orders");

        ordersContainerFragmentVp.setAdapter(viewPagerFragmentAdapter);
        ordersConatinerFragmentTl.setupWithViewPager(ordersContainerFragmentVp);
    }

    @Override
    public void onBack() {
        super.onBack();
        userCycleActivity.setSelection(R.id.nav_home);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
