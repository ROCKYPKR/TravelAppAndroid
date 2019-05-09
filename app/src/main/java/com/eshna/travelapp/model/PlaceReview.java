package com.eshna.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class PlaceReview {

    @SerializedName("place_id")
    private int placeId;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("review")
    private String reviewText;
    @SerializedName("rating")
    private String rating;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("id")
    private int id;
    @SerializedName("user")
    private User user; //The user who provided this reviewText

    public PlaceReview(int placeId, String userId, String reviewText, String rating, String createdAt, int id, User user) {
        this.placeId = placeId;
        this.userId = userId;
        this.reviewText = reviewText;
        this.rating = rating;
        this.createdAt = createdAt;
        this.id = id;
        this.user = user;
    }

    public int getPlaceId() {
        return placeId;
    }

    public String getUserId() {
        return userId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public String getRating() {
        return rating;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "PlaceReview{" +
                "placeId=" + placeId +
                ", userId='" + userId + '\'' +
                ", reviewText='" + reviewText + '\'' +
                ", rating='" + rating + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", id=" + id +
                ", user=" + user +
                '}';
    }
}
