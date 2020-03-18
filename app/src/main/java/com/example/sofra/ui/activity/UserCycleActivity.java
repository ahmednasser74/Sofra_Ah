package com.example.sofra.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.sofra.data.local.room.OrderItem;
import com.example.sofra.data.local.room.RoomDao;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.ui.fragment.userCycle.UserNotificationListFragment;
import com.example.sofra.ui.fragment.userCycle.userEditProfile.UserEditProfileFragment;
import com.example.sofra.ui.fragment.userCycle.userHome.UserRestaurantListFragment;
import com.example.sofra.ui.fragment.userCycle.userHome.userRestaurantMenu.ShoppingCartFragment;
import com.example.sofra.ui.fragment.userCycle.userMore.UserMoreFragment;
import com.example.sofra.ui.fragment.userCycle.userOrders.UserOrdersContainerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.sofra.data.local.room.RoomManger.getInstance;
import static com.example.sofra.helper.HelperMethod.replace;

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

    List<OrderItem> orderItems;
    RoomDao roomDao;
    ShoppingCartFragment shoppingCartFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cycle);
        ButterKnife.bind(this);

        HelperMethod.replace(new UserRestaurantListFragment(), getSupportFragmentManager(),
                R.id.user_cycle_activity_fl_container, null, null);
        HelperMethod.disappearKeypad(this, userCycleActivityFlContainer);

        shoppingCartFragment = new ShoppingCartFragment();
        roomDao = getInstance(UserCycleActivity.this).roomDao();
        initNavigation();
        internetConnection();
    }

    private void internetConnection() {
        if (haveNetworkConnection()) {
            userCycleActivityLayoutNoConnection.setVisibility(View.GONE);
            userCycleActivityFlContainer.setVisibility(View.VISIBLE);
        } else if (!haveNetworkConnection()) {
            userCycleActivityLayoutNoConnection.setVisibility(View.VISIBLE);
            userCycleActivityFlContainer.setVisibility(View.GONE);
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

    @OnClick({R.id.user_cycle_activity_btn_rety, R.id.user_cycle_activity_fl_container, R.id.user_cycle_activity_img_shopping_cart, R.id.user_cycle_activity_img_notification})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_cycle_activity_btn_rety:
                if (haveNetworkConnection()) {
                    userCycleActivityLayoutNoConnection.setVisibility(View.GONE);
                    userCycleActivityFlContainer.setVisibility(View.VISIBLE);
                } else if (!haveNetworkConnection()) {
                    userCycleActivityLayoutNoConnection.setVisibility(View.VISIBLE);
                    userCycleActivityFlContainer.setVisibility(View.GONE);
                }
                break;
            case R.id.user_cycle_activity_img_notification:
                HelperMethod.replace(new UserNotificationListFragment(), getSupportFragmentManager(),
                        R.id.user_cycle_activity_fl_container, null, null);
                break;

            case R.id.user_cycle_activity_img_shopping_cart:

                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        orderItems = roomDao.getAll();
                        shoppingCartFragment.listOrderItem = orderItems;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                HelperMethod.replace(shoppingCartFragment, getSupportFragmentManager(),
                                        R.id.user_cycle_activity_fl_container, null, null);
                            }
                        });
                    }
                });
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
