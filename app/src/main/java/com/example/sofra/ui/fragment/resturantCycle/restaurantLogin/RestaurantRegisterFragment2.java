package com.example.sofra.ui.fragment.resturantCycle.restaurantLogin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sofra.R;
import com.example.sofra.data.model.StepOne;
import com.example.sofra.data.model.restaurantRegister.RestaurantRegister;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.helper.MediaLoader;
import com.example.sofra.ui.fragment.resturantCycle.restaurantHome.RestaurantCategoryFragment;
import com.example.sofra.ui.fragment.untitledFolder.BaseFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.helper.HelperMethod.convertFileToMultipart;
import static com.example.sofra.helper.HelperMethod.convertToRequestBody;


public class RestaurantRegisterFragment2 extends BaseFragment {

    public StepOne stepOne;
    @BindView(R.id.restaurant_register2_fragment_et_phone)
    TextInputEditText restaurantRegister2FragmentEtPhone;
    @BindView(R.id.restaurant_register2_fragment_et_phone_whatsapp)
    TextInputEditText restaurantRegister2FragmentEtPhoneWhatsapp;
    @BindView(R.id.restaurant_register2_fragment_img_add_photo)
    ImageView restaurantRegister2FragmentImgAddPhoto;
    @BindView(R.id.restaurant_register2_fragment_btn_register)
    Button restaurantRegister2FragmentBtnRegister;

    private String path;
    private RequestBody whatsApp, Phone;
    private MultipartBody.Part photo;

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

    private void getRegister() {

        whatsApp = convertToRequestBody(restaurantRegister2FragmentEtPhoneWhatsapp.getText().toString().trim());
        Phone = convertToRequestBody(restaurantRegister2FragmentEtPhone.getText().toString().trim());
        photo = convertFileToMultipart(path, "photo");

        Register(stepOne.getName(), stepOne.getEmail(), stepOne.getPassword(), stepOne.getPasswordConfirmation(),
                Phone, whatsApp, stepOne.getRegionId(), stepOne.getDeliveryCost(), stepOne.getMinimumCharger()
                , photo, stepOne.getDeliveryTime());
    }

    private void Register(RequestBody name, RequestBody email, RequestBody password, RequestBody passwordConfirmation, RequestBody phone,
                          RequestBody whatsapp, RequestBody regionId, RequestBody deliveryCost,
                          RequestBody minimumCharger, MultipartBody.Part photo, RequestBody deliveryTime) {

        getClient().getRestaurantRegister(name, email, password, passwordConfirmation, phone, whatsapp, regionId, deliveryCost,
                minimumCharger, photo, deliveryTime).enqueue(new Callback<RestaurantRegister>() {
            @Override
            public void onResponse(Call<RestaurantRegister> call, Response<RestaurantRegister> response) {
                if (response.body().getStatus() == 1) {

                    HelperMethod.replace(new RestaurantCategoryFragment(), getActivity().getSupportFragmentManager(),
                            R.id.restaurant_cycle_fl_fragment_container, null, null);

                    Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<RestaurantRegister> call, Throwable t) {

            }
        });
    }

    @OnClick({R.id.restaurant_register2_fragment_img_add_photo, R.id.restaurant_register2_fragment_btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.restaurant_register2_fragment_img_add_photo:
                initImage();
                break;
            case R.id.restaurant_register2_fragment_btn_register:
                getRegister();
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
}
