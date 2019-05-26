package com.eshna.travelapp.apiResponse;

import com.eshna.travelapp.model.PackageReview;
import com.google.gson.annotations.SerializedName;

public class PackageReviewResponse {
    @SerializedName("code")
    private String code;
    @SerializedName("data")
    private PackageReview review;
    @SerializedName("error")
    private boolean error;
    @SerializedName("message")
    private String message;

    public PackageReviewResponse(String code, PackageReview review, boolean error, String message) {
        this.code = code;
        this.review = review;
        this.error = error;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public PackageReview getReview() {
        return review;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage(){
        return message;
    }

    @Override
    public String toString() {
        return "PackageReviewResponse{" +
                "code='" + code + '\'' +
                ", review=" + review +
                ", error=" + error +
                ", message=" + message +
                '}';
    }
}
