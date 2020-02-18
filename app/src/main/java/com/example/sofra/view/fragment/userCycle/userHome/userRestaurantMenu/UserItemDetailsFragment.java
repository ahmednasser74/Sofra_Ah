package com.example.sofra.view.fragment.userCycle.userHome.userRestaurantMenu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sofra.R;
import com.example.sofra.data.model.listRestaurantItem.FoodItemData;
import com.example.sofra.view.activity.BaseActivity;
import com.example.sofra.view.fragment.untitledFolder.BaseFragment;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class UserItemDetailsFragment extends BaseFragment {

    @BindView(R.id.item_details_fragment_btn_plus)
    Button itemDetailsFragmentBtnPlus;
    @BindView(R.id.item_details_fragment_tv_quantity)
    TextView itemDetailsFragmentTvQuantity;
    @BindView(R.id.item_details_fragment_btn_minus)
    Button itemDetailsFragmentBtnMinus;
    @BindView(R.id.item_details_fragment_btn_shopping_cart)
    Button itemDetailsFragmentBtnShoppingCart;
    @BindView(R.id.item_details_fragment_img_background)
    ImageView itemDetailsFragmentImgBackground;
    @BindView(R.id.item_details_fragment_tv_meal_name)
    TextView itemDetailsFragmentTvMealName;
    @BindView(R.id.item_details_fragment_tv_meal_details)
    TextView itemDetailsFragmentTvMealDetails;
    @BindView(R.id.item_details_fragment_tv_meal_price)
    TextView itemDetailsFragmentTvMealPrice;

    public FoodItemData restaurantItem;
    private int quantity = 1;
    private int id = -1;
    private BaseActivity activity;

    public UserItemDetailsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        setUpActivity();
        View view = inflater.inflate(R.layout.fragment_user_item_details, container, false);
        ButterKnife.bind(this, view);

        getItemDetailsData();
        return view;
    }

    private void getItemDetailsData() {
        Picasso.get().load(restaurantItem.getPhotoUrl()).into(itemDetailsFragmentImgBackground);
        itemDetailsFragmentTvMealName.setText(restaurantItem.getName());
        itemDetailsFragmentTvMealDetails.setText(restaurantItem.getDescription());
        if (restaurantItem.getHasOffer()) {
            itemDetailsFragmentTvMealPrice.setText(restaurantItem.getOfferPrice());
        } else {
            itemDetailsFragmentTvMealPrice.setText(restaurantItem.getPrice());
        }
    }

    public void increment() {
        if (quantity < 100) {
            quantity++;
        } else {
            Toast.makeText(getActivity(), "can't order above 100 item", Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);

    }

    public void decrement() {
        int x = 0;
        if (quantity > 1) {
            quantity--;
        } else {
            Toast.makeText(getActivity(), "can't order below 1 item ", Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);
    }

    private void displayQuantity(int number1) {
        itemDetailsFragmentTvQuantity.setText("" + number1);
    }

    @OnClick({R.id.item_details_fragment_btn_plus, R.id.item_details_fragment_tv_quantity, R.id.item_details_fragment_btn_minus})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item_details_fragment_btn_plus:
                increment();
                break;
            case R.id.item_details_fragment_tv_quantity:
                displayQuantity(1);
                break;
            case R.id.item_details_fragment_btn_minus:
                decrement();
                break;
        }
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
