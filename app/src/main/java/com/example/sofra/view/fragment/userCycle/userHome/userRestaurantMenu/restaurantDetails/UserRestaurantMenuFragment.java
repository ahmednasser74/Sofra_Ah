package com.example.sofra.view.fragment.userCycle.userHome.userRestaurantMenu.restaurantDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofra.R;
import com.example.sofra.adapter.UserRestaurantMenuAdapter;
import com.example.sofra.adapter.UserRestaurantCategoryAdapter;
import com.example.sofra.data.model.listRestaurantItem.FoodItemData;
import com.example.sofra.data.model.listRestaurantItem.FoodItems;
import com.example.sofra.data.model.restaurantCategory.CategoriesNotPaginated;
import com.example.sofra.data.model.restaurantCategory.CategoryData;
import com.example.sofra.data.model.restaurantLogin.Restaurant;
import com.example.sofra.helper.OnEndLess;
import com.example.sofra.view.activity.BaseActivity;
import com.example.sofra.view.fragment.untitledFolder.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;


public class UserRestaurantMenuFragment extends BaseFragment {


    @BindView(R.id.restaurant_menu_fragment_rv_menu)
    RecyclerView restaurantMenuFragmentRvMenu;
    @BindView(R.id.restaurant_menu_fragment_rv_category)
    RecyclerView restaurantMenuFragmentRvCategory;
    @BindView(R.id.user_restaurant_fragment_pb_loading)
    ProgressBar userRestaurantFragmentPbLoading;

    private int maxPage = 0;
    private LinearLayoutManager linearLayoutManager;
    private OnEndLess onEndLess;
    private List<FoodItemData> listRestaurantItemData = new ArrayList<>();
    private List<CategoryData> listOfCategoryDataList = new ArrayList<>();
    private UserRestaurantMenuAdapter restaurantItemAdapter;
    public int id = -1;
    private UserRestaurantCategoryAdapter userRestaurantCategoryAdapter;
    public Restaurant restaurantData;

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

        restaurantMenuFragmentRvCategory.setVisibility(View.GONE);
        restaurantMenuFragmentRvMenu.setVisibility(View.GONE);
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

        userRestaurantCategoryAdapter = new UserRestaurantCategoryAdapter((BaseActivity) getActivity(), listOfCategoryDataList);
        restaurantMenuFragmentRvCategory.setAdapter(userRestaurantCategoryAdapter);
        if (listOfCategoryDataList.size() == 0) {
            getCategoryList();
        } else {
            restaurantMenuFragmentRvCategory.setVisibility(View.VISIBLE);
        }
    }

    private void getCategoryList() {
        getClient().getUserCategory(restaurantData.getId()).enqueue(new Callback<CategoriesNotPaginated>() {
            @Override
            public void onResponse(Call<CategoriesNotPaginated> call, Response<CategoriesNotPaginated> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        listOfCategoryDataList.addAll(response.body().getData());
                        restaurantMenuFragmentRvCategory.setVisibility(View.VISIBLE);
                        userRestaurantCategoryAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<CategoriesNotPaginated> call, Throwable t) {
                try {

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
                            getMenuList(current_page);
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
            restaurantItemAdapter = new UserRestaurantMenuAdapter((BaseActivity) getActivity(), listRestaurantItemData);
            restaurantMenuFragmentRvMenu.setAdapter(restaurantItemAdapter);
            getMenuList(1);
        } else {
            restaurantMenuFragmentRvMenu.setAdapter(restaurantItemAdapter);
            restaurantMenuFragmentRvMenu.setVisibility(View.VISIBLE);
        }
    }

    public void getMenuList(int page) {
        getClient().getRestaurantItem(restaurantData.getId(), id, page).
                enqueue(new Callback<FoodItems>() {
                    @Override
                    public void onResponse(Call<FoodItems> call, Response<FoodItems> response) {
                        try {
                            if (response.body().getStatus() == 1) {
                                maxPage = response.body().getData().getLastPage();
                                listRestaurantItemData.addAll(response.body().getData().getData());
                                restaurantMenuFragmentRvMenu.setVisibility(View.VISIBLE);
                                restaurantItemAdapter.notifyDataSetChanged();
                            }

                        } catch (Exception e) {
                        }
                    }

                    @Override
                    public void onFailure(Call<FoodItems> call, Throwable t) {
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
