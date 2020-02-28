package com.example.sofra.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofra.R;
import com.example.sofra.ui.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {

    private int quantity = 1;
    private BaseActivity activity;
//    private List<UserRestaurantReviewData> restaurantDataList = new ArrayList<>();


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_shopping_cart,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {

    }

    private void setAction(ViewHolder holder, int position) {
        holder.itemShoppingCartImgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.itemShoppingCartImgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.itemShoppingCartImgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

//    public void increment() {
//        if (quantity < 100) {
//            quantity++;
//        } else {
//            Toast.makeText(activity, "can't order above 100 cup ", Toast.LENGTH_SHORT).show();
//        }
//        displayQuantity(quantity);
//
//    }
//
//    public void decrement() {
//        int x = 0;
//        if (quantity > 1) {
//            quantity--;
//        } else {
//            Toast.makeText(activity, "can't order below 1 item ", Toast.LENGTH_SHORT).show();
//        }
//        displayQuantity(quantity);
//    }
//
//    private void displayQuantity(int number1) {
//        itemDetailsFragmentTvQuantity.setText("" + number1);
//    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_shopping_cart_img_restaurant_logo)
        ImageView itemShoppingCartImgRestaurantLogo;
        @BindView(R.id.item_shopping_cart_tv_item_name)
        TextView itemShoppingCartTvItemName;
        @BindView(R.id.item_shopping_cart_img_plus)
        ImageView itemShoppingCartImgPlus;
        @BindView(R.id.item_shopping_cart_tv_quantity)
        TextView itemShoppingCartTvQuantity;
        @BindView(R.id.item_shopping_cart_img_minus)
        ImageView itemShoppingCartImgMinus;
        @BindView(R.id.item_shopping_cart_img_delete)
        ImageView itemShoppingCartImgDelete;

        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
