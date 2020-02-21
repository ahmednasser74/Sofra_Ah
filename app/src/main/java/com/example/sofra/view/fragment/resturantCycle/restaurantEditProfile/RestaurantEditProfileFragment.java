package com.example.sofra.view.fragment.resturantCycle.restaurantEditProfile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sofra.R;
import com.example.sofra.adapter.SpinnersAdapter;
import com.example.sofra.data.local.SharedPreference;
import com.example.sofra.data.model.GeneralRequestSpinner;
import com.example.sofra.data.model.restaurantChangeState.RestaurantChangeState;
import com.example.sofra.data.model.restaurantEditProfile.RestaurantEditProfile;
import com.example.sofra.data.model.restaurantEditProfile.RestaurantEditProfileData;
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
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.data.local.SharedPreference.LoadBoolean;
import static com.example.sofra.data.local.SharedPreference.LoadData;
import static com.example.sofra.data.local.SharedPreference.RESTAURANT_ACTIVATED;
import static com.example.sofra.data.local.SharedPreference.RESTAURANT_API_TOKEN;
import static com.example.sofra.data.local.SharedPreference.RESTAURANT_DATA;
import static com.example.sofra.data.local.SharedPreference.LoadRestaurantData;
import static com.example.sofra.data.local.SharedPreference.RESTAURANT_DELIVERY_COST;
import static com.example.sofra.data.local.SharedPreference.RESTAURANT_DELIVERY_TIME;
import static com.example.sofra.data.local.SharedPreference.RESTAURANT_MAIL;
import static com.example.sofra.data.local.SharedPreference.RESTAURANT_MINIMUM_CHARGER;
import static com.example.sofra.data.local.SharedPreference.RESTAURANT_PHONE;
import static com.example.sofra.data.local.SharedPreference.RESTAURANT_PHOTO;
import static com.example.sofra.data.local.SharedPreference.RESTAURANT_REGION;
import static com.example.sofra.data.local.SharedPreference.RESTAURANT_USER_NAME;
import static com.example.sofra.data.local.SharedPreference.RESTAURANT_WHATS_APP;
import static com.example.sofra.helper.HelperMethod.onLoadImageFromUrl;


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
    RestaurantEditProfileData restaurantEditProfileData ;
    private SpinnersAdapter cityAdapter, townAdapter;
    private RequestBody email, name, phone, regionId, deliveryCost, minimumCharger, availability, apiToken, deliveryTime;
    private MultipartBody.Part photo;

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

        String token = LoadData(getActivity(), RESTAURANT_API_TOKEN);
        String state = LoadData(getActivity(),RESTAURANT_ACTIVATED);

        getClient().getRestaurantChangeState(state, token).enqueue(new Callback<RestaurantChangeState>() {
            @Override
            public void onResponse(Call<RestaurantChangeState> call, Response<RestaurantChangeState> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        restaurantEditProfileFragmentSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    restaurantEditProfileFragmentTvState.setText("Opened");
                                } else {
                                    restaurantEditProfileFragmentTvState.setText("Closed");
                                }
                            }

                        });

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
        restaurantEditProfileFragmentEtName.getEditText().setText(LoadData(getActivity(), RESTAURANT_USER_NAME));
        restaurantEditProfileFragmentEtMail.getEditText().setText(SharedPreference.LoadData(getActivity(), RESTAURANT_MAIL));
        restaurantEditProfileFragmentEtMinimumDelivery.getEditText().setText(SharedPreference.LoadData(getActivity(), RESTAURANT_MINIMUM_CHARGER));
        restaurantEditProfileFragmentEtPhone.getEditText().setText(LoadData(getActivity(), RESTAURANT_PHONE));
        restaurantEditProfileFragmentEtWhatsapp.getEditText().setText(LoadData(getActivity(), RESTAURANT_WHATS_APP));
        restaurantEditProfileFragmentEtDurationDelivery.getEditText().setText(LoadData(getActivity(), RESTAURANT_DELIVERY_TIME));
        restaurantEditProfileFragmentEtDeliveryCost.getEditText().setText(LoadData(getActivity(), RESTAURANT_DELIVERY_COST));
        restaurantEditProfileFragmentSwitch.setChecked(Boolean.parseBoolean(LoadData(getActivity(), RESTAURANT_ACTIVATED)));
        restaurantEditProfileFragmentSpGovernorate.setSelected(Boolean.parseBoolean(LoadData(getActivity(), RESTAURANT_REGION)));

        restaurantEditProfileFragmentSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    restaurantEditProfileFragmentTvState.setText("Opened");
                } else {
                    restaurantEditProfileFragmentTvState.setText("Closed");
                }
            }

        });
//        onLoadImageFromUrl(restaurantEditProfileFragmentAddPhoto, restaurantEditProfileData.getUser().getPhotoUrl(), getActivity());

//        init();

    }

    private void init(RequestBody email, RequestBody name, RequestBody phone, RequestBody regionId
            , RequestBody deliveryCost, RequestBody minimumCharger, RequestBody availability,
                      MultipartBody.Part photo, RequestBody apiToken, RequestBody deliveryTime) {
        apiToken = HelperMethod.convertToRequestBody(LoadData(getActivity(), RESTAURANT_API_TOKEN));

        getClient().getRestauranEditProfile(email, name, phone, regionId, deliveryCost, minimumCharger,
                availability, photo, apiToken, deliveryTime).enqueue(new Callback<RestaurantEditProfile>() {
            @Override
            public void onResponse(Call<RestaurantEditProfile> call, Response<RestaurantEditProfile> response) {
                try {
                    if (response.body().getStatus() == 1) {

                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<RestaurantEditProfile> call, Throwable t) {

            }
        });
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
