package com.eshna.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class PackageReview extends Review {

    @SerializedName("package_id")
    private int packageId;

    public PackageReview(String user_id, String review, String rating, String created_at, int id, User user) {
        super(user_id, review, rating, created_at, id, user);
    }

    public int getPackageId() {
        return packageId;
    }
}
