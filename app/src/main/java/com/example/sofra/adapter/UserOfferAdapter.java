package com.example.sofra.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofra.R;
import com.example.sofra.data.model.restaurantOffer.OfferData;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.UI.activity.BaseActivity;
import com.example.sofra.UI.fragment.userCycle.userMore.offer.UserOfferDetailsFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserOfferAdapter extends RecyclerView.Adapter<UserOfferAdapter.ViewHolder> {

    private BaseActivity activity;
    private List<OfferData> listUserListOfferData = new ArrayList<>();
    OfferData userListOfferData;

    public UserOfferAdapter(BaseActivity activity, List<OfferData> listUserListOfferData) {
        this.activity = activity;
        this.listUserListOfferData = listUserListOfferData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_user_offers,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {
        userListOfferData = listUserListOfferData.get(position);
        holder.itemUserOffersTvOffer.setText(userListOfferData.getName());
        Picasso.get().load(userListOfferData.getPhotoUrl()).into(holder.itemUserOffersImgRestaurantLogo);
    }

    private void setAction(ViewHolder holder, int position) {
        holder.position = position;

        holder.itemUserOffersBtnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserOfferDetailsFragment.userDetailsListOfferData = listUserListOfferData.get(position);
                HelperMethod.replace(new UserOfferDetailsFragment(), activity.getSupportFragmentManager(),
                        R.id.user_cycle_activity_fl_container, null, null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listUserListOfferData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_user_offers_tv_offer)
        TextView itemUserOffersTvOffer;
        @BindView(R.id.item_user_offers_btn_details)
        Button itemUserOffersBtnDetails;
        @BindView(R.id.item_user_offers_img_restaurant_logo)
        CircleImageView itemUserOffersImgRestaurantLogo;

        private View view;
        private int position;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
