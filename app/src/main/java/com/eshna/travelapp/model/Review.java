package com.eshna.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class Review{
    @SerializedName("hotel_id")
    private int hotelId;
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

    public Review(String userId, String reviewText, String rating, String createdAt, int id, User user) {
        this.userId = userId;
        this.reviewText = reviewText;
        this.rating = rating;
        this.createdAt = createdAt;
        this.id = id;
        this.user = user;
    }

    public Review(int hotelId, String userId, String reviewText, String rating, String createdAt, int id, User user) {
        this.hotelId = hotelId;
        this.userId = userId;
        this.reviewText = reviewText;
        this.rating = rating;
        this.createdAt = createdAt;
        this.id = id;
        this.user = user;
    }

    public int getHotelId() {
        return hotelId;
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
        return "Review{" +
                "hotelId=" + hotelId +
                ", userId='" + userId + '\'' +
                ", reviewText='" + reviewText + '\'' +
                ", rating='" + rating + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", id=" + id +
                ", user=" + user +
                '}';
    }
}
