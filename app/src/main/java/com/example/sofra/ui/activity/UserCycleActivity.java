package com.example.sofra.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.sofra.R;
import com.example.sofra.data.local.room.OrderItem;
import com.example.sofra.data.local.room.RoomDao;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.ui.fragment.resturantCycle.resaurantMore.RestaurantChangePasswordFragment;
import com.example.sofra.ui.fragment.userCycle.UserNotificationListFragment;
import com.example.sofra.ui.fragment.userCycle.userEditProfile.UserEditProfileFragment;
import com.example.sofra.ui.fragment.userCycle.userHome.UserRestaurantListFragment;
import com.example.sofra.ui.fragment.userCycle.userHome.userRestaurantMenu.ShoppingCartFragment;
import com.example.sofra.ui.fragment.userCycle.userMore.UserAboutAppFragment;
import com.example.sofra.ui.fragment.userCycle.userMore.UserContactUsFragment;
import com.example.sofra.ui.fragment.userCycle.userMore.UserMoreFragment;
import com.example.sofra.ui.fragment.userCycle.userMore.offer.UserOffersFragment;
import com.example.sofra.ui.fragment.userCycle.userOrders.UserOrdersContainerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

import static com.example.sofra.data.local.SharedPreference.Clean;
import static com.example.sofra.data.local.room.RoomManger.getInstance;

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

    @BindView(R.id.user_cycle_activity_tv_badge_counter)
    TextView userCycleActivityTvBadgeCounter;
    @BindView(R.id.user_cycle_activity_rl_badge)
    RelativeLayout userCycleActivityRlBadge;

    List<OrderItem> orderItems;
    RoomDao roomDao;
    ShoppingCartFragment shoppingCartFragment;
    @BindView(R.id.activity_user_bottom_fab_menu)
    FabSpeedDial activityUserBottomFabMenu;
    private List<OrderItem> listOrderItem = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cycle);
        ButterKnife.bind(this);

        HelperMethod.replace(new UserRestaurantListFragment(), getSupportFragmentManager(),
                R.id.user_cycle_activity_fl_container, null, null);
        HelperMethod.disappearKeypad(this, userCycleActivityFlContainer);

        shoppingCartFragment = new ShoppingCartFragment();

        if (listOrderItem.size() == 0) {
            userCycleActivityRlBadge.setVisibility(View.GONE);
        }
        if (listOrderItem.size() != 0) {
            userCycleActivityRlBadge.setVisibility(View.VISIBLE);
//            userCycleActivityTvBadgeCounter.setText(roomDao.getAll().get(0).getQuantity());
        }

        roomDao = getInstance(UserCycleActivity.this).roomDao();
        initFabMenu();
        initNavigation();
        internetConnection();
    }

    private void initFabMenu() {
        activityUserBottomFabMenu.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id._fab_nav_offers:
                        HelperMethod.replaceFragmentWithAnimation(UserCycleActivity.this.getSupportFragmentManager(),
                                R.id.user_cycle_activity_fl_container, new UserOffersFragment(), "r");
                        break;
                    case R.id._fab_nav_contact_us:
                        HelperMethod.replace(new UserContactUsFragment(), UserCycleActivity.this.getSupportFragmentManager(),
                                R.id.user_cycle_activity_fl_container, null, null);
                        break;
                    case R.id._fab_nav_about_app:
                        HelperMethod.replace(new UserAboutAppFragment(), UserCycleActivity.this.getSupportFragmentManager(),
                                R.id.user_cycle_activity_fl_container, null, null);
                        break;
                    case R.id._fab_nav_change_password:
                        HelperMethod.replace(new RestaurantChangePasswordFragment(), UserCycleActivity.this.getSupportFragmentManager(),
                                R.id.user_cycle_activity_fl_container, null, null);
                        break;
                    case R.id._fab_nav_log_out:
                        dialogLogOut();
//                        Clean(UserCycleActivity.this);
                        break;
                }
                return true;
            }
        });
    }

    private void dialogLogOut() {
        Dialog dialog = new Dialog(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.dialog_sign_out, null);
        dialog.setContentView(v);

        Button button = (Button) dialog.findViewById(R.id.item_sign_out_dialog_btn_yes);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserCycleActivity.this, SplashCycleActivity.class);
                UserCycleActivity.this.startActivity(intent);
                UserCycleActivity.this.finish();
                Toast.makeText(UserCycleActivity.this, "You Have LogOut", Toast.LENGTH_SHORT).show();
            }
        });
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
