package com.eshna.travelapp.apiResponse;

import com.eshna.travelapp.model.Booking;
import com.google.gson.annotations.SerializedName;

public class BookHotelResponse {
    @SerializedName("code")
    private String code;
    @SerializedName("data")
    private Booking booking;
    @SerializedName("error")
    private String error;

    public BookHotelResponse(String code, Booking booking, String error) {
        this.code = code;
        this.booking = booking;
        this.error = error;
    }

    public String getCode() {
        return code;
    }

    public Booking getBooking() {
        return booking;
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        return "BookHotelResponse{" +
                "code='" + code + '\'' +
                ", booking=" + booking +
                ", error='" + error + '\'' +
                '}';
    }
}
