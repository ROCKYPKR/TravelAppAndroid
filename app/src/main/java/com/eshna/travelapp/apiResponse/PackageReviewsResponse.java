package com.eshna.travelapp.apiResponse;

import com.eshna.travelapp.model.PackageReview;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class PackageReviewsResponse {
    @SerializedName("code")
    private String code;
    @SerializedName("data")
    private PackageReview[] reviews;
    @SerializedName("error")
    private String error;

    public PackageReviewsResponse(String code, PackageReview[] reviews, String error) {
        this.code = code;
        this.reviews = reviews;
        this.error = error;
    }

    public String getCode() {
        return code;
    }

    public PackageReview[] getReviews() {
        return reviews;
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        return "PackageReviewsResponse{" +
                "code='" + code + '\'' +
                ", reviews=" + Arrays.toString(reviews) +
                ", error='" + error + '\'' +
                '}';
    }
}
