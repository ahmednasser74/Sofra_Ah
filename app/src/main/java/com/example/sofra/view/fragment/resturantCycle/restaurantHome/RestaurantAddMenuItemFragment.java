package com.example.sofra.view.fragment.resturantCycle.restaurantHome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.sofra.R;
import com.example.sofra.data.model.listRestaurantItem.FoodItemData;
import com.example.sofra.data.model.restaurantAddMenuItem.RestaurantAddMenuItem;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.helper.MediaLoader;
import com.example.sofra.view.fragment.untitledFolder.BaseFragment;
import com.yanzhenjie.album.Action;
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

public class RestaurantAddMenuItemFragment extends BaseFragment {

    @BindView(R.id.restaurant_add_item_fragment_tv_title)
    TextView restaurantAddItemFragmentTvTitle;
    @BindView(R.id.restaurant_add_item_fragment_img_add_photo)
    ImageView restaurantAddItemFragmentImgAddPhoto;
    @BindView(R.id.restaurant_add_item_fragment_et_item_name)
    EditText restaurantAddItemFragmentEtItemName;
    @BindView(R.id.restaurant_add_item_fragment_et_item_description)
    EditText restaurantAddItemFragmentEtItemDescription;
    @BindView(R.id.restaurant_add_item_fragment_et_item_price)
    EditText restaurantAddItemFragmentEtItemPrice;
    @BindView(R.id.restaurant_add_item_fragment_et_item_offer_price)
    EditText restaurantAddItemFragmentEtItemOfferPrice;
    @BindView(R.id.restaurant_add_item_fragment_btn_add_item)
    Button restaurantAddItemFragmentBtnAddItem;

    private String path;
    public FoodItemData foodItemData;

    RequestBody description, price, preparingTime, name, apiToken, offerPrice, categoryId;
    MultipartBody.Part photo;

    public RestaurantAddMenuItemFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        setUpActivity();
        getClient();
        View view = inflater.inflate(R.layout.fragment_restaurant_add_item, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    private void convertAddNewMenuItem() {

        description = convertToRequestBody(restaurantAddItemFragmentEtItemDescription.getText().toString());
        price = convertToRequestBody(restaurantAddItemFragmentEtItemPrice.getText().toString());
        photo = convertFileToMultipart(String.valueOf(restaurantAddItemFragmentImgAddPhoto), "photo");
        name = convertToRequestBody(restaurantAddItemFragmentEtItemName.getText().toString());
        apiToken = convertToRequestBody("Jptu3JVmDXGpJEaQO9ZrjRg5RuAVCo45OC2AcOKqbVZPmu0ZJPN3T1sm0cWx");
        offerPrice = convertToRequestBody(restaurantAddItemFragmentEtItemOfferPrice.getText().toString());
        categoryId = convertToRequestBody(foodItemData.getCategoryId());

        addNewMenuItem(description, price, preparingTime, photo, name, apiToken, offerPrice, categoryId);
    }

    private void addNewMenuItem(RequestBody description, RequestBody price, RequestBody preparingTime,
                                MultipartBody.Part photo, RequestBody name, RequestBody apiToken,
                                RequestBody offerPrice, RequestBody categoryId) {

        getClient().getRestaurantAddMenuItem(description, price, preparingTime, name, photo,
                apiToken, offerPrice, categoryId).enqueue(new Callback<RestaurantAddMenuItem>() {
            @Override
            public void onResponse(Call<RestaurantAddMenuItem> call, Response<RestaurantAddMenuItem> response) {
                try {
                    if (response.body().getStatus() == 1) {

                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<RestaurantAddMenuItem> call, Throwable t) {

            }
        });
    }

    public void setMenuItemData() {

        Glide.with(getActivity()).load(foodItemData.getPhotoUrl()).into(restaurantAddItemFragmentImgAddPhoto);
        restaurantAddItemFragmentTvTitle.setText(foodItemData.getName());
        restaurantAddItemFragmentEtItemDescription.setText(foodItemData.getDescription());
        restaurantAddItemFragmentEtItemPrice.setText(foodItemData.getPrice());

        if (foodItemData.getHasOffer()) {
            restaurantAddItemFragmentEtItemOfferPrice.setText(foodItemData.getOfferPrice());
        } else {
            restaurantAddItemFragmentEtItemOfferPrice.setVisibility(View.GONE);
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
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        path = result.get(0).getPath();
                        HelperMethod.onLoadImageFromUrl(restaurantAddItemFragmentImgAddPhoto, path, getActivity());
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                    }
                })
                .start();
    }

    @OnClick({R.id.restaurant_add_item_fragment_img_add_photo, R.id.restaurant_add_item_fragment_btn_add_item})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.restaurant_add_item_fragment_img_add_photo:
                initImage();
                break;
            case R.id.restaurant_add_item_fragment_btn_add_item:
                convertAddNewMenuItem();
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
}
