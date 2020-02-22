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
import com.example.sofra.adapter.RestaurantMenuItemAdapter;
import com.example.sofra.data.local.SharedPreference;
import com.example.sofra.data.model.listRestaurantItem.FoodItemData;
import com.example.sofra.data.model.restaurantAddMenuItem.RestaurantAddMenuItem;
import com.example.sofra.data.model.restaurantCategory.CategoryData;
import com.example.sofra.data.model.restaurantEditMenuItem.RestaurantEditMenuItem;
import com.example.sofra.data.model.restaurantMenuItem.RestaurantMenuItem;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.helper.MediaLoader;
import com.example.sofra.view.fragment.untitledFolder.BaseFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
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

import static android.text.TextUtils.isEmpty;
import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.data.local.SharedPreference.LoadData;
import static com.example.sofra.data.local.SharedPreference.RESTAURANT_API_TOKEN;
import static com.example.sofra.helper.HelperMethod.convertFileToMultipart;
import static com.example.sofra.helper.HelperMethod.convertToRequestBody;
import static com.example.sofra.helper.HelperMethod.dismissProgressDialog;
import static com.example.sofra.helper.HelperMethod.showProgressDialog;

public class RestaurantAddMenuItemFragment extends BaseFragment {

    @BindView(R.id.restaurant_add_item_fragment_tv_title)
    TextView restaurantAddItemFragmentTvTitle;
    @BindView(R.id.restaurant_add_item_fragment_img_add_photo)
    ImageView restaurantAddItemFragmentImgAddPhoto;
    @BindView(R.id.restaurant_add_item_fragment_et_item_name)
    TextInputLayout restaurantAddItemFragmentEtItemName;
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
    RequestBody description, price, preparingTime, name, apitoken, offerPrice, categoryId;
    MultipartBody.Part photo;
    public CategoryData categoryData;
    public RestaurantMenuItemAdapter restaurantMenuItemAdapter;
    public int position;

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

    private void addNewMenuItem() {
        description = convertToRequestBody(restaurantAddItemFragmentEtItemDescription.getText().toString());
        price = convertToRequestBody(restaurantAddItemFragmentEtItemPrice.getText().toString());
        photo = convertFileToMultipart((path), "photo");
        name = convertToRequestBody(restaurantAddItemFragmentEtItemName.getEditText().getText().toString());
        apitoken = convertToRequestBody(LoadData(getActivity(), RESTAURANT_API_TOKEN));
        offerPrice = convertToRequestBody(restaurantAddItemFragmentEtItemOfferPrice.getText().toString());
        categoryId = convertToRequestBody(categoryData.getId().toString());

        if (isEmpty(description.toString())) {
            restaurantAddItemFragmentEtItemName.setError("please enter name");
        } else if (restaurantAddItemFragmentEtItemDescription.equals("")) {
            restaurantAddItemFragmentEtItemDescription.setError("please enter description");
        } else if (restaurantAddItemFragmentEtItemPrice.equals("")) {
            restaurantAddItemFragmentEtItemPrice.setError("please enter price");
        } else if (restaurantAddItemFragmentImgAddPhoto == null) {
            Toast.makeText(baseActivity, "please select photo", Toast.LENGTH_SHORT).show();
        } else {
            addNewMenuItem(description, price, preparingTime, photo, name, apitoken, offerPrice, categoryId);
            showProgressDialog(getActivity(), "Please Wait...");
        }

    }

    private void addNewMenuItem(RequestBody description, RequestBody price, RequestBody preparingTime,
                                MultipartBody.Part photo, RequestBody name, RequestBody apiToken,
                                RequestBody offerPrice, RequestBody categoryId) {

        getClient().getRestaurantAddMenuItem(description, price, preparingTime, name, photo,
                apiToken, offerPrice, categoryId).enqueue(new Callback<RestaurantAddMenuItem>() {
            @Override
            public void onResponse(Call<RestaurantAddMenuItem> call, Response<RestaurantAddMenuItem> response) {
                dismissProgressDialog();
                try {
                    if (response.body().getStatus() == 1) {

//                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                        Snackbar.make(getActivity().findViewById(android.R.id.content),
                                response.body().getMsg(), Snackbar.LENGTH_LONG);
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
        if (foodItemData != (null)) {
            restaurantAddItemFragmentTvTitle.setText("Edit Menu Item");
            restaurantAddItemFragmentBtnAddItem.setText("Edit");

            Glide.with(getActivity()).load(foodItemData.getPhotoUrl()).into(restaurantAddItemFragmentImgAddPhoto);
            restaurantAddItemFragmentTvTitle.setText(foodItemData.getName());
            restaurantAddItemFragmentEtItemDescription.setText(foodItemData.getDescription());
            restaurantAddItemFragmentEtItemPrice.setText(foodItemData.getPrice());
            restaurantAddItemFragmentEtItemOfferPrice.setText(foodItemData.getOfferPrice());
        }
    }

    @OnClick({R.id.restaurant_add_item_fragment_img_add_photo, R.id.restaurant_add_item_fragment_btn_add_item})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.restaurant_add_item_fragment_img_add_photo:
                initImage();
                break;
            case R.id.restaurant_add_item_fragment_btn_add_item:
                if (foodItemData != (null)) {
                    editMenuItem();
                } else {
                    addNewMenuItem();
                }
                break;
        }
    }

    private void editMenuItem() {
        name = convertToRequestBody(restaurantAddItemFragmentEtItemName.getEditText().getText().toString());
        apitoken = convertToRequestBody(LoadData(getActivity(), RESTAURANT_API_TOKEN));
        categoryId = convertToRequestBody(categoryData.getId().toString());
        photo = convertFileToMultipart(path, "photo");

        getClient().getRestaurantEditMenuItem(name, photo, apitoken, categoryId).enqueue(new Callback<RestaurantEditMenuItem>() {
            @Override
            public void onResponse(Call<RestaurantEditMenuItem> call, Response<RestaurantEditMenuItem> response) {
                try {
                    if (response.body().getStatus() == 1) {

                        restaurantMenuItemAdapter.foodItemDataList.remove(restaurantMenuItemAdapter.position);
//                        restaurantMenuItemAdapter.foodItemDataList.add(restaurantMenuItemAdapter.position, response.body().getData());
                        restaurantMenuItemAdapter.notifyDataSetChanged();

//                        HelperMethod.replace(restaurantAddMenuItemFragment, getActivity().getSupportFragmentManager(),
//                                R.id.restaurant_cycle_fl_fragment_container, null, null);

                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<RestaurantEditMenuItem> call, Throwable t) {

            }
        });
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

    @Override
    public void onBack() {
        super.onBack();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
