package com.example.sofra.UI.fragment.resturantCycle.restaurantLogin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sofra.R;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.helper.MediaLoader;
import com.example.sofra.UI.fragment.untitledFolder.BaseFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RestaurantRegisterFragment2 extends BaseFragment {

    @BindView(R.id.restaurant_register2_fragment_et_phone)
    TextInputEditText restaurantRegister2FragmentEtPhone;
    @BindView(R.id.restaurant_register2_fragment_et_phone_whatsapp)
    TextInputEditText restaurantRegister2FragmentEtPhoneWhatsapp;
    @BindView(R.id.restaurant_register2_fragment_img_add_photo)
    ImageView restaurantRegister2FragmentImgAddPhoto;
    @BindView(R.id.restaurant_register2_fragment_btn_register)
    Button restaurantRegister2FragmentBtnRegister;
    private String path;

    public RestaurantRegisterFragment2() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        setUpActivity();
        View view = inflater.inflate(R.layout.fragment_restaurnat_register2, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    private void initImage() {
        Album.initialize(AlbumConfig.newBuilder(getActivity())
                .setAlbumLoader(new MediaLoader())
                .build());
        Album.image(this) // Image selection.
                .singleChoice()
                .camera(true)
                .columnCount(3)
                .onResult(new com.yanzhenjie.album.Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        path = result.get(0).getPath();
                        HelperMethod.onLoadImageFromUrl(restaurantRegister2FragmentImgAddPhoto, path, getActivity());
                    }
                })
                .onCancel(new com.yanzhenjie.album.Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                    }
                })
                .start();
    }

    @Override
    public void onBack() {
        super.onBack();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.restaurant_register2_fragment_img_add_photo, R.id.restaurant_register2_fragment_btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.restaurant_register2_fragment_img_add_photo:
                initImage();
                break;
            case R.id.restaurant_register2_fragment_btn_register:

                break;
        }
    }
}
