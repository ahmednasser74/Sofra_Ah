package com.example.sofra.view.fragment.resturantCycle.resaurantMore;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sofra.R;
import com.example.sofra.data.local.SharedPreference;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.view.activity.SplashCycleActivity;
import com.example.sofra.view.fragment.resturantCycle.resaurantMore.offers.RestaurantOffersFragment;
import com.example.sofra.view.fragment.untitledFolder.BaseFragment;
import com.example.sofra.view.fragment.userCycle.userMore.UserAboutAppFragment;
import com.example.sofra.view.fragment.userCycle.userMore.UserContactUsFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class RestaurantMoreFragment extends BaseFragment {

    public RestaurantMoreFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        setUpActivity();
        View view = inflater.inflate(R.layout.fragment_restaurant_more, container, false);
        ButterKnife.bind(this, view);


        return view;
    }


    @Override
    public void onBack() {
        super.onBack();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.restaurant_more_fragment_fragment_offers, R.id.restaurant_more_fragment_fragment_contact_us, R.id.restaurant_more_fragment_fragment_about_app, R.id.restaurant_more_fragment_fragment_rating, R.id.restaurant_more_fragment_fragment_change_password, R.id.restaurant_more_fragment_sign_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.restaurant_more_fragment_fragment_offers:
                HelperMethod.replace(new RestaurantOffersFragment(), getActivity().getSupportFragmentManager(),
                        R.id.restaurant_cycle_fl_fragment_container, null, null);
                break;
            case R.id.restaurant_more_fragment_fragment_contact_us:
                HelperMethod.replace(new UserContactUsFragment(), getActivity().getSupportFragmentManager(),
                        R.id.restaurant_cycle_fl_fragment_container, null, null);
                break;
            case R.id.restaurant_more_fragment_fragment_about_app:
                HelperMethod.replace(new UserAboutAppFragment(), getActivity().getSupportFragmentManager(),
                        R.id.restaurant_cycle_fl_fragment_container, null, null);
                break;
            case R.id.restaurant_more_fragment_fragment_rating:
                HelperMethod.replace(new RestaurantCommentsFragment(), getActivity().getSupportFragmentManager(),
                        R.id.restaurant_cycle_fl_fragment_container, null, null);
                break;
            case R.id.restaurant_more_fragment_fragment_change_password:
                HelperMethod.replace(new RestaurantChangePasswordFragment(), getActivity().getSupportFragmentManager(),
                        R.id.restaurant_cycle_fl_fragment_container, null, null);                break;
            case R.id.restaurant_more_fragment_sign_out:

                Dialog dialog = new Dialog(getActivity());
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = inflater.inflate(R.layout.dialog_sign_out, null);
                dialog.setContentView(v);

                Button button = (Button) dialog.findViewById(R.id.item_sign_out_dialog_btn_yes);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), SplashCycleActivity.class);
                        getActivity().startActivity(intent);
                        getActivity().finish();
                        SharedPreference.Clean(getActivity());
                        Toast.makeText(baseActivity, "You Have LogOut", Toast.LENGTH_SHORT).show();
                    }
                });
                Button btn_done = (Button) dialog.findViewById(R.id.item_sign_out_dialog_btn_no);
                btn_done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

                break;
        }
    }
}
