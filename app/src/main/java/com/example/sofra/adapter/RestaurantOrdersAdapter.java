package com.example.sofra.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sofra.R;
import com.example.sofra.data.model.userOrders.UserOrdersData;
import com.example.sofra.view.activity.BaseActivity;
import com.example.sofra.view.activity.SplashCycleActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class RestaurantOrdersAdapter extends RecyclerView.Adapter<RestaurantOrdersAdapter.ViewHolder> {


    private BaseActivity activity;
    private List<UserOrdersData> userOrdersDataList = new ArrayList<>();
    private String status;

    public RestaurantOrdersAdapter(BaseActivity activity, List<UserOrdersData> userOrdersData, String status) {
        this.activity = activity;
        this.userOrdersDataList = userOrdersData;
        this.status = status;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_restaurant_orders,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {
        UserOrdersData userOrdersData = userOrdersDataList.get(position);

        Glide.with(activity).load(userOrdersData.getRestaurant().getPhotoUrl()).into(holder.itemRestaurantOrdersImgClient);
        holder.itemRestaurantOrdersTvAddress.setText(userOrdersData.getAddress());
        holder.itemRestaurantOrdersTvTotalAmount.setText(userOrdersData.getTotal());
        holder.itemRestaurantOrdersTvOrderNumber.setText(userOrdersData.getRestaurantId());
        holder.itemRestaurantOrdersTvRestaurantName.setText(userOrdersData.getRestaurant().getName());
        holder.itemRestaurantOrdersBtnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = userOrdersData.getRestaurant().getPhone();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                activity.startActivity(intent);
            }
        });

        if (status.equals("pending")) {
            holder.itemRestaurantOrdersBtnCancelOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialog dialog = new Dialog(activity);
                    LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View v = inflater.inflate(R.layout.dialog_restaurant_cancel_reason, null);
                    dialog.setContentView(v);

                    Button cancelBtn = (Button) dialog.findViewById(R.id.item_restaurant_cancel_reason_dialog_btn_cancel);
                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(activity, "Order Canceled", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            });


        } else if (status.equals("current")) {
            holder.itemRestaurantOrdersTvConfirm.setText("Confirm Delivery");
            holder.itemRestaurantOrdersBtnCancelOrder.setVisibility(View.GONE);

        } else if (status.equals("completed")) {
            holder.itemRestaurantOrdersTvConfirm.setText("Completed Order");
//            holder.itemRestaurantOrdersBtnConfirm.setBackgroundColor(R.drawable.blue_shape);
//            holder.itemRestaurantOrdersBtnConfirm.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            holder.itemRestaurantOrdersTvConfirm.setTextSize(20);
            holder.itemRestaurantOrdersImgConfirm.setVisibility(View.GONE);
            holder.itemRestaurantOrdersBtnCancelOrder.setVisibility(View.GONE);
            holder.itemRestaurantOrdersBtnCall.setVisibility(View.GONE);
        }
    }

    private void setAction(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return userOrdersDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_restaurant_orders_img_client)
        CircleImageView itemRestaurantOrdersImgClient;
        @BindView(R.id.item_restaurant_orders_tv_restaurant_name)
        TextView itemRestaurantOrdersTvRestaurantName;
        @BindView(R.id.item_restaurant_orders_tv_order_number)
        TextView itemRestaurantOrdersTvOrderNumber;
        @BindView(R.id.item_restaurant_orders_tv_total_amount)
        TextView itemRestaurantOrdersTvTotalAmount;
        @BindView(R.id.item_restaurant_orders_tv_address)
        TextView itemRestaurantOrdersTvAddress;
        @BindView(R.id.item_restaurant_orders_btn_cancel_order)
        LinearLayout itemRestaurantOrdersBtnCancelOrder;
        @BindView(R.id.item_restaurant_orders_img_confirm)
        ImageView itemRestaurantOrdersImgConfirm;
        @BindView(R.id.item_restaurant_orders_tv_confirm)
        TextView itemRestaurantOrdersTvConfirm;
        @BindView(R.id.item_restaurant_orders_btn_call)
        LinearLayout itemRestaurantOrdersBtnCall;
        @BindView(R.id.item_restaurant_orders_btn_confirm)
        LinearLayout itemRestaurantOrdersBtnConfirm;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}