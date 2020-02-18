package com.example.sofra.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.sofra.R;
import com.example.sofra.data.local.SharedPreference;
import com.example.sofra.data.model.restaurantCategory.CategoryData;
import com.example.sofra.data.model.restaurantDeleteCategory.RestaurantDeleteCategory;
import com.example.sofra.dialogs.DialogAddCategory;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.view.activity.BaseActivity;
import com.example.sofra.view.fragment.resturantCycle.restaurantHome.RestaurantMenuFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.data.local.SharedPreference.loadRestaurantData;

public class RestaurantCategoryAdapter extends RecyclerView.Adapter<RestaurantCategoryAdapter.ViewHolder> {


    private BaseActivity activity;
    private List<CategoryData> listRestaurantCategoryData = new ArrayList<>();
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public RestaurantCategoryAdapter(BaseActivity activity, List<CategoryData> listRestaurantCategoryData) {
        this.activity = activity;
        this.listRestaurantCategoryData = listRestaurantCategoryData;
        viewBinderHelper.setOpenOnlyOne(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_restaurant_category,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(listRestaurantCategoryData.get(position).getId()));
        holder.swipeRevealLayout.computeScroll();
    }

    private void setData(ViewHolder holder, int position) {
        CategoryData restaurantCategoryData = listRestaurantCategoryData.get(position);
//        holder.textViewTitle.setText(restaurantCategoryData.getName());
        Picasso.get().load(restaurantCategoryData.getPhotoUrl()).into(holder.itemRestaurantCategoryImgCategory);
        holder.itemRestaurantCategoryTvCategory.setText(restaurantCategoryData.getName());
    }

    private void setAction(ViewHolder holder, int position) {
        holder.position = position;

        holder.itemRestaurantCategoryLinView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RestaurantMenuFragment restaurantMenuItemFragment = new RestaurantMenuFragment();
                restaurantMenuItemFragment.categoryData = listRestaurantCategoryData.get(position);

                HelperMethod.replace(restaurantMenuItemFragment, activity.getSupportFragmentManager(),
                        R.id.restaurant_cycle_fl_fragment_container, null, null);
            }
        });

        holder.itemRestaurantCategoryImgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alert;
                AlertDialog.Builder dialog2 = new AlertDialog.Builder(activity);
                alert = dialog2.create();
                alert.setTitle("Delete ?");
                alert.setMessage("Are you sure you want to delete this Category?");
                alert.setButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        getClient().getRestaurantDeleteCategory("Jptu3JVmDXGpJEaQO9ZrjRg5RuAVCo45OC2AcOKqbVZPmu0ZJPN3T1sm0cWx",
                                listRestaurantCategoryData.get(position).getId()).enqueue(new Callback<RestaurantDeleteCategory>() {
                            @Override
                            public void onResponse(Call<RestaurantDeleteCategory> call, Response<RestaurantDeleteCategory> response) {
                                try {
                                    if (response.body().getStatus() == 1) {
                                        listRestaurantCategoryData.remove(position);
                                        notifyItemRemoved(position);
                                        Toast.makeText(activity, "Category Deleted", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (Exception e) {

                                }
                            }

                            @Override
                            public void onFailure(Call<RestaurantDeleteCategory> call, Throwable t) {

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

        holder.itemRestaurantCategoryImgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogAddCategory dialogAddCategory = new DialogAddCategory(activity, activity, true);
                dialogAddCategory.categoryData = listRestaurantCategoryData.get(position);
                dialogAddCategory.setCategoryData();
//                RestaurantCategoryAdapter restaurantCategoryAdapter = new RestaurantCategoryAdapter(activity, listRestaurantCategoryData);
//                restaurantCategoryAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listRestaurantCategoryData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_restaurant_category_img_category)
        ImageView itemRestaurantCategoryImgCategory;
        @BindView(R.id.item_restaurant_category_tv_category)
        TextView itemRestaurantCategoryTvCategory;
        @BindView(R.id.item_restaurant_category_img_edit)
        ImageView itemRestaurantCategoryImgEdit;
        @BindView(R.id.item_restaurant_category_img_remove)
        ImageView itemRestaurantCategoryImgRemove;
        @BindView(R.id.item_restaurant_category_sw)
        SwipeRevealLayout swipeRevealLayout;
        @BindView(R.id.item_restaurant_category_lin_view)
        LinearLayout itemRestaurantCategoryLinView;

        public int position;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
