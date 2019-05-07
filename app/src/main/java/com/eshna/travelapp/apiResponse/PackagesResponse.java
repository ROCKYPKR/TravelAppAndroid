package com.eshna.travelapp.apiResponse;
import com.eshna.travelapp.model.Package;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class PackagesResponse {
    @SerializedName("data")
    private Package[] packages;
    @SerializedName("error")
    private boolean error;
    @SerializedName("code")
    private String code;

    public PackagesResponse(Package[] packages, boolean error, String code) {
        this.packages = packages;
        this.error = error;
        this.code = code;
    }

    public Package[] getPackages() {
        return packages;
    }

    public boolean isError() {
        return error;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "PackagesResponse{" +
                "packages=" + Arrays.toString(packages) +
                ", error=" + error +
                ", code='" + code + '\'' +
                '}';
    }
}
