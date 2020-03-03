package com.example.sofra.ui.fragment.resturantCycle.restaurantEditProfile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sofra.R;
import com.example.sofra.adapter.SpinnersAdapter;
import com.example.sofra.data.model.GeneralRequestSpinner;
import com.example.sofra.data.model.restaurantChangeState.RestaurantChangeState;
import com.example.sofra.data.model.restaurantEditProfile.RestaurantEditProfile;
import com.example.sofra.data.model.restaurantLogin.AuthRestaurantData;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.helper.MediaLoader;
import com.example.sofra.ui.fragment.untitledFolder.BaseFragment;
import com.google.android.material.textfield.TextInputLayout;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.data.local.SharedPreference.LoadData;
import static com.example.sofra.data.local.SharedPreference.RESTAURANT_API_TOKEN;
import static com.example.sofra.data.local.SharedPreference.LoadRestaurantData;
import static com.example.sofra.data.model.GeneralRequestSpinner.getSpinnerCityData;
import static com.example.sofra.helper.HelperMethod.convertFileToMultipart;
import static com.example.sofra.helper.HelperMethod.convertToRequestBody;
import static com.example.sofra.helper.HelperMethod.dismissProgressDialog;
import static com.example.sofra.helper.HelperMethod.onLoadImageFromUrl;
import static com.example.sofra.helper.HelperMethod.showProgressDialog;


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

    private String path;
    private SpinnersAdapter cityAdapter, townAdapter;
    private RequestBody Email, Name, Phone, RegionId, DeliveryCost, MinimumCharger, Availability, apitoken, DeliveryTime;
    private MultipartBody.Part Photo;
    private AuthRestaurantData authRestaurantData;

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

        authRestaurantData = LoadRestaurantData(getActivity());
        cityAdapter = new SpinnersAdapter(getActivity());
        townAdapter = new SpinnersAdapter(getActivity());

        getClient().getRestaurantChangeState("", LoadData(getActivity(), RESTAURANT_API_TOKEN)).enqueue(new Callback<RestaurantChangeState>() {
            @Override
            public void onResponse(Call<RestaurantChangeState> call, Response<RestaurantChangeState> response) {
                try {
                    if (response.body().getStatus() == 1) {

                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<RestaurantChangeState> call, Throwable t) {

            }
        });

        setData();
        return view;
    }

    private void setData() {

        onLoadImageFromUrl(restaurantEditProfileFragmentAddPhoto, authRestaurantData.getUser().getPhotoUrl(), getActivity());
        restaurantEditProfileFragmentEtName.getEditText().setText(authRestaurantData.getUser().getName());
        restaurantEditProfileFragmentEtMail.getEditText().setText(authRestaurantData.getUser().getEmail());
        restaurantEditProfileFragmentEtMinimumDelivery.getEditText().setText(authRestaurantData.getUser().getMinimumCharger());
        restaurantEditProfileFragmentEtPhone.getEditText().setText(authRestaurantData.getUser().getPhone());
        restaurantEditProfileFragmentEtWhatsapp.getEditText().setText(authRestaurantData.getUser().getWhatsapp());
        restaurantEditProfileFragmentEtDurationDelivery.getEditText().setText(authRestaurantData.getUser().getDeliveryTime());
        restaurantEditProfileFragmentEtDeliveryCost.getEditText().setText(authRestaurantData.getUser().getDeliveryCost());

        if (!authRestaurantData.getUser().getAvailability().equals("open")) {
            restaurantEditProfileFragmentSwitch.setChecked(false);
        } else {
            restaurantEditProfileFragmentSwitch.setChecked(true);
        }

//        restaurantEditProfileFragmentSpCity.setSelected(authRestaurantData.getUser().getRegion().getCity().getName());
//        restaurantEditProfileFragmentSpGovernorate.setSelected(Boolean.parseBoolean(LoadData(getActivity(), authRestaurantData.getUser().getRegion().getName())));

        getSpinnerCityData(getClient().getCity(), cityAdapter
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

    }

    private void editProfile(RequestBody email, RequestBody name, RequestBody phone, RequestBody regionId
            , RequestBody deliveryCost, RequestBody minimumCharger, RequestBody availability,
                             MultipartBody.Part photo, RequestBody apiToken, RequestBody deliveryTime) {

        getClient().getRestaurantEditProfile(email, name, phone, regionId, deliveryCost, minimumCharger,
                availability, photo, apiToken, deliveryTime).enqueue(new Callback<RestaurantEditProfile>() {
            @Override
            public void onResponse(Call<RestaurantEditProfile> call, Response<RestaurantEditProfile> response) {
                dismissProgressDialog();
                try {
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<RestaurantEditProfile> call, Throwable t) {

            }
        });
    }

    private void getEdits() {

        Email = convertToRequestBody(LoadData(getActivity(), authRestaurantData.getUser().getEmail()));
        Name = convertToRequestBody(LoadData(getActivity(), authRestaurantData.getUser().getName()));
        Phone = convertToRequestBody(LoadData(getActivity(), authRestaurantData.getUser().getPhone()));
        RegionId = convertToRequestBody(LoadData(getActivity(), authRestaurantData.getUser().getRegionId()));
        DeliveryCost = convertToRequestBody(LoadData(getActivity(), authRestaurantData.getUser().getDeliveryCost()));
        MinimumCharger = convertToRequestBody(LoadData(getActivity(), authRestaurantData.getUser().getMinimumCharger()));
        Availability = convertToRequestBody(LoadData(getActivity(), authRestaurantData.getUser().getAvailability()));
        Photo = convertFileToMultipart(String.valueOf(getActivity()), authRestaurantData.getUser().getPhotoUrl());
        apitoken = convertToRequestBody(LoadData(getActivity(), RESTAURANT_API_TOKEN));
        DeliveryTime = convertToRequestBody(LoadData(getActivity(), authRestaurantData.getUser().getDeliveryTime()));

        showProgressDialog(getActivity(), "please wait...");

        editProfile(Email, Name, Phone, RegionId, DeliveryCost, MinimumCharger, Availability, Photo, apitoken, DeliveryTime);
    }

    @OnClick({R.id.restaurant_edit_profile_fragment_et_minimum_delivery, R.id.restaurant_edit_profile_fragment_btn_edit, R.id.restaurant_edit_profile_fragment_add_photo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.restaurant_edit_profile_fragment_add_photo:
                initImage();
                break;
            case R.id.restaurant_edit_profile_fragment_btn_edit:
                getEdits();
                break;
        }
    }

    @Override
    public void onBack() {
        super.onBack();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
}
