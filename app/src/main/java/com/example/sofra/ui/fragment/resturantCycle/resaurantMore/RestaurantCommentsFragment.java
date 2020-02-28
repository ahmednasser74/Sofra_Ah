package com.example.sofra.ui.fragment.resturantCycle.resaurantMore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofra.R;
import com.example.sofra.adapter.UserRestaurantReviewAdapter;
import com.example.sofra.data.model.userRestaurantReview.UserRestaurantReview;
import com.example.sofra.data.model.userRestaurantReview.UserRestaurantReviewData;
import com.example.sofra.helper.OnEndLess;
import com.example.sofra.ui.activity.BaseActivity;
import com.example.sofra.ui.fragment.untitledFolder.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.data.local.SharedPreference.LoadData;
import static com.example.sofra.data.local.SharedPreference.RESTAURANT_API_TOKEN;


public class RestaurantCommentsFragment extends BaseFragment {

    @BindView(R.id.restaurant_comments_fragment_rv_comments)
    RecyclerView restaurantCommentsFragmentRvComments;

    private OnEndLess onEndLess;
    private int maxPage = 0;
    private List<UserRestaurantReviewData> userRestaurantReviewDataList = new ArrayList<>();
    private UserRestaurantReviewAdapter userRestaurantReviewAdapter;
    public UserRestaurantReviewData restaurantData;

    public RestaurantCommentsFragment() {
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

        View view = inflater.inflate(R.layout.fragment_restaurant_comments, container, false);
        ButterKnife.bind(this, view);
//        init();
        return view;
    }

    private void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        restaurantCommentsFragmentRvComments.setLayoutManager(linearLayoutManager);
        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;
                        getReviewList(current_page);
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }
            }
        };
        restaurantCommentsFragmentRvComments.addOnScrollListener(onEndLess);

        userRestaurantReviewAdapter = new UserRestaurantReviewAdapter((BaseActivity) getActivity(), userRestaurantReviewDataList);
        restaurantCommentsFragmentRvComments.setAdapter(userRestaurantReviewAdapter);
        getReviewList(1);
    }

    private void getReviewList(int page) {

        getClient().getUserRestaurantReview(LoadData(getActivity(), RESTAURANT_API_TOKEN),
                restaurantData.getId(), page).enqueue(new Callback<UserRestaurantReview>() {
            @Override
            public void onResponse(Call<UserRestaurantReview> call, Response<UserRestaurantReview> response) {
                try {

                    if (response.body().getStatus() == 1) {
                        userRestaurantReviewDataList.addAll(response.body().getData().getData());
                        restaurantCommentsFragmentRvComments.setVisibility(View.VISIBLE);
                        userRestaurantReviewAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<UserRestaurantReview> call, Throwable t) {
                try {

                } catch (Exception e) {
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
}
