package com.eshna.travelapp.apiResponse;

import com.eshna.travelapp.model.HotelBooking;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class HotelBookingsResponse {
        @SerializedName("code")
        private String code;
        @SerializedName("data")
        private HotelBooking[] hotelBookings;
        @SerializedName("error")
        private boolean error;


    public HotelBookingsResponse(String code, HotelBooking[] hotelBookings, boolean error) {
        this.code = code;
        this.hotelBookings = hotelBookings;
        this.error = error;
    }

    public String getCode() {
        return code;
    }

    public HotelBooking[] getHotelBookings() {
        return hotelBookings;
    }

    public boolean isError() {
        return error;
    }

    @Override
    public String toString() {
        return "HotelBookingsResponse{" +
                "code='" + code + '\'' +
                ", hotelBookings=" + Arrays.toString(hotelBookings) +
                ", error=" + error +
                '}';
    }
}
