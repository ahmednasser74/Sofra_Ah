package com.example.sofra.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofra.R;
import com.example.sofra.data.model.userRestaurantReview.UserRestaurantReviewData;
import com.example.sofra.ui.activity.BaseActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserRestaurantReviewAdapter extends RecyclerView.Adapter<UserRestaurantReviewAdapter.ViewHolder> {

    private BaseActivity activity;

    public UserRestaurantReviewAdapter(BaseActivity activity, List<UserRestaurantReviewData> listUserRestaurantReviewData) {
        this.activity = activity;
        this.listUserRestaurantReviewData = listUserRestaurantReviewData;
    }

    private List<UserRestaurantReviewData> listUserRestaurantReviewData = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_user_review_list,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {

        UserRestaurantReviewData userRestaurantReviewData = listUserRestaurantReviewData.get(position);
        Picasso.get().load(userRestaurantReviewData.getRate()).into(holder.itemReviewImgEmojiReview);

        if (userRestaurantReviewData.getRate().equals("1")) {
            holder.itemReviewImgEmojiReview.setImageResource(R.drawable.ic_pink_emoji_angry);
        } else if (userRestaurantReviewData.getRate().equals("2")) {
            holder.itemReviewImgEmojiReview.setImageResource(R.drawable.ic_pink_emoji_sad);
        } else if (userRestaurantReviewData.getRate().equals("3")) {
            holder.itemReviewImgEmojiReview.setImageResource(R.drawable.ic_pink_emoji_smile);
        } else if (userRestaurantReviewData.getRate().equals("4")) {
            holder.itemReviewImgEmojiReview.setImageResource(R.drawable.ic_pink_emoji_laugh);
        } else {
            holder.itemReviewImgEmojiReview.setImageResource(R.drawable.ic_pink_emoji_hearts);
        }

        holder.itemReviewTvName.setText(userRestaurantReviewData.getClient().getName());
        holder.itemReviewTvReviewDescription.setText(userRestaurantReviewData.getComment());
    }

    private void setAction(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return listUserRestaurantReviewData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_review_tv_name)
        TextView itemReviewTvName;
        @BindView(R.id.item_review_tv_review_description)
        TextView itemReviewTvReviewDescription;
        @BindView(R.id.item_review_img_emoji_review)
        ImageView itemReviewImgEmojiReview;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
