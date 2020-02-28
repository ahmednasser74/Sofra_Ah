package com.example.sofra.UI.activity;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.example.sofra.R;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.UI.fragment.userCycle.UserNotificationListFragment;
import com.example.sofra.UI.fragment.userCycle.userEditProfile.UserEditProfileFragment;
import com.example.sofra.UI.fragment.userCycle.userHome.UserRestaurantListFragment;
import com.example.sofra.UI.fragment.userCycle.userMore.UserMoreFragment;
import com.example.sofra.UI.fragment.userCycle.userOrders.UserOrdersContainerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserCycleActivity extends BaseActivity {

    @BindView(R.id.activity_user_bottom_nav_bar)
    BottomNavigationView activityUserBottomNavBar;
    @BindView(R.id.user_cycle_activity_img_shopping_cart)
    ImageView userCycleActivityImgShoppingCart;
    @BindView(R.id.user_cycle_activity_img_notification)
    ImageView userCycleActivityImgNotification;
    @BindView(R.id.user_cycle_activity_btn_rety)
    Button userCycleActivityBtnRety;
    @BindView(R.id.user_cycle_activity_layout_no_connection)
    LinearLayout userCycleActivityLayoutNoConnection;
    @BindView(R.id.user_cycle_activity_fl_container)
    FrameLayout userCycleActivityFlContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cycle);
        ButterKnife.bind(this);

        HelperMethod.replace(new UserRestaurantListFragment(), getSupportFragmentManager(),
                R.id.user_cycle_activity_fl_container, null, null);
        HelperMethod.disappearKeypad(this, userCycleActivityFlContainer);

        initNavigation();
        internetConnection();

    }

    private void internetConnection() {
        if (haveNetworkConnection()) {
            userCycleActivityLayoutNoConnection.setVisibility(View.GONE);
            userCycleActivityFlContainer.setVisibility(View.VISIBLE);
        } else {
            userCycleActivityLayoutNoConnection.setVisibility(View.VISIBLE);
            userCycleActivityFlContainer.setVisibility(View.GONE);
            userCycleActivityBtnRety.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    private void initNavigation() {
        activityUserBottomNavBar = findViewById(R.id.activity_user_bottom_nav_bar);
        activityUserBottomNavBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        HelperMethod.replace(new UserRestaurantListFragment(), getSupportFragmentManager(),
                                R.id.user_cycle_activity_fl_container, null, null);
                        break;
                    case R.id.nav_profile:
                        HelperMethod.replace(new UserEditProfileFragment(), getSupportFragmentManager(),
                                R.id.user_cycle_activity_fl_container, null, null);
                        break;
                    case R.id.nav_orders:
                        HelperMethod.replace(new UserOrdersContainerFragment(), getSupportFragmentManager(),
                                R.id.user_cycle_activity_fl_container, null, null);
                        break;
                    case R.id.nav_more:
                        HelperMethod.replace(new UserMoreFragment(), getSupportFragmentManager(),
                                R.id.user_cycle_activity_fl_container, null, null);
                        break;
                }
                return true;
            }
        });


    }

    @OnClick({R.id.user_cycle_activity_fl_container, R.id.user_cycle_activity_img_shopping_cart, R.id.user_cycle_activity_img_notification})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_cycle_activity_img_notification:
                HelperMethod.replace(new UserNotificationListFragment(), getSupportFragmentManager(),
                        R.id.user_cycle_activity_fl_container, null, null);
                break;

            case R.id.user_cycle_activity_img_shopping_cart:
                break;

            case R.id.user_cycle_activity_fl_container:
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                /**this to dismiss keyboard when touch background*/
                break;
        }
    }

    public void setSelection(int id) {
        activityUserBottomNavBar.setSelectedItemId(id);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
