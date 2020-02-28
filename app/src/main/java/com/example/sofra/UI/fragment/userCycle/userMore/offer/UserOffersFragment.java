package com.example.sofra.UI.fragment.userCycle.userMore.offer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofra.R;
import com.example.sofra.adapter.UserOfferAdapter;
import com.example.sofra.data.model.restaurantOffer.OfferData;
import com.example.sofra.data.model.userOffer.UserOffer;
import com.example.sofra.helper.OnEndLess;
import com.example.sofra.UI.activity.BaseActivity;
import com.example.sofra.UI.fragment.untitledFolder.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;


public class UserOffersFragment extends BaseFragment {

    @BindView(R.id.user_offer_fragment_rv_offers)
    RecyclerView userOfferFragmentRvOffers;
    @BindView(R.id.user_offer_fragment_progress)
    ProgressBar userOfferFragmentProgress;
    private OnEndLess onEndLess;
    private int maxPage = 0;
    private UserOfferAdapter userOfferAdapter;
    private List<OfferData> offerDataList = new ArrayList<>();

    public UserOffersFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        setUpActivity();
        View view = inflater.inflate(R.layout.fragment_user_offers, container, false);
        ButterKnife.bind(this, view);

        userOfferFragmentRvOffers.setVisibility(View.GONE);

        init();
        return view;
    }

    private void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        userOfferFragmentRvOffers.setLayoutManager(linearLayoutManager);

        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;
                        getUserOffer(current_page);
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                }
            }
        };
        userOfferFragmentRvOffers.addOnScrollListener(onEndLess);

        userOfferAdapter = new UserOfferAdapter((BaseActivity) getActivity(), offerDataList);
        userOfferFragmentRvOffers.setAdapter(userOfferAdapter);
        getUserOffer(1);
    }

    private void getUserOffer(int page) {
        getClient().getUserOffer(page).enqueue(new Callback<UserOffer>() {
            @Override
            public void onResponse(Call<UserOffer> call, Response<UserOffer> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        userOfferFragmentProgress.setVisibility(View.GONE);
                        userOfferFragmentRvOffers.setVisibility(View.VISIBLE);
                        offerDataList.addAll(response.body().getData().getData());
                        maxPage = response.body().getData().getLastPage();
                        userOfferAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<UserOffer> call, Throwable t) {

            }
        });
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
