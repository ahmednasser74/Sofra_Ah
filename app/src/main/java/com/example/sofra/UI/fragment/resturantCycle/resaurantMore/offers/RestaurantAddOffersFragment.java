package com.example.sofra.UI.fragment.resturantCycle.resaurantMore.offers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sofra.R;
import com.example.sofra.data.model.restaurantOffer.OfferData;
import com.example.sofra.helper.DateTxt;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.helper.MediaLoader;
import com.example.sofra.UI.fragment.untitledFolder.BaseFragment;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.helper.HelperMethod.showCalender;


public class RestaurantAddOffersFragment extends BaseFragment {

    @BindView(R.id.restaurant_add_offer_fragment_tv_add_image)
    TextView restaurantAddOfferFragmentTvAddImage;
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

    @OnClick({R.id.restaurant_add_offer_fragment_tv_add_image, R.id.restaurant_add_offer_fragment_img_add_photo, R.id.restaurant_add_offer_fragment_et_offer_name, R.id.restaurant_add_offer_fragment_et_offer_description, R.id.restaurant_add_offer_fragment_et_date_from, R.id.restaurant_add_offer_fragment_et_date_till, R.id.restaurant_add_offer_fragment_btn_add_offer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.restaurant_add_offer_fragment_tv_add_image:
                break;
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
