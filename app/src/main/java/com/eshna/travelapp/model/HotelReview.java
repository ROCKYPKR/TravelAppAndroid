package com.eshna.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class HotelReview extends Review{

    @SerializedName("hotel_id")
    private int id;

    public HotelReview(String user_id, String review, String rating, String created_at, int id, User user) {
        super(user_id, review, rating, created_at, id, user);
    }

    public int getId() {
        return id;
    }
}
