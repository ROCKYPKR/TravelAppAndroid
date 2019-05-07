package com.eshna.travelapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.eshna.travelapp.R;
import com.eshna.travelapp.model.Review;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private static final String LOG_TAG = HotelAdapter.class.getSimpleName();

    private Context mContext;
    private List<Review> mReviews;

    public ReviewAdapter(List<Review> reviewList, Context context){
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
    public void onBindViewHolder(@NonNull ReviewAdapter.ReviewViewHolder reviewViewHolder, int i) {
        final Review review = mReviews.get(i);

        reviewViewHolder.ratingBar.setRating(Float.valueOf(review.getRating().trim()));
        reviewViewHolder.userNameTV.setText(review.getUser().getName());
        reviewViewHolder.reviewTV.setText(review.getReviewText());

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

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }
}
