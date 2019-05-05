package com.eshna.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class Review {
    @SerializedName("user_id")
    private String userId;
    @SerializedName("review")
    private String review;
    @SerializedName("rating")
    private String rating;
    @SerializedName("createdAt")
    private String createdAt;
    @SerializedName("id")
    private int id;
    @SerializedName("user")
    private User user; //The user who provided this review

    public Review(String userId, String review, String rating, String createdAt, int id, User user) {
        this.userId = userId;
        this.review = review;
        this.rating = rating;
        this.createdAt = createdAt;
        this.id = id;
        this.user = user;
    }
}
