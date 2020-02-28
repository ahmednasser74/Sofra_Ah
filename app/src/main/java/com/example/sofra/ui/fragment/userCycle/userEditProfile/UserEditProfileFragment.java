package com.example.sofra.ui.fragment.userCycle.userEditProfile;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sofra.R;
import com.example.sofra.adapter.SpinnersAdapter;
import com.example.sofra.data.model.GeneralRequestSpinner;
import com.example.sofra.data.model.listOfTown.GeneralResponseData;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.helper.MediaLoader;
import com.example.sofra.ui.activity.UserCycleActivity;
import com.example.sofra.ui.fragment.untitledFolder.BaseFragment;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.sofra.data.api.ApiClient.getClient;


public class UserEditProfileFragment extends BaseFragment {

    @BindView(R.id.user_edit_profile_fragment_add_photo)
    CircleImageView editProfileFragmentAddPhoto;
    @BindView(R.id.user_edit_profile_fragment_sp_city)
    Spinner userEditProfileFragmentSpCity;
    @BindView(R.id.user_edit_profile_fragment_sp_town)
    Spinner userEditProfileFragmentSpTown;

    private static final int PERMISSION_REQUEST = 0;
    private static final int RESULT_LOAD_IMAGE = 1;
    UserCycleActivity userCycleActivity = new UserCycleActivity();
    Uri imageUri;

    private SpinnersAdapter cityAdapter, townAdapter;
    private GeneralResponseData listOfCityData;
    private String path;


    public UserEditProfileFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        setUpActivity();
        View view = inflater.inflate(R.layout.fragment_user_edit_profile, container, false);
        ButterKnife.bind(this, view);
        userCycleActivity = (UserCycleActivity) getActivity();

        cityAdapter = new SpinnersAdapter(getActivity());
        townAdapter = new SpinnersAdapter(getActivity());
        GeneralRequestSpinner.getSpinnerCityData(getClient().getCity(), cityAdapter
                , userEditProfileFragmentSpCity, "City", new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i != 0) {
                            GeneralRequestSpinner.getTownSpinnerData(getClient().getTown(cityAdapter.selectedId), townAdapter
                                    , userEditProfileFragmentSpTown, "Town");
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

        return view;
    }
    private void initImage() {
        Album.initialize(AlbumConfig.newBuilder(getActivity())
                .setAlbumLoader(new MediaLoader())
                .build());
        Album.image(this) // Image selection.
                .singleChoice()
                .camera(true)
                .columnCount(3)
                .onResult(new com.yanzhenjie.album.Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        path = result.get(0).getPath();
                        HelperMethod.onLoadImageFromUrl(editProfileFragmentAddPhoto, path, getActivity());
                    }
                })
                .onCancel(new com.yanzhenjie.album.Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                    }
                })
                .start();
    }

    @OnClick({R.id.user_edit_profile_fragment_add_photo, R.id.user_edit_profile_fragment_et_name, R.id.user_edit_profile_fragment_btn_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_edit_profile_fragment_add_photo:
                initImage();
                break;
            case R.id.user_edit_profile_fragment_et_name:

                break;
            case R.id.user_edit_profile_fragment_btn_edit:
                break;
        }
    }

    @Override
    public void onBack() {
        super.onBack();
        userCycleActivity.setSelection(R.id.nav_home);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
