package com.example.sofra.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sofra.R;
import com.example.sofra.data.local.SharedPreference;
import com.example.sofra.data.model.restaurantLogin.Restaurant;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.ui.activity.BaseActivity;
import com.example.sofra.ui.fragment.userCycle.userHome.userRestaurantMenu.restaurantDetails.UserRestaurantItemContainerFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.sofra.data.local.SharedPreference.RESTAURANT_ID;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ViewHolder> {


    private BaseActivity activity;
    Restaurant restaurantData;
    private int lastPosition = -1;

    private List<Restaurant> restaurantDataList = new ArrayList<>();

    public RestaurantListAdapter(BaseActivity activity, List<Restaurant> restaurantDataList) {
        this.activity = activity;
        this.restaurantDataList = restaurantDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_user_restaurant_list,
                parent, false);

        return new ViewHolder(view);
    }

    public void updateData(List<Restaurant> listNew, int flag) {
        if (flag == 0) { //append
            for (int i = 0; i < listNew.size(); i++) {
                restaurantDataList.add(listNew.get(i));
                notifyItemInserted(getItemCount());
            }
            //notifyDataSetChanged();
        } else { //clear all
            restaurantDataList.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
        setAnimation(holder.itemView, position, holder);

    }

    private void setAnimation(View viewToAnimate, int position, ViewHolder holder) {
//        de 3shan lw 3ayz al animation mysht3sh w ana tal3 tany
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(activity, R.anim.animation_down_to_up);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    private void setData(ViewHolder holder, int position) {
        restaurantData = restaurantDataList.get(position);

        holder.itemRestaurantListTvRestaurantName.setText(restaurantData.getName());
        if (restaurantData.getAvailability().equals("open")) {
            holder.itemRestaurantListImgOpened.setImageResource(R.drawable.shape_green_circle);
        } else {
            holder.itemRestaurantListImgOpened.setImageResource(R.drawable.shape_red_circle);

//            if (holder.view.isClickable()) {
//                if (!restaurantData.getAvailability().equals("open")) {
//                    Toast.makeText(activity, restaurantData.getName() + "is closed", Toast.LENGTH_SHORT).show();
//                }
//            }

        }

        Glide.with(activity).load(restaurantData.getPhotoUrl()).into(holder.itemRestaurantListImgRestaurantLogo);
        holder.itemRestaurantListRbRating.setRating(restaurantData.getRate());
        holder.itemRestaurantListTvDeliveryFees.setText(restaurantData.getDeliveryCost());
        holder.itemRestaurantListTvMinimumOrder.setText(restaurantData.getMinimumCharger());
        holder.itemRestaurantListTvOpened.setText(restaurantData.getAvailability());
    }

    private void setAction(ViewHolder holder, int position) {
        holder.position = position;

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserRestaurantItemContainerFragment restaurantMenuFragment = new UserRestaurantItemContainerFragment();
                restaurantMenuFragment.restaurantData = restaurantDataList.get(position);

                SharedPreference.SaveData(activity, RESTAURANT_ID, restaurantData.getId());

                HelperMethod.replace(restaurantMenuFragment, activity.getSupportFragmentManager(),
                        R.id.user_cycle_activity_fl_container, null, null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurantDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_restaurant_list_tv_restaurant_name)
        TextView itemRestaurantListTvRestaurantName;
        @BindView(R.id.item_restaurant_list_rb_rating)
        RatingBar itemRestaurantListRbRating;
        @BindView(R.id.item_restaurant_list_tv_minimum_order)
        TextView itemRestaurantListTvMinimumOrder;
        @BindView(R.id.item_restaurant_list_tv_delivery_fees)
        TextView itemRestaurantListTvDeliveryFees;
        @BindView(R.id.item_restaurant_list_img_opened)
        ImageView itemRestaurantListImgOpened;
        @BindView(R.id.item_restaurant_list_tv_opened)
        TextView itemRestaurantListTvOpened;
        @BindView(R.id.item_restaurant_list_img_restaurant_logo)
        ImageView itemRestaurantListImgRestaurantLogo;

        private View view;
        public int position;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
