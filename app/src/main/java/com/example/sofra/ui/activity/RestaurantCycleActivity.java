package com.example.sofra.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.example.sofra.R;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.ui.fragment.resturantCycle.RestaurantCommissionFragment;
import com.example.sofra.ui.fragment.resturantCycle.resaurantMore.RestaurantMoreFragment;
import com.example.sofra.ui.fragment.resturantCycle.restaurantEditProfile.RestaurantEditProfileFragment;
import com.example.sofra.ui.fragment.resturantCycle.restaurantHome.RestaurantCategoryFragment;
import com.example.sofra.ui.fragment.resturantCycle.restaurantLogin.RestaurantLoginFragment;
import com.example.sofra.ui.fragment.resturantCycle.restaurantOrders.RestaurantOrderContainerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RestaurantCycleActivity extends BaseActivity {

    @BindView(R.id.restaurant_cycle_activity_img_commission)
    ImageView restaurantCycleActivityImgShoppingCart;
    @BindView(R.id.restaurant_cycle_activity_img_notification)
    ImageView restaurantCycleActivityImgNotification;
    @BindView(R.id.restaurant_cycle_fl_fragment_container)
    FrameLayout restaurantCycleFlFragmentContainer;
    @BindView(R.id.activity_restaurant_bottom_nav_bar)
    BottomNavigationView activityRestaurantBottomNavBar;
    @BindView(R.id.restaurant_cycle_activity_tool_bar)
    Toolbar restaurantCycleActivityToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_cycle);
        ButterKnife.bind(this);

        HelperMethod.replace(new RestaurantLoginFragment(), getSupportFragmentManager(),
                R.id.restaurant_cycle_fl_fragment_container, null, null);

//        try {
//            Badges.setBadge(this,50);
//        } catch (BadgesNotSupportedException e) {
//            e.printStackTrace();
//        }

        initBottomNavigation();
    }



    private void initBottomNavigation() {
        activityRestaurantBottomNavBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        HelperMethod.replace(new RestaurantCategoryFragment(), getSupportFragmentManager(),
                                R.id.restaurant_cycle_fl_fragment_container, null, null);
                        break;
                    case R.id.nav_profile:
                        HelperMethod.replace(new RestaurantEditProfileFragment(), getSupportFragmentManager(),
                                R.id.restaurant_cycle_fl_fragment_container, null, null);
                        break;
                    case R.id.nav_orders:
                        HelperMethod.replace(new RestaurantOrderContainerFragment(), getSupportFragmentManager(),
                                R.id.restaurant_cycle_fl_fragment_container, null, null);
                        break;
                    case R.id.nav_more:
                        HelperMethod.replace(new RestaurantMoreFragment(), getSupportFragmentManager(),
                                R.id.restaurant_cycle_fl_fragment_container, null, null);
                        break;
                }
                return true;
            }
        });

    }

    public void setVisibilityBottomNavBar(int visibility) {
        activityRestaurantBottomNavBar.setVisibility(visibility);
    }

    public void setVisibilityToolBar(int visibility) {
        restaurantCycleActivityToolBar.setVisibility(visibility);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick({R.id.restaurant_cycle_activity_img_commission, R.id.restaurant_cycle_activity_img_notification})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.restaurant_cycle_activity_img_commission:
                HelperMethod.replace(new RestaurantCommissionFragment(), getSupportFragmentManager(),
                        R.id.restaurant_cycle_fl_fragment_container, null, null);
                break;
            case R.id.restaurant_cycle_activity_img_notification:
//                View target = findViewById(R.id.target_view);
//                BadgeView badge = new BadgeView(this, target);
//                badge.setText("1");
//                badge.show();

                break;
            case R.id.restaurant_cycle_fl_fragment_container:
//                this to dismiss keyboard when touch background
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                break;
        }
    }
}