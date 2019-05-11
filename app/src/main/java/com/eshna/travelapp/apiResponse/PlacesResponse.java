package com.eshna.travelapp.apiResponse;

import com.eshna.travelapp.model.Place;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class PlacesResponse {
    @SerializedName("error")
    private boolean error;
    @SerializedName("code")
    private int code;
    @SerializedName("data")
    private Place[] places;

    public PlacesResponse(boolean error, int code, Place[] places) {
        this.error = error;
        this.code = code;
        this.places = places;
    }

    public boolean isError() {
        return error;
    }

    public int getCode() {
        return code;
    }

    public Place[] getPlaces() {
        return places;
    }

    @Override
    public String toString() {
        return "PlacesResponse{" +
                "error=" + error +
                ", code=" + code +
                ", places=" + Arrays.toString(places) +
                '}';
    }
}
