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
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sofra.R;
import com.example.sofra.data.local.room.OrderItem;
import com.example.sofra.data.local.room.RoomDao;
import com.example.sofra.data.model.restaurantDeleteOffer.RestaurantDeleteOffer;
import com.example.sofra.ui.activity.BaseActivity;
import com.example.sofra.ui.fragment.userCycle.userHome.userRestaurantMenu.ShoppingCartFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.data.local.SharedPreference.LoadData;
import static com.example.sofra.data.local.SharedPreference.RESTAURANT_API_TOKEN;
import static com.example.sofra.data.local.room.RoomManger.getInstance;
import static com.example.sofra.helper.HelperMethod.showProgressDialog;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {

    private BaseActivity activity;
    private List<OrderItem> listOrderItem = new ArrayList<>();
    private RoomDao roomDao;
    private int quantity;
    private ShoppingCartFragment shoppingCartFragment;

    public ShoppingCartAdapter(BaseActivity activity, List<OrderItem> listOrderItem) {
        this.activity = activity;
        this.listOrderItem = listOrderItem;
        roomDao = getInstance(activity).roomDao();
    }


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

        OrderItem orderItem = listOrderItem.get(position);
        Glide.with(activity).load(orderItem.getPhoto()).into(holder.itemShoppingCartImgRestaurantLogo);
        holder.itemShoppingCartTvItemName.setText(orderItem.getItem_name());
        holder.itemShoppingCartTvQuantity.setText(String.valueOf(orderItem.getQuantity()));
        holder.itemShoppingCartTvItemPrice.setText(String.valueOf(orderItem.getPrice()));
    }

    private void setAction(ViewHolder holder, int position) {
        OrderItem orderItem = listOrderItem.get(position);

        holder.itemShoppingCartImgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Executors.newSingleThreadExecutor().execute(new Runnable() {
//                    @Override
//                    public void run() {
                quantity = orderItem.getQuantity();
                quantity++;
                orderItem.setQuantity(quantity);
                roomDao.update(orderItem);
                holder.itemShoppingCartTvQuantity.setText(String.valueOf(orderItem.getQuantity()));

            }
//                });
//            }
        });

        holder.itemShoppingCartImgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        quantity = orderItem.getQuantity();
                        if (quantity > 1) {
                            quantity--;
                            orderItem.setQuantity(quantity);
                            roomDao.update(orderItem);
                            holder.itemShoppingCartTvQuantity.setText(String.valueOf(orderItem.getQuantity()));
                        }
                    }
                });
            }
        });

        holder.itemShoppingCartImgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alert;
                AlertDialog.Builder dialog2 = new AlertDialog.Builder(activity);
                alert = dialog2.create();
                alert.setTitle("Delete ?");
                alert.setMessage("sure about delete item from cart?");
                alert.setButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Executors.newSingleThreadExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                roomDao.removeItem(orderItem);
                                (activity).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        listOrderItem.remove(position);
                                        notifyDataSetChanged();
                                        roomDao.update(orderItem);
                                        holder.itemShoppingCartTvQuantity.setText(String.valueOf(orderItem.getQuantity()));
                                    }
                                });
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


    //        public void increment() {
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
        return listOrderItem.size();
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
        @BindView(R.id.item_shopping_cart_tv_item_price)
        TextView itemShoppingCartTvItemPrice;

        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
//                else if (quantity < 1) {
//
//                    final AlertDialog alert;
//                    AlertDialog.Builder dialog2 = new AlertDialog.Builder(activity);
//                    alert = dialog2.create();
//                    alert.setTitle("Delete ?");
//                    alert.setMessage("Are you sure you want to delete this Category?");
//                    showProgressDialog(activity, "please wait...");
//                    alert.setButton("Yes", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            Executors.newSingleThreadExecutor().execute(new Runnable() {
//                                @Override
//                                public void run() {
//                                    roomDao.removeItem(orderItem);
//                                    (activity).runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            listOrderItem.remove(position);
//                                            notifyDataSetChanged();
//                                        }
//                                    });
//                                }
//                            });
//
//                        }
//                    });
//                    alert.setButton2("No", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            alert.dismiss();
//                        }
//                    });
//                    alert.show();
//                }
