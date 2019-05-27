package com.eshna.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class HotelBooking {
    @SerializedName("user_id")
    private int userId;
    @SerializedName("hotel_id")
    private int hotelId;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("id")
    private int bookingId;
    @SerializedName("booked_for")
    private String bookedDate; //date
    @SerializedName("user")
    private User user;
    @SerializedName("hotel")
    private Hotel hotel;
    @SerializedName("status")
    private String status;

    public HotelBooking(int userId, int hotelId, String created_at, int bookingId, String bookedDate, User user, Hotel hotel, String status) {
        this.userId = userId;
        this.hotelId = hotelId;
        this.created_at = created_at;
        this.bookingId = bookingId;
        this.bookedDate = bookedDate;
        this.user = user;
        this.hotel = hotel;
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public String getCreated_at() {
        return created_at;
    }

    public int getBookingId() {
        return bookingId;
    }

    public String getBookedDate() {
        return bookedDate;
    }

    public User getUser() {
        return user;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "HotelBooking{" +
                "userId='" + userId + '\'' +
                ", hotelId='" + hotelId + '\'' +
                ", created_at='" + created_at + '\'' +
                ", bookingId='" + bookingId + '\'' +
                ", bookedDate='" + bookedDate + '\'' +
                ", user=" + user +
                ", hotel=" + hotel +
                ", status='" + status + '\'' +
                '}';
    }
}
