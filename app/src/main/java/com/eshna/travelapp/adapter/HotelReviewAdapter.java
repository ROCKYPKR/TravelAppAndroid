package com.eshna.travelapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eshna.travelapp.R;
import com.eshna.travelapp.api.ApiClient;
import com.eshna.travelapp.api.ApiInterface;
import com.eshna.travelapp.apiResponse.HotelReviewResponse;
import com.eshna.travelapp.apiResponse.MinimalResponse;
import com.eshna.travelapp.event.HotelReviewDeletedEvent;
import com.eshna.travelapp.event.HotelReviewUpdatedEvent;
import com.eshna.travelapp.model.HotelReview;
import com.eshna.travelapp.utility.UserLocalStore;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelReviewAdapter extends RecyclerView.Adapter<HotelReviewAdapter.ReviewViewHolder> {

    private static final String TAG = HotelAdapter.class.getSimpleName();

    private Context mContext;
    private List<HotelReview> mReviews;

    public HotelReviewAdapter(List<HotelReview> reviewList, Context context){
        this.mReviews = reviewList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_review, viewGroup, false);
        return new ReviewViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelReviewAdapter.ReviewViewHolder reviewViewHolder, final int position) {
        final HotelReview review = mReviews.get(position);

        UserLocalStore userLocalStore = new UserLocalStore(mContext);
        int currentLoggedInUserId = userLocalStore.getLoggedInUser().getId();
        final String apiToken = userLocalStore.getLoggedInUser().getApiToken();

        if (review.getUser().getId() == currentLoggedInUserId){
            reviewViewHolder.editDeletePane.setVisibility(View.VISIBLE);

            reviewViewHolder.editReviewIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    askUpdateConfirmation(review, apiToken, position);
                }
            });

            reviewViewHolder.deleteReviewIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    askDeleteReviewConfirmation(review, apiToken, position);
                }
            });
        }

        reviewViewHolder.ratingBar.setRating(Float.valueOf(review.getRating().trim()));
        reviewViewHolder.userNameTV.setText(review.getUser().getName());
        reviewViewHolder.reviewTV.setText(review.getReviewText());

    }

    private void askDeleteReviewConfirmation(final HotelReview review, final String apiToken, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(R.string.confirm_delete_review)
                .setTitle(R.string.delete)
                .setCancelable(true)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        hitDeleteReviewApi(review.getId(), apiToken, position);
                    }})
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void hitDeleteReviewApi(int reviewId, String apiToken, final int position) {
        ApiInterface apiInterface =
                ApiClient.getClient().create(ApiInterface.class);

        Call<MinimalResponse> call = apiInterface.deleteHotelReview(reviewId, apiToken);

        call.enqueue(new Callback<MinimalResponse>() {
            @Override
            public void onResponse(@NonNull Call<MinimalResponse> call, @NonNull Response<MinimalResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, String.valueOf(response.code()));
                    Log.d(TAG, response.toString());
                    if (response.body()!= null){
                        Log.d(TAG, "onResponse: "+response.body().getMessage());
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        //pass an event that the review is deleted
                        EventBus.getDefault().post(new HotelReviewDeletedEvent(position));
                    }
                } else {
                    Log.e(TAG, "onResponse: Response is not successful");
                }
            }

            @Override
            public void onFailure(@NonNull Call<MinimalResponse> call,@NonNull Throwable t) {
                Log.e(TAG, " in onFailure() ");
                Log.d(TAG, t.getLocalizedMessage());
                t.printStackTrace();
                Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void askUpdateConfirmation(final HotelReview review, final String apiToken, final int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View promptView = layoutInflater.inflate(R.layout.dialog_write_review, null);

        final AlertDialog alertD = new AlertDialog.Builder(mContext).create();

        final EditText userInput =  promptView.findViewById(R.id.et_write_review);
        final RatingBar ratingBar = promptView.findViewById(R.id.rb_write_review);

        TextView tvDone =  promptView.findViewById(R.id.tv_done_write_review);
        TextView tvCancel = promptView.findViewById(R.id.tv_cancel_write_review);

        userInput.setText(review.getReviewText());
        ratingBar.setRating(Float.parseFloat(review.getRating()));

        tvDone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (userInput.getText().toString().isEmpty()){
                    Toast.makeText(mContext, "Please provide review", Toast.LENGTH_SHORT).show();
                } else {
                    String reviewText = userInput.getText().toString().trim();
                    float ratingValue = ratingBar.getRating();

                    if (review.getReviewText().equals(reviewText) && Float.parseFloat(review.getRating()) == ratingValue){
                        //review is not changed at all
                        alertD.dismiss();
                        return;
                    } else {
                        Log.d(TAG, "onClick: "+reviewText+" "+ratingValue);
                        hitUpdateReviewAPI(apiToken, review.getId(), ratingValue, reviewText, position);
                    }
                  alertD.dismiss();
                }
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alertD.dismiss();
            }
        });

        alertD.setView(promptView);

        alertD.show();
    }

    private void hitUpdateReviewAPI(String apiToken, int currentReviewId, float ratingValue, final String reviewText, final int position) {
        ApiInterface apiInterface =
                ApiClient.getClient().create(ApiInterface.class);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("rating", String.valueOf(ratingValue));
        requestBody.put("review", reviewText);

        Call<HotelReviewResponse> call = apiInterface.updateHotelReview(currentReviewId, apiToken, requestBody);


        call.enqueue(new Callback<HotelReviewResponse>() {
            @Override
            public void onResponse(@NonNull Call<HotelReviewResponse> call,@NonNull Response<HotelReviewResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, String.valueOf(response.code()));
                    Log.d(TAG, response.toString());
                    Log.d(TAG, "onResponse: "+response.body().toString());
                    if (response.body() != null) {
                        if (!response.body().isError()){
                            Toast.makeText(mContext, "Review updated", Toast.LENGTH_SHORT).show();
                            //pass an event that the review is updated
                            EventBus.getDefault().post(new HotelReviewUpdatedEvent(response.body().getReview(), position));
                        } else {
                            Log.e(TAG, "onResponse: "+response.body().getCode());
                            Log.e(TAG, "onResponse: "+response.body().getMessage());
                        }
                    } else {
                        Log.e(TAG, "onResponse: Response body is null");
                    }
                } else {
                    Log.e(TAG, "onResponse: Response is unsuccessful");
                }
            }

            @Override
            public void onFailure(@NonNull Call<HotelReviewResponse> call,@NonNull Throwable t) {
                Log.e(TAG, " in onFailure() ");
                Log.d(TAG, t.getLocalizedMessage());
                t.printStackTrace();
                Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mReviews == null ? 0 : mReviews.size());
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.civ_review_user)
        CircleImageView reviewUserIV;
        @BindView(R.id.tv_review_user_name)
        TextView userNameTV;
        @BindView(R.id.tv_review_text)
        TextView reviewTV;
        @BindView(R.id.rb_review_rating)
        RatingBar ratingBar;
        @BindView(R.id.pane_edit_delete_review)
        RelativeLayout editDeletePane;
        @BindView(R.id.iv_edit_review)
        ImageView editReviewIV;
        @BindView(R.id.iv_delete_review)
        ImageView deleteReviewIV;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }
}
