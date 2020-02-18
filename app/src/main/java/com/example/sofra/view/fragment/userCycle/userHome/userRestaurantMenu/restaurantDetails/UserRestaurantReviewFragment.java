package com.example.sofra.view.fragment.userCycle.userHome.userRestaurantMenu.restaurantDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofra.R;
import com.example.sofra.adapter.UserRestaurantReviewAdapter;
import com.example.sofra.data.model.restaurantLogin.Restaurant;
import com.example.sofra.data.model.userRestaurantReview.UserRestaurantReview;
import com.example.sofra.data.model.userRestaurantReview.UserRestaurantReviewData;
import com.example.sofra.helper.OnEndLess;
import com.example.sofra.dialogs.smileDialog.DialogCallback;
import com.example.sofra.dialogs.smileDialog.GlobalUtils;
import com.example.sofra.view.activity.BaseActivity;
import com.example.sofra.view.fragment.untitledFolder.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;


public class UserRestaurantReviewFragment extends BaseFragment {

    @BindView(R.id.restaurant_review_btn_add_review)
    Button restaurantReviewBtnAddReview;
    @BindView(R.id.restaurant_review_rv_review)
    RecyclerView restaurantReviewRvReview;

    public Restaurant restaurantData;
    private OnEndLess onEndLess;
    private int maxPage = 0;
    private List<UserRestaurantReviewData> userRestaurantReviewDataList = new ArrayList<>();
    private UserRestaurantReviewAdapter userRestaurantReviewAdapter;

    public UserRestaurantReviewFragment() {
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
        View view = inflater.inflate(R.layout.fragment_user_restaurant_review, container, false);
        ButterKnife.bind(this, view);

        restaurantReviewRvReview.setVisibility(View.GONE);

        init();
        return view;
    }

    public void showDialog() {
        // create and show dialog
        GlobalUtils.showDiallog(getActivity(), new DialogCallback() {
            @Override
            public void callback(String rating) {
                Toast.makeText(getActivity(), "Thanks For Review", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        restaurantReviewRvReview.setLayoutManager(linearLayoutManager);


        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        if ((userRestaurantReviewDataList.size() + 1) / 10 != current_page) {
                            onEndLess.previous_page = current_page;
                            getReviewList(current_page);
                        }
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }
            }
        };
        restaurantReviewRvReview.addOnScrollListener(onEndLess);

        if (userRestaurantReviewDataList.size() == 0) {
            userRestaurantReviewAdapter = new UserRestaurantReviewAdapter((BaseActivity) getActivity(), userRestaurantReviewDataList);
            restaurantReviewRvReview.setAdapter(userRestaurantReviewAdapter);
            getReviewList(1);
        } else {
            restaurantReviewRvReview.setAdapter(userRestaurantReviewAdapter);
            restaurantReviewRvReview.setVisibility(View.VISIBLE);
        }
    }

    private void getReviewList(int page) {
        getClient().getUserRestaurantReview("HRbqKFSaq5ZpsOKITYoztpFZNylmzL9elnlAThxZSZ52QWqVBIj8Rdq7RhoB",
                restaurantData.getId(), page).enqueue(new Callback<UserRestaurantReview>() {
            @Override
            public void onResponse(Call<UserRestaurantReview> call, Response<UserRestaurantReview> response) {
                try {

                    if (response.body().getStatus() == 1) {
                        userRestaurantReviewDataList.addAll(response.body().getData().getData());
                        restaurantReviewRvReview.setVisibility(View.VISIBLE);
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

    @OnClick(R.id.restaurant_review_btn_add_review)
    public void onViewClicked() {
        showDialog();
    }
}
