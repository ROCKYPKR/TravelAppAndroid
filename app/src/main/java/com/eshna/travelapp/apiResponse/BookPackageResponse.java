package com.eshna.travelapp.apiResponse;

import com.eshna.travelapp.model.PackageBooking;
import com.google.gson.annotations.SerializedName;

public class BookPackageResponse {
    @SerializedName("code")
    private String code;
    @SerializedName("data")
    private PackageBooking packageBooking;
    @SerializedName("error")
    private boolean error;

    public BookPackageResponse(String code, PackageBooking packageBooking, boolean error) {
        this.code = code;
        this.packageBooking = packageBooking;
        this.error = error;
    }

    public String getCode() {
        return code;
    }

    public PackageBooking getPackageBooking() {
        return packageBooking;
    }

    public boolean isError() {
        return error;
    }

    @Override
    public String toString() {
        return "BookPackageResponse{" +
                "code='" + code + '\'' +
                ", packageBooking=" + packageBooking +
                ", error='" + error + '\'' +
                '}';
    }
}
