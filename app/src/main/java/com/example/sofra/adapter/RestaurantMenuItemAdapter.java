package com.example.sofra.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.sofra.R;
import com.example.sofra.data.model.listRestaurantItem.FoodItemData;
import com.example.sofra.data.model.restaurantCategory.CategoryData;
import com.example.sofra.data.model.restaurantDeleteCategory.RestaurantDeleteCategory;
import com.example.sofra.data.model.restaurantDeleteMenuItem.RestaurantDeleteMenuItem;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.view.activity.BaseActivity;
import com.example.sofra.view.fragment.resturantCycle.restaurantHome.RestaurantAddMenuItemFragment;

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

public class RestaurantMenuItemAdapter extends RecyclerView.Adapter<RestaurantMenuItemAdapter.ViewHolder> {


    private BaseActivity activity;
    private List<FoodItemData> foodItemDataList = new ArrayList<>();
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private FoodItemData foodItemData;
    private CategoryData categoryData;

    public RestaurantMenuItemAdapter(BaseActivity activity, List<FoodItemData> foodItemDataList, CategoryData categoryData) {
        this.activity = activity;
        this.foodItemDataList = foodItemDataList;
        this.categoryData = categoryData;
        viewBinderHelper.setOpenOnlyOne(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_restaurant_menu_item,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
        viewBinderHelper.bind(holder.itemRestaurantMenuSwipe, String.valueOf(foodItemDataList.get(position).getId()));
        holder.itemRestaurantMenuSwipe.computeScroll();
    }

    private void setData(ViewHolder holder, int position) {
        FoodItemData foodItemData = foodItemDataList.get(position);
        holder.itemRestaurantMenuTvMealName.setText(foodItemData.getName());
        holder.itemRestaurantMenuTvMealPrice.setText(foodItemData.getPrice());
        holder.itemRestaurantMenuTvMealDetails.setText(foodItemData.getDescription());
        Glide.with(activity).load(foodItemData.getPhotoUrl()).into(holder.itemRestaurantMenuImg);
    }

    private void setAction(ViewHolder holder, int position) {

        holder.itemRestaurantMenuImgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RestaurantAddMenuItemFragment restaurantAddMenuItemFragment = new RestaurantAddMenuItemFragment();
                restaurantAddMenuItemFragment.foodItemData = foodItemDataList.get(position);
                restaurantAddMenuItemFragment.setMenuItemData();
                restaurantAddMenuItemFragment.categoryData = categoryData;

                HelperMethod.replace(restaurantAddMenuItemFragment, activity.getSupportFragmentManager(),
                        R.id.restaurant_cycle_fl_fragment_container, null, null);

            }
        });

        holder.itemRestaurantMenuImgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog alert;
                AlertDialog.Builder dialog2 = new AlertDialog.Builder(activity);
                alert = dialog2.create();
                alert.setTitle("Delete ?");
                alert.setMessage("Are you sure you want to delete this Category?");
                alert.setButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        getClient().getRestaurantDeleteMenuItem(LoadData(activity, RESTAURANT_API_TOKEN),
                                foodItemDataList.get(position).getId()).enqueue(new Callback<RestaurantDeleteMenuItem>() {
                            @Override
                            public void onResponse(Call<RestaurantDeleteMenuItem> call, Response<RestaurantDeleteMenuItem> response) {
                                try {
                                    if (response.body().getStatus() == 1) {
                                        foodItemDataList.remove(position);
                                        notifyItemRemoved(position);
                                        Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                    }

                                } catch (Exception e) {
                                }
                            }

                            @Override
                            public void onFailure(Call<RestaurantDeleteMenuItem> call, Throwable t) {
                            }
                        });

                    }
                });
                alert.setButton2("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alert.dismiss();
                    }
                });
                alert.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodItemDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_restaurant_menu_img_edit)
        ImageView itemRestaurantMenuImgEdit;
        @BindView(R.id.item_restaurant_menu_img_delete)
        ImageView itemRestaurantMenuImgDelete;
        @BindView(R.id.item_restaurant_menu_img)
        ImageView itemRestaurantMenuImg;
        @BindView(R.id.item_restaurant_menu_tv_meal_name)
        TextView itemRestaurantMenuTvMealName;
        @BindView(R.id.item_restaurant_menu_tv_meal_details)
        TextView itemRestaurantMenuTvMealDetails;
        @BindView(R.id.item_restaurant_menu_tv_meal_price)
        TextView itemRestaurantMenuTvMealPrice;
        @BindView(R.id.item_restaurant_menu_swipe)
        SwipeRevealLayout itemRestaurantMenuSwipe;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
