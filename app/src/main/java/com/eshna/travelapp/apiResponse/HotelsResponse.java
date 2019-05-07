package com.eshna.travelapp.apiResponse;

import com.eshna.travelapp.model.Hotel;
import com.google.gson.annotations.SerializedName;

public class HotelsResponse {
    @SerializedName("data")
    private Hotel[] hotels;
    @SerializedName("error")
    private boolean error;
    @SerializedName("code")
    private String code;

    public Hotel[] getHotels() {
        return hotels;
    }

    public boolean getError() {
        return error;
    }

    public String getCode() {
        return code;
    }
}
