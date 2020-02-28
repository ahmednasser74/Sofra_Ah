package com.example.sofra.dialogs.smileDialog;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.data.model.restaurantLogin.Restaurant;
import com.example.sofra.data.model.userAddReview.UserAddReview;
import com.example.sofra.ui.activity.BaseActivity;
import com.hsalf.smilerating.SmileRating;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.example.sofra.data.api.ApiClient.getClient;

public class GlobalUtils {
    public static String rating = "Not given yet";

    @BindView(R.id.smile_rating)
    SmileRating smileRating;
    @BindView(R.id.dialog_btn_add_review)
    Button dialogBtnAddReview;
    @BindView(R.id.et_review_comment)
    EditText etReviewComment;
    private Activity activity;

    public static void showDiallog(Context context, final DialogCallback dialogCallback) {
        //create the dialog
        final CustomDialog dialog = new CustomDialog(context, R.style.customDialogTheme);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.dialog_rating, null);

        dialog.setContentView(v);

        Button dialogBtnAddReview = (Button) dialog.findViewById(R.id.dialog_btn_add_review);
        SmileRating smileRating = (SmileRating) dialog.findViewById(R.id.smile_rating);
        EditText etReviewComment = (EditText) dialog.findViewById(R.id.et_review_comment);

        smileRating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley, boolean reselected) {
                switch (smiley) {
                    case SmileRating.BAD:
                        Log.i(TAG, "Bad");
                        rating = "bad";
                        break;
                    case SmileRating.GOOD:
                        Log.i(TAG, "Good");
                        rating = "Good";
                        break;
                    case SmileRating.GREAT:
                        Log.i(TAG, "Great");
                        rating = "Great";
                        break;
                    case SmileRating.OKAY:
                        Log.i(TAG, "Okay");
                        rating = "Okay";
                        break;
                    case SmileRating.TERRIBLE:
                        Log.i(TAG, "Terrible");
                        rating = "Terrible";
                        break;
                }
            }
        });

        dialogBtnAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dialogCallback != null && etReviewComment != null) {
                    dialogCallback.callback(rating);
                } else {
                    if (etReviewComment.equals("")) {
                        Toast.makeText(context, "Please Write Comment", Toast.LENGTH_SHORT).show();
                    }

                    Toast.makeText(context, "Please Select & Write Review", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void getReview() {
        Restaurant restaurant = new Restaurant();
        String comment = etReviewComment.getText().toString();
        int rate = smileRating.getRating(); // level is from 1 to 5

        init(rate, comment, restaurant.getId());
    }

    private void init(int rate, String comment, int restaurantId) {

        getClient().getUserAddReview(rate, comment, restaurantId, "HRbqKFSaq5ZpsOKITYoztpFZNylmzL9elnlAThxZSZ52QWqVBIj8Rdq7RhoB").enqueue(new Callback<UserAddReview>() {
            @Override
            public void onResponse(Call<UserAddReview> call, Response<UserAddReview> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<UserAddReview> call, Throwable t) {

            }
        });
    }
}

