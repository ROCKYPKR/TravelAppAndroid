package com.eshna.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class PlaceReview extends Review {

    @SerializedName("place_id")
    private int placeId;

    public PlaceReview(String user_id, String review, String rating, String created_at, int id, User user) {
        super(user_id, review, rating, created_at, id, user);
    }

    public int getId() {
        return placeId;
    }
}
