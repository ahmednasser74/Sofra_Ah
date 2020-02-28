package com.example.sofra.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sofra.R;
import com.example.sofra.data.model.userOrders.UserOrdersData;
import com.example.sofra.UI.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserOrdersAdapter extends RecyclerView.Adapter<UserOrdersAdapter.ViewHolder> {

    private BaseActivity activity;
    private List<UserOrdersData> userOrdersDataList = new ArrayList<>();
    private String status;
    private int lastPosition = -1;

    public UserOrdersAdapter(BaseActivity activity, List<UserOrdersData> userOrdersData, String status) {
        this.activity = activity;
        this.userOrdersDataList = userOrdersData;
        this.status = status;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_user_order,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
        setAnimation(holder.itemView, position, holder);
    }

    private void setAnimation(View viewToAnimate, int position, ViewHolder holder) {
//        de 3shan lw 3ayz al animation mysht3sh w ana tal3 tany
//        if (position > lastPosition) {
        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.animation_left_to_right);
        viewToAnimate.startAnimation(animation);
        lastPosition = position;
//        }
    }

    private void setData(ViewHolder holder, int position) {
        UserOrdersData userOrdersData = userOrdersDataList.get(position);

        holder.itemUserNewOrderTvRestaurantName.setText(userOrdersData.getRestaurant().getName());
        holder.itemUserNewOrderTvAddress.setText(userOrdersData.getAddress());
        holder.itemUserNewOrderTvTotalAmount.setText(userOrdersData.getTotal());
        holder.itemUserNewOrderTvOrderNumber.setText(userOrdersData.getRestaurantId());
        Glide.with(activity).load(userOrdersData.getRestaurant().getPhotoUrl()).into(holder.itemUserNewOrderImgRestaurantLogo);

        if (status.equals("pending")) {
            holder.itemUserNewOrderTvCancelOrder.setText("Cancel");
            holder.itemUserNewOrderTvCancelOrder.setAllCaps(false);
            holder.itemUserNewOrderBtnCancelOrder.setBackgroundResource(R.drawable.blue_shape);

        } else if (status.equals("current")) {
            holder.itemUserNewOrderTvCancelOrder.setText("Confirmation");
            holder.itemUserNewOrderTvCancelOrder.setAllCaps(false);
            holder.itemUserNewOrderBtnCancelOrder.setBackgroundResource(R.drawable.green_shape);
        } else if (status.equals("completed")) {
            holder.itemUserNewOrderTvCancelOrder.setText("Completed Order");
            holder.itemUserNewOrderTvCancelOrder.setAllCaps(false);
            holder.itemUserNewOrderIvCancelOrder.setVisibility(View.GONE);
//            holder.itemUserNewOrderBtnCancelOrder.getLayoutParams().width=300;
            holder.itemUserNewOrderBtnCancelOrder.setBackgroundResource(R.drawable.green_shape);

        }
    }

    private void setAction(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return userOrdersDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_user_new_order_tv_restaurant_name)
        TextView itemUserNewOrderTvRestaurantName;
        @BindView(R.id.item_user_new_order_tv_order_number)
        TextView itemUserNewOrderTvOrderNumber;
        @BindView(R.id.item_user_new_order_tv_total_amount)
        TextView itemUserNewOrderTvTotalAmount;
        @BindView(R.id.item_user_new_order_tv_address)
        TextView itemUserNewOrderTvAddress;
        @BindView(R.id.item_user_new_order_btn_cancel_order)
        LinearLayout itemUserNewOrderBtnCancelOrder;
        @BindView(R.id.item_user_new_order_iv_cancel_order)
        ImageView itemUserNewOrderIvCancelOrder;
        @BindView(R.id.item_user_new_order_tv_cancel_order)
        TextView itemUserNewOrderTvCancelOrder;
        @BindView(R.id.item_user_new_order_img_restaurant_logo)
        ImageView itemUserNewOrderImgRestaurantLogo;

        private View view;
        private int position;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}

