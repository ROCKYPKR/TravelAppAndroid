package com.eshna.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class PackageReview {

    @SerializedName("package_id")
    private int packageId;
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

    public PackageReview(int packageId, String userId, String reviewText, String rating, String createdAt, int id, User user) {
        this.packageId = packageId;
        this.userId = userId;
        this.reviewText = reviewText;
        this.rating = rating;
        this.createdAt = createdAt;
        this.id = id;
        this.user = user;
    }

    public int getPackageId() {
        return packageId;
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
        return "PackageReview{" +
                "packageId=" + packageId +
                ", userId='" + userId + '\'' +
                ", reviewText='" + reviewText + '\'' +
                ", rating='" + rating + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", id=" + id +
                ", user=" + user +
                '}';
    }
}
