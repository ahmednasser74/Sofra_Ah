package com.example.sofra.view.fragment.resturantCycle.restaurantHome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofra.R;
import com.example.sofra.adapter.RestaurantMenuItemAdapter;
import com.example.sofra.data.model.listRestaurantItem.FoodItemData;
import com.example.sofra.data.model.listRestaurantItem.FoodItems;
import com.example.sofra.data.model.restaurantCategory.CategoryData;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.helper.OnEndLess;
import com.example.sofra.view.activity.BaseActivity;
import com.example.sofra.view.fragment.untitledFolder.BaseFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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


public class RestaurantMenuFragment extends BaseFragment {

    @BindView(R.id.restaurant_menu_item_fragment_tv_title)
    TextView restaurantMenuItemFragmentTvTitle;
    @BindView(R.id.restaurant_menu_item_fragment_rv_items)
    RecyclerView restaurantMenuItemFragmentRvItems;
    @BindView(R.id.restaurant_menu_item_fragment_progress)
    ProgressBar restaurantMenuItemFragmentProgress;
    @BindView(R.id.restaurant_menu_item_fragment_btn_float)
    FloatingActionButton restaurantMenuItemFragmentBtnFloat;
    @BindView(R.id.restaurant_menu_item_fragment_tv_empty)
    TextView restaurantMenuItemFragmentTvEmpty;

    public CategoryData categoryData;
    private LinearLayoutManager linearLayoutManager;
    private List<FoodItemData> foodItemDataList = new ArrayList<>();
    private RestaurantMenuItemAdapter restaurantMenuItemAdapter;
    private OnEndLess onEndLess;
    private int maxPage = 0;
//    String apiToken = LoadRestaurantData(getActivity()).getApiToken();

    public RestaurantMenuFragment() {
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

        View view = inflater.inflate(R.layout.fragment_restaurant_menu, container, false);
        ButterKnife.bind(this, view);

        floatHidden();
        init();
        restaurantMenuItemFragmentTvTitle.setText(categoryData.getName());

        return view;
    }

    private void init() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        restaurantMenuItemFragmentRvItems.setLayoutManager(linearLayoutManager);

        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        if ((foodItemDataList.size() + 1) / 10 != current_page) {
                            onEndLess.previous_page = current_page;
                            getRestaurantMenu(current_page);
                        }
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                }
            }
        };
        restaurantMenuItemFragmentRvItems.addOnScrollListener(onEndLess);

        if (foodItemDataList.size() == 0) {
            restaurantMenuItemAdapter = new RestaurantMenuItemAdapter((BaseActivity) getActivity(), foodItemDataList, categoryData);
            restaurantMenuItemFragmentRvItems.setAdapter(restaurantMenuItemAdapter);
            getRestaurantMenu(1);
        } else {
            restaurantMenuItemFragmentRvItems.setAdapter(restaurantMenuItemAdapter);
            restaurantMenuItemFragmentRvItems.setVisibility(View.VISIBLE);
        }
    }

    private void getRestaurantMenu(int page) {
//        String apiToken = LoadRestaurantData(getActivity()).getApiToken();

        getClient().getRestaurantMenu(LoadData(getActivity(),RESTAURANT_API_TOKEN),
                categoryData.getId(), page).enqueue(new Callback<FoodItems>() {
            @Override
            public void onResponse(Call<FoodItems> call, Response<FoodItems> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        restaurantMenuItemFragmentProgress.setVisibility(View.GONE);
                        maxPage = response.body().getData().getLastPage();
                        foodItemDataList.addAll(response.body().getData().getData());
                        restaurantMenuItemAdapter.notifyDataSetChanged();

                        if (foodItemDataList.size() == 0) {
                            restaurantMenuItemFragmentTvEmpty.setVisibility(View.VISIBLE);
                        }
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<FoodItems> call, Throwable t) {

            }
        });
    }

    private void floatHidden() {
        restaurantMenuItemFragmentRvItems.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && restaurantMenuItemFragmentBtnFloat.getVisibility() == View.VISIBLE) {
                    restaurantMenuItemFragmentBtnFloat.hide();
                } else if (dy < 0 && restaurantMenuItemFragmentBtnFloat.getVisibility() != View.VISIBLE) {
                    restaurantMenuItemFragmentBtnFloat.show();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        restaurantMenuItemFragmentBtnFloat.show();

    }

    @OnClick(R.id.restaurant_menu_item_fragment_btn_float)
    public void onViewClicked() {

        RestaurantAddMenuItemFragment restaurantAddMenuItemFragment = new RestaurantAddMenuItemFragment();
        restaurantAddMenuItemFragment.categoryData = categoryData;
        HelperMethod.replace(restaurantAddMenuItemFragment, getActivity().getSupportFragmentManager(),
                R.id.restaurant_cycle_fl_fragment_container, null, null);

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
