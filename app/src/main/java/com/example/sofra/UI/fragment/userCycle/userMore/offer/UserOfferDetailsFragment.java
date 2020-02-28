package com.example.sofra.UI.fragment.userCycle.userMore.offer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sofra.R;
import com.example.sofra.data.model.restaurantOffer.OfferData;
import com.example.sofra.UI.fragment.untitledFolder.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.sofra.data.api.ApiClient.getClient;


public class UserOfferDetailsFragment extends BaseFragment {

    public static OfferData userDetailsListOfferData;
    @BindView(R.id.user_offer_details_fragment_tv_offer_name)
    TextView userOfferDetailsFragmentTvOfferName;
    @BindView(R.id.user_offer_details_fragment_tv_offer_details)
    TextView userOfferDetailsFragmentTvOfferDetails;
    @BindView(R.id.user_offer_details_fragment_tv_date_from)
    TextView userOfferDetailsFragmentTvDateFrom;
    @BindView(R.id.user_offer_details_fragment_tv_date_till)
    TextView userOfferDetailsFragmentTvDateTill;
    @BindView(R.id.user_offer_details_fragment_btn_get_offer)
    Button userOfferDetailsFragmentBtnGetOffer;

    public UserOfferDetailsFragment() {
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

        View view = inflater.inflate(R.layout.fragment_user_offer_details, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        userOfferDetailsFragmentTvDateFrom.setText(userDetailsListOfferData.getStartingAt());
        userOfferDetailsFragmentTvDateTill.setText(userDetailsListOfferData.getEndingAt());
        userOfferDetailsFragmentTvOfferName.setText(userDetailsListOfferData.getName());
        userOfferDetailsFragmentTvOfferDetails.setText(userDetailsListOfferData.getDescription());
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
