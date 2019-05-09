package com.eshna.travelapp.apiResponse;

import com.eshna.travelapp.model.PackageBooking;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class PackageBookingsResponse {
    @SerializedName("code")
    private String code;
    @SerializedName("data")
    private PackageBooking[] packageBookings;
    @SerializedName("error")
    private boolean error;

    public PackageBookingsResponse(String code, PackageBooking[] packageBookings, boolean error) {
        this.code = code;
        this.packageBookings = packageBookings;
        this.error = error;
    }

    public String getCode() {
        return code;
    }

    public PackageBooking[] getPackageBookings() {
        return packageBookings;
    }

    public boolean isError() {
        return error;
    }

    @Override
    public String toString() {
        return "PackageBookingsResponse{" +
                "code='" + code + '\'' +
                ", packageBookings=" + Arrays.toString(packageBookings) +
                ", error=" + error +
                '}';
    }
}
