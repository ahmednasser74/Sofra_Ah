package com.example.sofra.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.sofra.R;
import com.example.sofra.data.local.SharedPreference;
import com.example.sofra.data.model.restaurantAddOffer.RestaurantAddOffer;
import com.example.sofra.data.model.restaurantDeleteMenuItem.RestaurantDeleteMenuItem;
import com.example.sofra.data.model.restaurantDeleteOffer.RestaurantDeleteOffer;
import com.example.sofra.data.model.restaurantOffer.OfferData;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.ui.activity.BaseActivity;
import com.example.sofra.ui.fragment.resturantCycle.resaurantMore.offers.RestaurantAddOffersFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.data.local.SharedPreference.LoadData;
import static com.example.sofra.data.local.SharedPreference.RESTAURANT_API_TOKEN;
import static com.example.sofra.helper.HelperMethod.dismissProgressDialog;
import static com.example.sofra.helper.HelperMethod.showProgressDialog;

public class RestaurantOfferAdapter extends RecyclerView.Adapter<RestaurantOfferAdapter.ViewHolder> {


    private BaseActivity activity;
    public List<OfferData> offerDataList = new ArrayList<>();
    public OfferData offerData;
    public int position;
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public RestaurantOfferAdapter(BaseActivity activity, List<OfferData> offerDataList) {
        this.activity = activity;
        this.offerDataList = offerDataList;
        viewBinderHelper.setOpenOnlyOne(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_restaurant_offers,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
        viewBinderHelper.bind(holder.itemRestaurantOffersSwipe, String.valueOf(offerDataList.get(position).getId()));
        holder.itemRestaurantOffersSwipe.computeScroll();
    }

    private void setData(ViewHolder holder, int position) {

        OfferData offerData = offerDataList.get(position);
        holder.itemRestaurantOffersTvOffer.setText(offerData.getName());
        Glide.with(activity).load(offerData.getPhotoUrl()).into(holder.itemRestaurantOffersImgOffer);

    }

    private void setAction(ViewHolder holder, int position) {
        holder.position = position;

        holder.itemRestaurantOffersImgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                offerData = offerDataList.get(position);
                RestaurantAddOffersFragment restaurantAddOffersFragment = new RestaurantAddOffersFragment();
                restaurantAddOffersFragment.offerData = offerDataList.get(position);
                restaurantAddOffersFragment.position = position;
//                restaurantAddOffersFragment.restaurantOfferAdapter.notifyDataSetChanged();

            }
        });

        holder.itemRestaurantOffersImgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog alert;
                AlertDialog.Builder dialog2 = new AlertDialog.Builder(activity);
                alert = dialog2.create();
                alert.setTitle("Delete ?");
                alert.setMessage("Are you sure you want to delete this Category?");
                showProgressDialog(activity, "please wait...");
                alert.setButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        getClient().getRestaurantDeleteOffer(offerData.getId(), LoadData(activity, RESTAURANT_API_TOKEN)).enqueue(new Callback<RestaurantDeleteOffer>() {
                            @Override
                            public void onResponse(Call<RestaurantDeleteOffer> call, Response<RestaurantDeleteOffer> response) {
                                try {

                                    if (response.body().getStatus() == 1) {
                                        offerDataList.remove(position);
                                        notifyItemRemoved(position);
                                        Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                    }

                                } catch (Exception e) {
                                }
                            }

                            @Override
                            public void onFailure(Call<RestaurantDeleteOffer> call, Throwable t) {

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

    @Override
    public int getItemCount() {
        return offerDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_restaurant_offers_tv_offer)
        TextView itemRestaurantOffersTvOffer;
        @BindView(R.id.item_restaurant_offers_img_offer)
        CircleImageView itemRestaurantOffersImgOffer;
        @BindView(R.id.item_restaurant_offers_img_edit)
        ImageView itemRestaurantOffersImgEdit;
        @BindView(R.id.item_restaurant_offers_img_remove)
        ImageView itemRestaurantOffersImgRemove;
        @BindView(R.id.item_restaurant_offers_swipe)
        SwipeRevealLayout itemRestaurantOffersSwipe;

        private View view;
        int position;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
