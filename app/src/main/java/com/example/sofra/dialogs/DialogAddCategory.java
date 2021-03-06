package com.example.sofra.dialogs;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.sofra.R;
import com.example.sofra.adapter.RestaurantCategoryAdapter;
import com.example.sofra.data.local.SharedPreference;
import com.example.sofra.data.model.restaurantAddNewCategory.RestaurantAddNewCategory;
import com.example.sofra.data.model.restaurantCategory.CategoryData;
import com.example.sofra.data.model.restaurantUpdateCategory.RestaurantUpdateCategory;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.helper.MediaLoader;
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

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.data.local.SharedPreference.LoadData;
import static com.example.sofra.data.local.SharedPreference.RESTAURANT_API_TOKEN;
import static com.example.sofra.helper.HelperMethod.convertFileToMultipart;
import static com.example.sofra.helper.HelperMethod.convertToRequestBody;
import static com.example.sofra.helper.HelperMethod.dismissProgressDialog;
import static com.example.sofra.helper.HelperMethod.onLoadImageFromUrl;
import static com.example.sofra.helper.HelperMethod.showProgressDialog;

public class DialogAddCategory extends Dialog {

    @BindView(R.id.item_restaurant_add_category_dialog_img_add_photo)
    CircleImageView itemRestaurantAddCategoryDialogImgAddPhoto;
    @BindView(R.id.item_restaurant_add_category_dialog_et_category_name)
    TextInputLayout itemRestaurantAddCategoryDialogEtCategoryName;
    @BindView(R.id.item_restaurant_add_category_dialog_btn_add_category)
    Button itemRestaurantAddCategoryDialogBtnAddCategory;
    @BindView(R.id.item_restaurant_add_category_dialog_tv_title)
    TextView itemRestaurantAddCategoryDialogTvTitle;

    public int position;
    public RestaurantCategoryAdapter restaurantCategoryAdapter;
    private Activity activity;
    private Context context;
    private boolean Cancelable;
    private String path;
    public CategoryData categoryData;
    RequestBody apitoken, categoryName, categoryId;
    private MultipartBody.Part categoryPhoto;

    public DialogAddCategory(@NonNull Context context) {
        super(context);
    }

    public DialogAddCategory(Context context, Activity activity, boolean Cancelable) {
        super(context);
        this.activity = activity;
        this.context = context;
        this.Cancelable = Cancelable;
        onCreate();
    }

    public void onCreate() {
        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_restaurant_add_category);
            ButterKnife.bind(this);

            DialogAddCategory.this.setCancelable(Cancelable);

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            getWindow().setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
            show();
        } catch (Exception e) {

        }

    }

    public void setCategoryData() {
        if (categoryData != (null)) {

            itemRestaurantAddCategoryDialogEtCategoryName.getEditText().setText(categoryData.getName());
            onLoadImageFromUrl(itemRestaurantAddCategoryDialogImgAddPhoto, categoryData.getPhotoUrl(), activity);
            itemRestaurantAddCategoryDialogBtnAddCategory.setText("Edit");
            itemRestaurantAddCategoryDialogTvTitle.setText("Edit Category");

        }
    }

    @OnClick({R.id.item_restaurant_add_category_dialog_img_add_photo, R.id.item_restaurant_add_category_dialog_et_category_name, R.id.item_restaurant_add_category_dialog_btn_add_category})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item_restaurant_add_category_dialog_img_add_photo:
                initImage();
                break;
            case R.id.item_restaurant_add_category_dialog_et_category_name:
                break;
            case R.id.item_restaurant_add_category_dialog_btn_add_category:

                if (categoryData != (null)) {
                    editCategory();
                } else {
                    newCategory();
                }

                break;
        }
    }

    private void editCategory() {

        categoryName = convertToRequestBody(itemRestaurantAddCategoryDialogEtCategoryName.getEditText().getText().toString());
        categoryPhoto = convertFileToMultipart(path, "photo");
        String apiToken = LoadData(activity, RESTAURANT_API_TOKEN);
        apitoken = convertToRequestBody(apiToken);
        categoryId = convertToRequestBody(categoryData.getId().toString());
        showProgressDialog(activity, "please wait...");
        getClient().getRestaurantUpdateCategory(categoryName, categoryPhoto, apitoken, categoryId).enqueue(new Callback<RestaurantUpdateCategory>() {
            @Override
            public void onResponse(Call<RestaurantUpdateCategory> call, Response<RestaurantUpdateCategory> response) {
                dismissProgressDialog();
                try {
                    if (response.body().getStatus() == 1) {

                        restaurantCategoryAdapter.listRestaurantCategoryData.remove(position);
                        restaurantCategoryAdapter.listRestaurantCategoryData.add(position, response.body().getData());
                        restaurantCategoryAdapter.notifyDataSetChanged();
                        dismiss();
                        Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<RestaurantUpdateCategory> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }

    private void newCategory() {

        categoryName = convertToRequestBody(itemRestaurantAddCategoryDialogEtCategoryName.getEditText().getText().toString());
        categoryPhoto = convertFileToMultipart((path), "photo");

        if (itemRestaurantAddCategoryDialogEtCategoryName.getEditText().getText().toString().equals("")) {
            Toast.makeText(activity, "Please Enter Category Name", Toast.LENGTH_SHORT).show();
            return;
        } else if (path == null) {
            Toast.makeText(activity, "Please Enter Category Photo", Toast.LENGTH_SHORT).show();
            return;
        }

        init(categoryName, categoryPhoto);
        showProgressDialog(activity, "please wait...");

    }

    private void init(RequestBody categoryName, MultipartBody.Part categoryPhoto) {
        String apiToken = LoadData(activity, RESTAURANT_API_TOKEN);

        getClient().getRestaurantAddNewCategory(categoryName, categoryPhoto,
                convertToRequestBody(apiToken)).enqueue(new Callback<RestaurantAddNewCategory>() {
            @Override
            public void onResponse(Call<RestaurantAddNewCategory> call, Response<RestaurantAddNewCategory> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        dismissProgressDialog();
                        restaurantCategoryAdapter.listRestaurantCategoryData.add(response.body().getData());
                        restaurantCategoryAdapter.notifyDataSetChanged();
                        dismiss();
                        Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<RestaurantAddNewCategory> call, Throwable t) {

            }
        });
    }

    private void initImage() {
        Album.initialize(AlbumConfig.newBuilder(activity)
                .setAlbumLoader(new MediaLoader())
                .build());
        Album.image(activity) // Image selection.
                .singleChoice()
                .camera(true)
                .columnCount(3)
                .onResult(new com.yanzhenjie.album.Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        path = result.get(0).getPath();
                        onLoadImageFromUrl(itemRestaurantAddCategoryDialogImgAddPhoto, path, activity);
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