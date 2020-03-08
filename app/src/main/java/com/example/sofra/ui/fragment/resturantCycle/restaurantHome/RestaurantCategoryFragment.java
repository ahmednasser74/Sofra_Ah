package com.example.sofra.ui.fragment.resturantCycle.restaurantHome;

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
import com.example.sofra.adapter.RestaurantCategoryAdapter;
import com.example.sofra.data.local.SharedPreference;
import com.example.sofra.data.model.restaurantCategory.CategoriesPaginated;
import com.example.sofra.data.model.restaurantCategory.CategoryData;
import com.example.sofra.dialogs.DialogAddCategory;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.helper.OnEndLess;
import com.example.sofra.ui.activity.BaseActivity;
import com.example.sofra.ui.activity.RestaurantCycleActivity;
import com.example.sofra.ui.fragment.untitledFolder.BaseFragment;
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
import static com.example.sofra.data.local.SharedPreference.RESTAURANT_API_TOKEN;
import static com.example.sofra.helper.HelperMethod.dismissProgressDialog;


public class RestaurantCategoryFragment extends BaseFragment {

    @BindView(R.id.restaurant_category_fragment_rv_category)
    RecyclerView restaurantCategoryFragmentRvCategory;
    @BindView(R.id.restaurant_list_fragment_btn_float)
    FloatingActionButton restaurantListFragmentBtnFloat;
    @BindView(R.id.restaurant_category_fragment_pb_loading)
    ProgressBar restaurantCategoryFragmentPbLoading;

    private LinearLayoutManager linearLayoutManager;
    private RestaurantCategoryAdapter restaurantCategoryAdapter;
    private List<CategoryData> listOfCategoryDatalist = new ArrayList<>();
    private OnEndLess onEndLess;
    private int maxPage = 0;
    View view;

    public RestaurantCategoryFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        setUpActivity();
        View view = inflater.inflate(R.layout.fragment_restaurant_category, container, false);
        ButterKnife.bind(this, view);

        RestaurantCycleActivity restaurantCycleActivity = (RestaurantCycleActivity) getActivity();
        restaurantCycleActivity.setVisibilityBottomNavBar(View.VISIBLE);
        restaurantCycleActivity.setVisibilityToolBar(View.VISIBLE);

        init();
        floatHidden();
        return view;
    }


    private void init() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        restaurantCategoryFragmentRvCategory.setLayoutManager(linearLayoutManager);

        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        if ((listOfCategoryDatalist.size() + 1) / 20 != current_page) {
                            onEndLess.previous_page = current_page;
                            getRestaurantCategory(current_page);
                        }
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                }
            }
        };
        restaurantCategoryFragmentRvCategory.addOnScrollListener(onEndLess);

        if (listOfCategoryDatalist.size() == 0) {
            restaurantCategoryAdapter = new RestaurantCategoryAdapter((BaseActivity) getActivity(), listOfCategoryDatalist);
            restaurantCategoryFragmentRvCategory.setAdapter(restaurantCategoryAdapter);
            getRestaurantCategory(1);
        } else {
            restaurantCategoryFragmentRvCategory.setAdapter(restaurantCategoryAdapter);
            restaurantCategoryFragmentRvCategory.setVisibility(View.VISIBLE);
        }
    }

    private void getRestaurantCategory(int page) {

        getClient().getRestaurantCategory(SharedPreference.LoadData(getActivity(), RESTAURANT_API_TOKEN), page).enqueue(new Callback<CategoriesPaginated>() {
            @Override
            public void onResponse(Call<CategoriesPaginated> call, Response<CategoriesPaginated> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        dismissProgressDialog();
                        maxPage = response.body().getData().getLastPage();
                        listOfCategoryDatalist.addAll(response.body().getData().getData());
                        restaurantCategoryFragmentPbLoading.setVisibility(View.GONE);
                        restaurantCategoryAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<CategoriesPaginated> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.restaurant_list_fragment_btn_float)
    public void onViewClicked() {
        DialogAddCategory dialogAddCategory = new DialogAddCategory(getActivity(), getActivity(), true);
        dialogAddCategory.restaurantCategoryAdapter = restaurantCategoryAdapter;
        dialogAddCategory.show();
    }

    private void floatHidden() {

        restaurantCategoryFragmentRvCategory.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && restaurantListFragmentBtnFloat.getVisibility() == View.VISIBLE) {
                    restaurantListFragmentBtnFloat.hide();
                } else if (dy < 0 && restaurantListFragmentBtnFloat.getVisibility() != View.VISIBLE) {
                    restaurantListFragmentBtnFloat.show();
                }

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        restaurantListFragmentBtnFloat.show();

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

//    String deletedItem = null;
//    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,
//            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//        @Override
//        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//            final int fromPos = viewHolder.getAdapterPosition();
//            final int toPos = target.getAdapterPosition();
//
//            return true;// true if moved, false otherwise
//        }
//
//        @Override
//        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//            final int position = viewHolder.getAdapterPosition();
//            switch (direction) {
//                case ItemTouchHelper.LEFT:
//                    listOfCategoryDatalist.remove(position);
//                    restaurantCategoryAdapter.notifyItemRemoved(position);
//                    Snackbar.make(restaurantCategoryFragmentRvCategory, deletedItem, Snackbar.LENGTH_LONG)
//                            .setAction("UNDO", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    listOfCategoryDatalist.add(position, deletedItem);
//                                }
//                            }).show();
//                    break;
//                case ItemTouchHelper.RIGHT:
//                    break;
//
//            }
//        }
//    };