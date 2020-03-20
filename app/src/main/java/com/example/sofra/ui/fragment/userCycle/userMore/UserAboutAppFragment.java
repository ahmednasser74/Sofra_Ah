package com.example.sofra.ui.fragment.userCycle.userMore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.sofra.R;
import com.example.sofra.ui.fragment.untitledFolder.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class UserAboutAppFragment extends BaseFragment {

    @BindView(R.id.img)
    ImageView img;

    public UserAboutAppFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        setUpActivity();
        View view = inflater.inflate(R.layout.fragment_user_about_app, container, false);
        ButterKnife.bind(this, view);
        Glide.with(getActivity()).load(R.drawable.about_us_background).into(img);

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
}
