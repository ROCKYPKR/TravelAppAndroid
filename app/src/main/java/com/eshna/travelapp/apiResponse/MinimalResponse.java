package com.eshna.travelapp.apiResponse;

import com.google.gson.annotations.SerializedName;

public class MinimalResponse {
    @SerializedName("error")
    private boolean error;
    @SerializedName("code")
    private String code;
    @SerializedName("message")
    private String message;

    public MinimalResponse(boolean error, String code, String message) {
        this.error = error;
        this.code = code;
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "MinimalResponse{" +
                "error=" + error +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
