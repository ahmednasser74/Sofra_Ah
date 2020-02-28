package com.example.sofra.ui.fragment.resturantCycle.resaurantMore.offers;

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
import com.example.sofra.adapter.RestaurantOfferAdapter;
import com.example.sofra.data.local.SharedPreference;
import com.example.sofra.data.model.restaurantAddOffer.RestaurantAddOffer;
import com.example.sofra.data.model.restaurantEditOffer.RestaurantEditOffer;
import com.example.sofra.data.model.restaurantOffer.OfferData;
import com.example.sofra.helper.DateTxt;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.helper.MediaLoader;
import com.example.sofra.ui.fragment.untitledFolder.BaseFragment;
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
import retrofit2.http.Multipart;

import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.data.local.SharedPreference.LoadData;
import static com.example.sofra.data.local.SharedPreference.RESTAURANT_API_TOKEN;
import static com.example.sofra.helper.HelperMethod.convertFileToMultipart;
import static com.example.sofra.helper.HelperMethod.convertToRequestBody;
import static com.example.sofra.helper.HelperMethod.showCalender;
import static com.example.sofra.helper.HelperMethod.showProgressDialog;


public class RestaurantAddOffersFragment extends BaseFragment {

    @BindView(R.id.restaurant_add_offer_fragment_tv_title)
    TextView restaurantAddOfferFragmentTvTitle;
    @BindView(R.id.restaurant_add_offer_fragment_img_add_photo)
    ImageView restaurantAddOfferFragmentImgAddPhoto;
    @BindView(R.id.restaurant_add_offer_fragment_et_offer_name)
    EditText restaurantAddOfferFragmentEtOfferName;
    @BindView(R.id.restaurant_add_offer_fragment_et_offer_description)
    EditText restaurantAddOfferFragmentEtOfferDescription;
    @BindView(R.id.restaurant_add_offer_fragment_et_date_from)
    TextView restaurantAddOfferFragmentEtDateFrom;
    @BindView(R.id.restaurant_add_offer_fragment_et_date_till)
    TextView restaurantAddOfferFragmentEtDateTill;
    @BindView(R.id.restaurant_add_offer_fragment_btn_add_offer)
    Button restaurantAddOfferFragmentBtnAddOffer;

    public OfferData offerData;
    private String path;
    public int position;
    public RestaurantOfferAdapter restaurantOfferAdapter;

    RequestBody name, description, startingAt, endingAt, offerId, apiToken;
    MultipartBody.Part photo;

    public RestaurantAddOffersFragment() {
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

        View view = inflater.inflate(R.layout.fragment_restaurant_add_offers, container, false);
        ButterKnife.bind(this, view);

        setOfferData();

        return view;
    }

    public void editOffer() {

        description = convertToRequestBody(restaurantAddOfferFragmentEtOfferDescription.getText().toString());
        startingAt = convertToRequestBody(restaurantAddOfferFragmentEtDateFrom.getText().toString());
        name = convertToRequestBody(restaurantAddOfferFragmentEtOfferName.getText().toString());
        photo = convertFileToMultipart(path, "photo");
        endingAt = convertToRequestBody(restaurantAddOfferFragmentEtDateTill.getText().toString());
        offerId = convertToRequestBody(String.valueOf(offerData.getId()));
        apiToken = convertToRequestBody(LoadData(getActivity(), RESTAURANT_API_TOKEN));

        getClient().getRestaurantEditOffer(description, startingAt, name, photo, endingAt, offerId, apiToken).enqueue(new Callback<RestaurantEditOffer>() {
            @Override
            public void onResponse(Call<RestaurantEditOffer> call, Response<RestaurantEditOffer> response) {
                try {
                    if (response.body().getStatus() == 1) {

                        restaurantOfferAdapter.offerDataList.remove(restaurantOfferAdapter.position);
                        restaurantOfferAdapter.offerDataList.add(restaurantOfferAdapter.position, response.body().getData());
                        restaurantOfferAdapter.notifyDataSetChanged();

                        Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<RestaurantEditOffer> call, Throwable t) {

            }
        });

    }

    private void setOfferData() {

        if (offerData != null) {

            restaurantAddOfferFragmentTvTitle.setText("Edit Offer");
            restaurantAddOfferFragmentBtnAddOffer.setText("Edit");

            Glide.with(getActivity()).load(offerData.getPhotoUrl()).into(restaurantAddOfferFragmentImgAddPhoto);
            restaurantAddOfferFragmentEtOfferName.setText(offerData.getName());
            restaurantAddOfferFragmentEtOfferDescription.setText(offerData.getDescription());
            restaurantAddOfferFragmentEtDateFrom.setText(offerData.getStartingAt());
            restaurantAddOfferFragmentEtDateTill.setText(offerData.getEndingAt());

        }

    }

    private void addNewOffer() {

        description = convertToRequestBody(restaurantAddOfferFragmentEtOfferDescription.getText().toString());
        name = convertToRequestBody(restaurantAddOfferFragmentEtOfferName.getText().toString());
        startingAt = convertToRequestBody(restaurantAddOfferFragmentEtDateFrom.getText().toString());
        endingAt = convertToRequestBody(restaurantAddOfferFragmentEtDateTill.getText().toString());
        photo = convertFileToMultipart(path, "photo");
        offerId = convertToRequestBody(String.valueOf(offerData.getId()));
        apiToken = convertToRequestBody(LoadData(getActivity(), RESTAURANT_API_TOKEN));

        if (path == null) {
            Toast.makeText(baseActivity, "please select photo", Toast.LENGTH_SHORT).show();
        } else if (restaurantAddOfferFragmentEtOfferName == null) {
            Toast.makeText(baseActivity, "please write item name", Toast.LENGTH_SHORT).show();
        } else if (restaurantAddOfferFragmentEtOfferDescription == null) {
            Toast.makeText(baseActivity, "please write item description", Toast.LENGTH_SHORT).show();
        } else if (startingAt == null) {
            Toast.makeText(baseActivity, "please select from", Toast.LENGTH_SHORT).show();
        } else if (endingAt == null) {
            Toast.makeText(baseActivity, "please select till", Toast.LENGTH_SHORT).show();
        } else {
            showProgressDialog(getActivity(), "Please Wait...");
        }

        getNewOffer(description, startingAt, name, photo, endingAt, offerId, apiToken);

    }

    private void getNewOffer(RequestBody description, RequestBody startingAt, RequestBody name,
                             MultipartBody.Part photo, RequestBody endingAt, RequestBody offerId, RequestBody apiToken) {

        getClient().getRestaurantAddOffer(description, startingAt, name, photo, endingAt, offerId, apiToken).enqueue(new Callback<RestaurantAddOffer>() {
            @Override
            public void onResponse(Call<RestaurantAddOffer> call, Response<RestaurantAddOffer> response) {
                try {
                    if (response.body().getStatus() == 1) {

                        Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<RestaurantAddOffer> call, Throwable t) {

            }
        });

    }

    @OnClick({R.id.restaurant_add_offer_fragment_img_add_photo, R.id.restaurant_add_offer_fragment_et_offer_name, R.id.restaurant_add_offer_fragment_et_offer_description, R.id.restaurant_add_offer_fragment_et_date_from, R.id.restaurant_add_offer_fragment_et_date_till, R.id.restaurant_add_offer_fragment_btn_add_offer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.restaurant_add_offer_fragment_img_add_photo:
                initImage();
                break;
            case R.id.restaurant_add_offer_fragment_et_date_from:
                DateTxt dateTxt1 = new DateTxt("02", "02", "2020", "02-02-2020");
                showCalender(getActivity(), "Select Date", restaurantAddOfferFragmentEtDateFrom, dateTxt1);
                break;
            case R.id.restaurant_add_offer_fragment_et_date_till:
                DateTxt dateTxt = new DateTxt("02", "02", "2020", "02-02-2020");
                showCalender(getActivity(), "Select Date", restaurantAddOfferFragmentEtDateTill, dateTxt);
                break;
            case R.id.restaurant_add_offer_fragment_btn_add_offer:

                if (offerData != (null)) {
                    editOffer();
                } else {
                    addNewOffer();
                }

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
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        path = result.get(0).getPath();
                        HelperMethod.onLoadImageFromUrl(restaurantAddOfferFragmentImgAddPhoto, path, getActivity());
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
