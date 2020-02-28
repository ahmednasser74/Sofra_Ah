package com.example.sofra.UI.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sofra.UI.fragment.untitledFolder.BaseFragment;

public class BaseActivity extends AppCompatActivity {

    public BaseFragment baseFragment;

    public void superBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        baseFragment.onBack();
    }
}
