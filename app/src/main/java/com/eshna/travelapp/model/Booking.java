package com.eshna.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class Booking {
    @SerializedName("user_id")
    private String userId;
    @SerializedName("hotel_id")
    private String hotelId;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("id")
    private String bookingId;
    @SerializedName("booked_for")
    private String bookedDate; //date
    @SerializedName("user")
    private User user;
    @SerializedName("status")
    private String status;

    public Booking(String userId, String hotelId, String created_at, String bookingId, String bookedDate, User user, String status) {
        this.userId = userId;
        this.hotelId = hotelId;
        this.created_at = created_at;
        this.bookingId = bookingId;
        this.bookedDate = bookedDate;
        this.user = user;
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public String getHotelId() {
        return hotelId;
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

    public User getUser() {
        return user;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "userId='" + userId + '\'' +
                ", hotelId='" + hotelId + '\'' +
                ", created_at='" + created_at + '\'' +
                ", bookingId='" + bookingId + '\'' +
                ", bookedDate='" + bookedDate + '\'' +
                ", user=" + user +
                ", status='" + status + '\'' +
                '}';
    }
}
