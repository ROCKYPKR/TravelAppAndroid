package com.eshna.travelapp.apiResponse;

import com.eshna.travelapp.model.User;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class UserResponse {
    @SerializedName("error")
    private Boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("code")
    private String code;

    @SerializedName("errors")
    private String[] errors;

    @SerializedName("data")
    private User user;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String[] getErrors() {
        return errors;
    }

    public void setErrors(String[] errors) {
        this.errors = errors;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "error=" + error +
                ", message='" + message + '\'' +
                ", code='" + code + '\'' +
                ", errors=" + Arrays.toString(errors) +
                ", user=" + user +
                '}';
    }
}
