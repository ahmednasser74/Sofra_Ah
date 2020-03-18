package com.example.sofra.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.example.sofra.R;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashCycleActivity extends BaseActivity {

    //    @BindView(R.id.splash_fragment_btn_make_order)
//    Button splashFragmentBtnMakeOrder;
//    @BindView(R.id.splash_fragment_btn_restaurant)
//    Button splashFragmentBtnRestaurant;
    @BindView(R.id.splash_fragment_btn_circle_menu)
    CircleMenu splashFragmentBtnCircleMenu;

    Animation animationLR;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_cycle);
        ButterKnife.bind(this);

        animationLR = AnimationUtils.loadAnimation(this, R.anim.animation_down_to_up);
        splashFragmentBtnCircleMenu.setAnimation(animationLR);

        String[] menu = {"Sale food", "Make Order"};
        splashFragmentBtnCircleMenu.setMainMenu(Color.parseColor("#ffffff"),
                R.drawable.logo, R.drawable.ic_pink_close)
                .addSubMenu(Color.parseColor("#DD1258"), R.drawable.ic_white_shopping_cart)
                .addSubMenu(Color.parseColor("#DD1258"), R.drawable.ic_white_coins_money)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {
                    @Override
                    public void onMenuSelected(int index) {
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                switch (index) {
                                    case 0:
                                        intent = new Intent(SplashCycleActivity.this, UserCycleActivity.class);
                                        startActivity(intent);
                                        break;
                                    case 1:
                                        intent = new Intent(SplashCycleActivity.this, RestaurantCycleActivity.class);
                                        startActivity(intent);
                                        break;
                                }
                            }
                        }, 800);


                        Toast.makeText(SplashCycleActivity.this, menu[index], Toast.LENGTH_SHORT).show();
                    }
                });
    }

//    @OnClick({R.id.splash_fragment_btn_make_order, R.id.splash_fragment_btn_restaurant})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.splash_fragment_btn_make_order:
//                intent = new Intent(this, UserCycleActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.splash_fragment_btn_restaurant:
//                intent = new Intent(this, RestaurantCycleActivity.class);
//                startActivity(intent);
//                break;
//        }
//    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
