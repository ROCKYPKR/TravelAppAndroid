package com.eshna.travelapp.apiResponse;

import com.eshna.travelapp.model.HotelReview;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class HotelReviewResponse {
    @SerializedName("code")
    private String code;
    @SerializedName("data")
    private HotelReview[] reviews;
    @SerializedName("error")
    private String error;

    public HotelReviewResponse(String code, HotelReview[] reviews, String error) {
        this.code = code;
        this.reviews = reviews;
        this.error = error;
    }

    public String getCode() {
        return code;
    }

    public HotelReview[] getReviews() {
        return reviews;
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        return "HotelReviewResponse{" +
                "code='" + code + '\'' +
                ", reviews=" + Arrays.toString(reviews) +
                ", error='" + error + '\'' +
                '}';
    }
}
