package com.example.sofra.ui.fragment.resturantCycle.resaurantMore.offers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofra.R;
import com.example.sofra.adapter.RestaurantOfferAdapter;
import com.example.sofra.data.model.restaurantOffer.OfferData;
import com.example.sofra.data.model.restaurantOffer.RestaurantOffer;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.helper.OnEndLess;
import com.example.sofra.ui.activity.BaseActivity;
import com.example.sofra.ui.fragment.untitledFolder.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.data.local.SharedPreference.LoadData;
import static com.example.sofra.data.local.SharedPreference.RESTAURANT_API_TOKEN;


public class RestaurantOffersFragment extends BaseFragment {

    @BindView(R.id.restaurant_offers_fragment_rv_offer)
    RecyclerView restaurantOffersFragmentRvOffer;
    @BindView(R.id.restaurant_offers_fragment_btn_add_offer)
    Button restaurantOffersFragmentBtnAddOffer;
    @BindView(R.id.restaurant_offers_fragment_progress)
    ProgressBar restaurantOffersFragmentProgress;

    private LinearLayoutManager linearLayoutManager;
    private List<OfferData> offerDataList = new ArrayList<>();
    private RestaurantOfferAdapter restaurantOfferAdapter;
    private OnEndLess onEndLess;
    private int maxPage = 0;

    public RestaurantOffersFragment() {
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

        View view = inflater.inflate(R.layout.fragment_restaurant_offers, container, false);
        ButterKnife.bind(this, view);

        restaurantOffersFragmentRvOffer.setVisibility(View.GONE);
        hideButton();
        init();
        return view;
    }

    private void init() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        restaurantOffersFragmentRvOffer.setLayoutManager(linearLayoutManager);
        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        if ((offerDataList.size() + 1) / 10 != current_page) {
                            onEndLess.previous_page = current_page;
                            getRestaurantOrders(current_page);
                        }
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                }
            }
        };
        restaurantOffersFragmentRvOffer.addOnScrollListener(onEndLess);
        if (offerDataList.size() == 0) {
            restaurantOfferAdapter = new RestaurantOfferAdapter((BaseActivity) getActivity(), offerDataList);
            restaurantOffersFragmentRvOffer.setAdapter(restaurantOfferAdapter);
            getRestaurantOrders(1);
        } else {
            restaurantOffersFragmentRvOffer.setAdapter(restaurantOfferAdapter);
            restaurantOffersFragmentRvOffer.setVisibility(View.VISIBLE);
        }
    }

    private void getRestaurantOrders(int page) {
        getClient().getRestaurantOffer(LoadData(getActivity(),RESTAURANT_API_TOKEN), page).enqueue(new Callback<RestaurantOffer>() {
            @Override
            public void onResponse(Call<RestaurantOffer> call, Response<RestaurantOffer> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        offerDataList.addAll(response.body().getData().getData());
                        maxPage = response.body().getData().getLastPage();
                        restaurantOffersFragmentProgress.setVisibility(View.GONE);
                        restaurantOffersFragmentRvOffer.setVisibility(View.VISIBLE);
                        restaurantOfferAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<RestaurantOffer> call, Throwable t) {

            }
        });
    }

    private void hideButton() {
        restaurantOffersFragmentRvOffer.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && restaurantOffersFragmentBtnAddOffer.getVisibility() == View.VISIBLE) {
                    restaurantOffersFragmentBtnAddOffer.setVisibility(View.GONE);

                } else if (dy < 0 && restaurantOffersFragmentBtnAddOffer.getVisibility() != View.VISIBLE) {
                    restaurantOffersFragmentBtnAddOffer.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.animation_down_to_up));
                    restaurantOffersFragmentBtnAddOffer.setVisibility(View.VISIBLE);
                }
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

    @OnClick(R.id.restaurant_offers_fragment_btn_add_offer)
    public void onViewClicked() {
        HelperMethod.replace(new RestaurantAddOffersFragment(), getActivity().getSupportFragmentManager()
                , R.id.restaurant_cycle_fl_fragment_container, null, null);
    }
}
