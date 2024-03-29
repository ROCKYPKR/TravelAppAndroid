package com.eshna.travelapp.apiResponse;

import com.eshna.travelapp.model.HotelBooking;
import com.google.gson.annotations.SerializedName;

public class BookHotelResponse {
    @SerializedName("code")
    private String code;
    @SerializedName("data")
    private HotelBooking hotelBooking;
    @SerializedName("error")
    private boolean error;

    public BookHotelResponse(String code, HotelBooking hotelBooking, boolean error) {
        this.code = code;
        this.hotelBooking = hotelBooking;
        this.error = error;
    }

    public String getCode() {
        return code;
    }

    public HotelBooking getHotelBooking() {
        return hotelBooking;
    }

    public boolean isError() {
        return error;
    }

    @Override
    public String toString() {
        return "BookHotelResponse{" +
                "code='" + code + '\'' +
                ", hotelBooking=" + hotelBooking +
                ", error='" + error + '\'' +
                '}';
    }
}
