package com.example.sofra.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sofra.R;
import com.example.sofra.data.model.restaurantCategory.CategoryData;
import com.example.sofra.UI.activity.BaseActivity;
import com.example.sofra.UI.fragment.userCycle.userHome.userRestaurantMenu.restaurantDetails.UserRestaurantMenuFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserRestaurantCategoryAdapter extends RecyclerView.Adapter<UserRestaurantCategoryAdapter.ViewHolder> {


    private BaseActivity activity;
    private List<CategoryData> listOfCategoryDataList = new ArrayList<>();
    private CategoryData categoryData;
    UserRestaurantMenuFragment userRestaurantListFragment;
    private int resId;

    public UserRestaurantCategoryAdapter(BaseActivity activity, List<CategoryData> listOfCategoryDataList, int resId, UserRestaurantMenuFragment userRestaurantMenuFragment) {
        this.activity = activity;
        this.listOfCategoryDataList = listOfCategoryDataList;
        this.resId = resId;
        this.userRestaurantListFragment = userRestaurantMenuFragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_user_category_restaurant,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {
        categoryData = listOfCategoryDataList.get(position);

        holder.itemUserCategoryTvCategoryName.setText(categoryData.getName());
        Glide.with(activity).load(categoryData.getPhotoUrl()).into(holder.itemCategoryRestaurantImgRestaurantLogo);

    }

    private void setAction(ViewHolder holder, int position) {

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userRestaurantListFragment.getMenuList(resId, listOfCategoryDataList.get(position).getId(), 1);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listOfCategoryDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_category_restaurant_img_restaurant_logo)
        CircleImageView itemCategoryRestaurantImgRestaurantLogo;
        @BindView(R.id.item_user_category_tv_category_name)
        TextView itemUserCategoryTvCategoryName;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
