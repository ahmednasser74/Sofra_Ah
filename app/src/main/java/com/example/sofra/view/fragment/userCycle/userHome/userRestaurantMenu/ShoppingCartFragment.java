package com.example.sofra.view.fragment.userCycle.userHome.userRestaurantMenu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sofra.R;
import com.example.sofra.view.fragment.untitledFolder.BaseFragment;

import butterknife.ButterKnife;

import static com.example.sofra.data.api.ApiClient.getClient;


public class ShoppingCartFragment extends BaseFragment {

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

        return view;
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
