package com.example.sofra.view.fragment.resturantCycle.restaurantEditProfile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sofra.R;
import com.example.sofra.adapter.SpinnersAdapter;
import com.example.sofra.data.local.SharedPreference;
import com.example.sofra.data.model.GeneralRequestSpinner;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.helper.MediaLoader;
import com.example.sofra.view.fragment.untitledFolder.BaseFragment;
import com.google.android.material.textfield.TextInputLayout;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.data.api.Keys.RESTAURANT_DELIVERY_COST;
import static com.example.sofra.data.api.Keys.RESTAURANT_DELIVERY_TIME;
import static com.example.sofra.data.api.Keys.RESTAURANT_MAIL;
import static com.example.sofra.data.api.Keys.RESTAURANT_MINIMUM_CHARGER;
import static com.example.sofra.data.api.Keys.RESTAURANT_PHONE;
import static com.example.sofra.data.api.Keys.RESTAURANT_USER_NAME;
import static com.example.sofra.data.api.Keys.RESTAURANT_WHATS_APP;
import static com.example.sofra.data.local.SharedPreference.RESTAURANT_DATA;


public class RestaurantEditProfileFragment extends BaseFragment {

    @BindView(R.id.restaurant_edit_profile_fragment_add_photo)
    CircleImageView restaurantEditProfileFragmentAddPhoto;
    @BindView(R.id.restaurant_edit_profile_fragment_et_name)
    TextInputLayout restaurantEditProfileFragmentEtName;
    @BindView(R.id.restaurant_edit_profile_fragment_et_mail)
    TextInputLayout restaurantEditProfileFragmentEtMail;
    @BindView(R.id.restaurant_edit_profile_fragment_sp_governorate)
    Spinner restaurantEditProfileFragmentSpGovernorate;
    @BindView(R.id.restaurant_edit_profile_fragment_sp_city)
    Spinner restaurantEditProfileFragmentSpCity;
    @BindView(R.id.restaurant_edit_profile_fragment_et_minimum_delivery)
    TextInputLayout restaurantEditProfileFragmentEtMinimumDelivery;
    @BindView(R.id.restaurant_edit_profile_fragment_btn_edit)
    Button restaurantEditProfileFragmentBtnEdit;
    @BindView(R.id.restaurant_edit_profile_fragment_et_whatsapp)
    TextInputLayout restaurantEditProfileFragmentEtWhatsapp;
    @BindView(R.id.restaurant_edit_profile_fragment_et_phone)
    TextInputLayout restaurantEditProfileFragmentEtPhone;
    @BindView(R.id.restaurant_edit_profile_fragment_et__duration_delivery)
    TextInputLayout restaurantEditProfileFragmentEtDurationDelivery;
    @BindView(R.id.restaurant_edit_profile_fragment_et_delivery_cost)
    TextInputLayout restaurantEditProfileFragmentEtDeliveryCost;
    @BindView(R.id.restaurant_edit_profile_fragment_switch)
    Switch restaurantEditProfileFragmentSwitch;
    @BindView(R.id.restaurant_edit_profile_fragment_tv_state)
    TextView restaurantEditProfileFragmentTvState;

    private String path;
    private SpinnersAdapter cityAdapter, townAdapter;

    public RestaurantEditProfileFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        setUpActivity();
        View view = inflater.inflate(R.layout.fragment_restaurant_edit_profile, container, false);
        ButterKnife.bind(this, view);

        cityAdapter = new SpinnersAdapter(getActivity());
        townAdapter = new SpinnersAdapter(getActivity());

        GeneralRequestSpinner.getSpinnerCityData(getClient().getCity(), cityAdapter
                , restaurantEditProfileFragmentSpCity, "City", new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i != 0) {
                            GeneralRequestSpinner.getTownSpinnerData(getClient().getTown(cityAdapter.selectedId), townAdapter
                                    , restaurantEditProfileFragmentSpGovernorate, "Town");
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

        setData();
        return view;
    }

    private void setData() {
        restaurantEditProfileFragmentEtName.getEditText().setText(SharedPreference.LoadData(getActivity(), RESTAURANT_DATA));
        restaurantEditProfileFragmentEtMail.getEditText().setText(SharedPreference.LoadData(getActivity(), RESTAURANT_MAIL));
        restaurantEditProfileFragmentEtMinimumDelivery.getEditText().setText(SharedPreference.LoadData(getActivity(), RESTAURANT_MINIMUM_CHARGER));
        restaurantEditProfileFragmentEtPhone.getEditText().setText(SharedPreference.LoadData(getActivity(), RESTAURANT_PHONE));
        restaurantEditProfileFragmentEtWhatsapp.getEditText().setText(SharedPreference.LoadData(getActivity(), RESTAURANT_WHATS_APP));
        restaurantEditProfileFragmentEtDurationDelivery.getEditText().setText(SharedPreference.LoadData(getActivity(), RESTAURANT_DELIVERY_TIME));
        restaurantEditProfileFragmentEtDeliveryCost.getEditText().setText(SharedPreference.LoadData(getActivity(), RESTAURANT_DELIVERY_COST));
        restaurantEditProfileFragmentSwitch.setChecked(true);
        if (restaurantEditProfileFragmentSwitch.isChecked()) {
            restaurantEditProfileFragmentTvState.setText("Opened");
        } else {
            restaurantEditProfileFragmentTvState.setText("Closed");
        }
//        restaurantEditProfileFragmentAddPhoto.setImageResource(Integer.parseInt(SharedPreference.LoadData(getActivity(), RESTAURANT_PHOTO)));
//        restaurantEditProfileFragmentSpCity.setSelection(SharedPreference.LoadData(getActivity(), RESTAURANT_REGION));
    }


    @OnClick({R.id.restaurant_edit_profile_fragment_et_minimum_delivery, R.id.restaurant_edit_profile_fragment_btn_edit, R.id.restaurant_edit_profile_fragment_add_photo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.restaurant_edit_profile_fragment_add_photo:
                initImage();
                break;
            case R.id.restaurant_edit_profile_fragment_btn_edit:
                break;
        }
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
                        HelperMethod.onLoadImageFromUrl(restaurantEditProfileFragmentAddPhoto, path, getActivity());
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
}
