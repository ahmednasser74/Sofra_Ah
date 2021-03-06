package com.example.sofra.ui.fragment.userCycle.userHome.userRestaurantMenu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofra.R;
import com.example.sofra.adapter.ShoppingCartAdapter;
import com.example.sofra.data.local.room.OrderItem;
import com.example.sofra.data.local.room.RoomDao;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.ui.activity.BaseActivity;
import com.example.sofra.ui.activity.UserCycleActivity;
import com.example.sofra.ui.fragment.untitledFolder.BaseFragment;
import com.example.sofra.ui.fragment.userCycle.userHome.userRestaurantMenu.restaurantDetails.UserRestaurantItemContainerFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.data.local.room.RoomManger.getInstance;


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

    RoomDao roomDao;

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

        roomDao = getInstance(getActivity()).roomDao();
        listOrderItem = getInstance(getActivity()).roomDao().getAll();

        initRecycler();
        setTotalPrice();

        return view;
    }

    private void initRecycler() {
        if (listOrderItem.size() == 0) {

            final AlertDialog alert;
            AlertDialog.Builder dialog2 = new AlertDialog.Builder(getActivity());
            alert = dialog2.create();
            alert.setMessage("sorry cart is empty");
            alert.setCanceledOnTouchOutside(true);
            alert.setButton2("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    onBack();
                    alert.dismiss();
                }
            });
            alert.show();

        } else {

            linearLayoutManager = new LinearLayoutManager(getActivity());
            shoppingCartFragmentRv.setLayoutManager(linearLayoutManager);

            shoppingCartAdapter = new ShoppingCartAdapter((BaseActivity) getActivity(), listOrderItem, this);
            shoppingCartFragmentRv.setAdapter(shoppingCartAdapter);
            shoppingCartAdapter.notifyDataSetChanged();

        }
    }

    public void setTotalPrice() {
        int total = 0;
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

                if (listOrderItem.size() != 0) {
                    HelperMethod.replace(new OrderConfirmationFragment1(), getActivity().getSupportFragmentManager(),
                            R.id.user_cycle_activity_fl_container, null, null);
                } else {
                    Toast.makeText(baseActivity, "Please add Items", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.shopping_cart_fragment_btn_add_more:
                onBack();
                break;
        }
    }
}
