package com.eshna.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class PackageBooking {
    @SerializedName("user_id")
    private String userId;
    @SerializedName("tour_package_id")
    private String packageId;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("id")
    private String bookingId;
    @SerializedName("booked_for")
    private String bookedDate;
    @SerializedName("package")
    private Package aPackage;
    @SerializedName("user")
    private User user;
    @SerializedName("status")
    private String status;

    public PackageBooking(String userId, String packageId, String created_at, String bookingId, String bookedDate, Package aPackage, User user, String status) {
        this.userId = userId;
        this.packageId = packageId;
        this.created_at = created_at;
        this.bookingId = bookingId;
        this.bookedDate = bookedDate;
        this.aPackage = aPackage;
        this.user = user;
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public String getPackageId() {
        return packageId;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getBookedDate() {
        return bookedDate;
    }

    public Package getaPackage() {
        return aPackage;
    }

    public User getUser() {
        return user;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "PackageBooking{" +
                "userId='" + userId + '\'' +
                ", packageId='" + packageId + '\'' +
                ", created_at='" + created_at + '\'' +
                ", bookingId='" + bookingId + '\'' +
                ", bookedDate='" + bookedDate + '\'' +
                ", aPackage=" + aPackage +
                ", user=" + user +
                ", status='" + status + '\'' +
                '}';
    }
}
