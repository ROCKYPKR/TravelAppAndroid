package com.eshna.travelapp.apiResponse;

import com.eshna.travelapp.model.HotelReview;
import com.google.gson.annotations.SerializedName;

public class HotelReviewResponse {
    @SerializedName("code")
    private String code;
    @SerializedName("data")
    private HotelReview review;
    @SerializedName("error")
    private boolean error;
    @SerializedName("message")
    private String message;

    public HotelReviewResponse(String code, HotelReview review, boolean error, String message) {
        this.code = code;
        this.review = review;
        this.error = error;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public HotelReview getReview() {
        return review;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "HotelReviewResponse{" +
                "code='" + code + '\'' +
                ", review=" + review +
                ", error=" + error +
                ", message='" + message + '\'' +
                '}';
    }
}
