package com.eshna.travelapp.apiResponse;

import com.eshna.travelapp.model.Review;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class ReviewResponse {
    @SerializedName("code")
    private String code;
    @SerializedName("data")
    private Review[] reviews;
    @SerializedName("error")
    private String error;

    public ReviewResponse(String code, Review[] reviews, String error) {
        this.code = code;
        this.reviews = reviews;
        this.error = error;
    }

    public String getCode() {
        return code;
    }

    public Review[] getReviews() {
        return reviews;
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        return "ReviewResponse{" +
                "code='" + code + '\'' +
                ", reviews=" + Arrays.toString(reviews) +
                ", error='" + error + '\'' +
                '}';
    }
}
