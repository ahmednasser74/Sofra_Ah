package com.example.sofra.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sofra.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashCycleActivity extends BaseActivity {

    @BindView(R.id.splash_fragment_btn_make_order)
    Button splashFragmentBtnMakeOrder;
    @BindView(R.id.splash_fragment_btn_restaurant)
    Button splashFragmentBtnRestaurant;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_cycle);

        ButterKnife.bind(this);

    }

    @OnClick({R.id.splash_fragment_btn_make_order, R.id.splash_fragment_btn_restaurant})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.splash_fragment_btn_make_order:
                intent = new Intent(this, UserCycleActivity.class);
                startActivity(intent);
                break;
            case R.id.splash_fragment_btn_restaurant:
                intent = new Intent(this, RestaurantCycleActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
