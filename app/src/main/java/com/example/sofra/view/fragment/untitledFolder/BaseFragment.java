package com.example.sofra.view.fragment.untitledFolder;

import androidx.fragment.app.Fragment;

import com.example.sofra.view.activity.BaseActivity;


public class BaseFragment extends Fragment {

    public BaseActivity baseActivity;
    public BaseFragment baseFragment;

    public void setUpActivity() {
        baseActivity = (BaseActivity) getActivity();
        baseActivity.baseFragment = this;

    }

    public void onBack() {//el on back de 3shan ab2a 3mlt on back fe fragment mesh fe activity
        baseActivity.superBackPressed();
    }

    @Override
    public void onStart() {
        super.onStart();
        setUpActivity();
    }

}
