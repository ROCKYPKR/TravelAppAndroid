package com.eshna.travelapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.eshna.travelapp.R;
import com.eshna.travelapp.model.PackageReview;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class PackageReviewAdapter extends RecyclerView.Adapter<PackageReviewAdapter.PackageReviewViewHolder> {

    private static final String LOG_TAG = PackageReviewAdapter.class.getSimpleName();

    private Context mContext;
    private List<PackageReview> mReviews;

    public PackageReviewAdapter(List<PackageReview> reviewList, Context context){
        this.mReviews = reviewList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public PackageReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_review, viewGroup, false);
        return new PackageReviewViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PackageReviewViewHolder reviewViewHolder, int i) {
        final PackageReview review = mReviews.get(i);

        reviewViewHolder.ratingBar.setRating(Float.valueOf(review.getRating().trim()));
        reviewViewHolder.userNameTV.setText(review.getUser().getName());
        reviewViewHolder.reviewTV.setText(review.getReviewText());

    }

    @Override
    public int getItemCount() {
        return (mReviews == null ? 0 : mReviews.size());
    }

    public class PackageReviewViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.civ_review_user)
        CircleImageView reviewUserIV;
        @BindView(R.id.tv_review_user_name)
        TextView userNameTV;
        @BindView(R.id.tv_review_text)
        TextView reviewTV;
        @BindView(R.id.rb_review_rating)
        RatingBar ratingBar;

        public PackageReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }
}