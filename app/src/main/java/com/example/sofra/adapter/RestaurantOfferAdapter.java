package com.example.sofra.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.sofra.R;
import com.example.sofra.data.model.restaurantOffer.OfferData;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.UI.activity.BaseActivity;
import com.example.sofra.UI.fragment.resturantCycle.resaurantMore.offers.RestaurantAddOffersFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class RestaurantOfferAdapter extends RecyclerView.Adapter<RestaurantOfferAdapter.ViewHolder> {


    private BaseActivity activity;
    private List<OfferData> offerDataList = new ArrayList<>();
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
            public void onClick(View view) {
                OfferData offerData = offerDataList.get(position);
                RestaurantAddOffersFragment restaurantAddOffersFragment = new RestaurantAddOffersFragment();
                restaurantAddOffersFragment.offerData = offerDataList.get(position);

                HelperMethod.replace(new RestaurantAddOffersFragment(), activity.getSupportFragmentManager(),
                        R.id.restaurant_cycle_fl_fragment_container, null, null);
                holder.textView.setText(offerData.getStartingAt());

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

        TextView textView = (TextView) activity.findViewById(R.id.restaurant_add_offer_fragment_et_date_from);

        private View view;
        int position;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
