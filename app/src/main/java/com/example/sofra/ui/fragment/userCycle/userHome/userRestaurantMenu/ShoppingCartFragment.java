package com.example.sofra.ui.fragment.userCycle.userHome.userRestaurantMenu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofra.R;
import com.example.sofra.adapter.ShoppingCartAdapter;
import com.example.sofra.data.local.room.OrderItem;
import com.example.sofra.ui.activity.BaseActivity;
import com.example.sofra.ui.fragment.untitledFolder.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.sofra.data.api.ApiClient.getClient;


public class ShoppingCartFragment extends BaseFragment {

    @BindView(R.id.shopping_cart_fragment_rv)
    RecyclerView shoppingCartFragmentRv;
    @BindView(R.id.shopping_cart_fragment_tv_total_price)
    TextView shoppingCartFragmentTvTotalPrice;
    @BindView(R.id.shopping_cart_fragment_btn_confirmation_order)
    Button shoppingCartFragmentBtnConfirmationOrder;
    @BindView(R.id.shopping_cart_fragment_btn_add_more)
    Button shoppingCartFragmentBtnAddMore;

    private LinearLayoutManager linearLayoutManager;
    public List<OrderItem> listOrderItem = new ArrayList<>();
    private ShoppingCartAdapter shoppingCartAdapter;
    private int total;

    public ShoppingCartFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        setUpActivity();
        getClient();

        View view = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        ButterKnife.bind(this, view);

        initRecycler();

        return view;
    }

    private void initRecycler() {

        linearLayoutManager = new LinearLayoutManager(getActivity());
        shoppingCartFragmentRv.setLayoutManager(linearLayoutManager);

        shoppingCartAdapter = new ShoppingCartAdapter((BaseActivity) getActivity(), listOrderItem);
        shoppingCartFragmentRv.setAdapter(shoppingCartAdapter);
        shoppingCartAdapter.notifyDataSetChanged();

      setTotalPrice();
    }

    public void setTotalPrice() {
        for (int i = 0; i < listOrderItem.size(); i++) {

            total = (int) (total + (listOrderItem.get(i).getQuantity() * listOrderItem.get(i).getPrice()));

        }
        shoppingCartFragmentTvTotalPrice.setText(String.valueOf(total));
    }


    @Override
    public void onBack() {
        super.onBack();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.shopping_cart_fragment_btn_confirmation_order, R.id.shopping_cart_fragment_btn_add_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.shopping_cart_fragment_btn_confirmation_order:

                break;
            case R.id.shopping_cart_fragment_btn_add_more:
                onBack();
                break;
        }
    }
}
