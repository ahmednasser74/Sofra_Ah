package com.example.sofra.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.sofra.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;

import java.util.Locale;

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
        LoadLocale();
        ButterKnife.bind(this);

        animationLR = AnimationUtils.loadAnimation(this, R.anim.animation_down_to_up);
        splashFragmentBtnCircleMenu.setAnimation(animationLR);

        splashFragmentBtnCircleMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                splashFragmentBtnCircleMenu.performClick();
            }
        });

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

    //
    @OnClick(R.id.splash_cycle_img_choose_language)
    public void onViewClicked() {
        showChangeLanguageDialog();
    }

    private void showChangeLanguageDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(SplashCycleActivity.this,
                R.style.BottomSheet);

        View bottomSheet = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.dialog_sheet_language,
                        (LinearLayout) findViewById(R.id.dialog_sheet_language_container));

        bottomSheet.findViewById(R.id.dialog_sheet_language_btn_eng).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocal("en");
                recreate();

            }
        });
        bottomSheet.findViewById(R.id.dialog_sheet_language_btn_arabic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocal("ar");
                recreate();

            }
        });
        bottomSheetDialog.setContentView(bottomSheet);
        bottomSheetDialog.show();


//        final String[] listItems = {"English", "Arabic"};
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(SplashCycleActivity.this);
//        builder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if (which == 0) {
//
//                } else if (which == 1) {
//
//                }
//                dialog.dismiss();
//            }
//        });
//
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
    }


    private void setLocal(String language) {
        Locale locale = new Locale(language);
        locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = getSharedPreferences("settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", language);
        editor.apply();
    }

    public void LoadLocale() {
        SharedPreferences prefs = getSharedPreferences("settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        setLocal(language);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
