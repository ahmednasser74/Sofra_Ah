package com.example.sofra.ui.fragment.userCycle.userHome.userRestaurantMenu.restaurantDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofra.R;
import com.example.sofra.adapter.UserRestaurantCategoryAdapter;
import com.example.sofra.adapter.UserRestaurantMenuAdapter;
import com.example.sofra.data.model.listRestaurantItem.FoodItemData;
import com.example.sofra.data.model.listRestaurantItem.FoodItems;
import com.example.sofra.data.model.restaurantCategory.CategoriesNotPaginated;
import com.example.sofra.data.model.restaurantCategory.CategoryData;
import com.example.sofra.data.model.restaurantLogin.Restaurant;
import com.example.sofra.helper.OnEndLess;
import com.example.sofra.ui.activity.BaseActivity;
import com.example.sofra.ui.fragment.untitledFolder.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.supercharge.shimmerlayout.ShimmerLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.helper.HelperMethod.dismissProgressDialog;


public class UserRestaurantMenuFragment extends BaseFragment {

    @BindView(R.id.restaurant_menu_fragment_rv_menu)
    RecyclerView restaurantMenuFragmentRvMenu;
    @BindView(R.id.restaurant_menu_fragment_rv_category)
    RecyclerView restaurantMenuFragmentRvCategory;
    @BindView(R.id.restaurant_menu_fragment_tv_no_item)
    TextView restaurantMenuFragmentTvNoItem;
    @BindView(R.id.restaurant_menu_fragment_shimmer_menu)
    ShimmerLayout restaurantMenuFragmentShimmerMenu;

    private LinearLayoutManager linearLayoutManager;
    private List<FoodItemData> listRestaurantItemData = new ArrayList<>();
    private List<CategoryData> listOfCategoryDataList = new ArrayList<>();
    private UserRestaurantMenuAdapter restaurantItemAdapter;
    private UserRestaurantCategoryAdapter userRestaurantCategoryAdapter;
    public Restaurant restaurantData;
    private OnEndLess onEndLess;
    private int maxPage = 0;
    public int id = 0;

    public UserRestaurantMenuFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        getClient();
        setUpActivity();
        View view = inflater.inflate(R.layout.fragment_user_restaurant_item, container, false);
        ButterKnife.bind(this, view);

        menuInit();
        categoryInit();
        hideRecycler();
        return view;
    }

    private void hideRecycler() {
//        final boolean[] check_ScrollingUp = {false};
//        restaurantMenuFragmentRvMenu.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (dy > 0) {
//                    // Scrolling up
//                    if (check_ScrollingUp[0]) {
//                        restaurantMenuFragmentRvCategory.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.animation_down_to_up));
//                        restaurantMenuFragmentRvCategory.setVisibility(View.GONE);
//                        check_ScrollingUp[0] = false;
//                    }
//
//                } else {
//                    // User scrolls down
//                    if (!check_ScrollingUp[0]) {
//                        restaurantMenuFragmentRvCategory.startAnimation(AnimationUtils
//                                .loadAnimation(getActivity(), R.anim.animation_up_to_down));
//                        restaurantMenuFragmentRvCategory.setVisibility(View.VISIBLE);
//                        check_ScrollingUp[0] = true;
//
//                    }
//                }
//            }
//
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//        });
    }

    private void categoryInit() {

        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        restaurantMenuFragmentRvCategory.setLayoutManager(linearLayoutManager);

        userRestaurantCategoryAdapter = new UserRestaurantCategoryAdapter((BaseActivity) getActivity(), listOfCategoryDataList, restaurantData.getId(), UserRestaurantMenuFragment.this);
        restaurantMenuFragmentRvCategory.setAdapter(userRestaurantCategoryAdapter);
        if (listOfCategoryDataList.size() == 0) {
            getCategoryList();
        } else {

            restaurantMenuFragmentShimmerMenu.stopShimmerAnimation();
            restaurantMenuFragmentShimmerMenu.setVisibility(View.GONE);
        }

    }

    private void getCategoryList() {

        listOfCategoryDataList.add(new CategoryData(0, "ALL", "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcThgwBsaHfG9JsLTxTWUboS18RYAa8W4xSBHwUx6QED-ytXC85e"));

        getClient().getUserCategory(restaurantData.getId()).enqueue(new Callback<CategoriesNotPaginated>() {
            @Override
            public void onResponse(Call<CategoriesNotPaginated> call, Response<CategoriesNotPaginated> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        listOfCategoryDataList.addAll(response.body().getData());
                        userRestaurantCategoryAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<CategoriesNotPaginated> call, Throwable t) {
                try {
                    dismissProgressDialog();
                } catch (Exception e) {
                }
            }
        });
    }

    public void menuInit() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        restaurantMenuFragmentRvMenu.setLayoutManager(linearLayoutManager);

        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        if ((listRestaurantItemData.size() + 1) / 10 != current_page) {
                            onEndLess.previous_page = current_page;
                            getMenuList(restaurantData.getId(), id, current_page);
                        }
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }
            }
        };
        restaurantMenuFragmentRvMenu.addOnScrollListener(onEndLess);


        if (listRestaurantItemData.size() == 0) {

            restaurantMenuFragmentShimmerMenu.startShimmerAnimation();
            restaurantMenuFragmentShimmerMenu.setVisibility(View.VISIBLE);

            restaurantItemAdapter = new UserRestaurantMenuAdapter((BaseActivity) getActivity(), listRestaurantItemData);
            restaurantMenuFragmentRvMenu.setAdapter(restaurantItemAdapter);
            getMenuList(restaurantData.getId(), id, 1);
        } else {
            restaurantMenuFragmentRvMenu.setAdapter(restaurantItemAdapter);
            restaurantMenuFragmentRvMenu.setVisibility(View.VISIBLE);
        }
    }

    public void getMenuList(int restaurantId, int categoryId, int page) {
        dismissProgressDialog();
        getClient().getRestaurantItem(restaurantId, categoryId, page).
                enqueue(new Callback<FoodItems>() {
                    @Override
                    public void onResponse(Call<FoodItems> call, Response<FoodItems> response) {
                        dismissProgressDialog();
                        try {
                            if (response.body().getStatus() == 1) {
                                maxPage = response.body().getData().getLastPage();
                                if (page == 1) {
                                    restaurantMenuFragmentShimmerMenu.stopShimmerAnimation();
                                    restaurantMenuFragmentShimmerMenu.setVisibility(View.GONE);
                                    listRestaurantItemData.clear();
                                }

                                listRestaurantItemData.addAll(response.body().getData().getData());
                                restaurantItemAdapter.notifyDataSetChanged();

                                if (listRestaurantItemData.size() == 0) {
                                    restaurantMenuFragmentTvNoItem.setVisibility(View.VISIBLE);
                                }
                            }

                        } catch (Exception e) {
                        }
                    }

                    @Override
                    public void onFailure(Call<FoodItems> call, Throwable t) {
                        try {
                            dismissProgressDialog();

                        } catch (Exception e) {

                        }
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        restaurantMenuFragmentShimmerMenu.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        super.onPause();
        restaurantMenuFragmentShimmerMenu.stopShimmerAnimation();
    }

    @Override
    public void onBack() {
        super.onBack();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
        restaurantMenuFragmentShimmerMenu.stopShimmerAnimation();
    }
}
