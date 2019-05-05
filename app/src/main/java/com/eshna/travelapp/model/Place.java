package com.eshna.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class Place {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("photo")
    private String photo;
    @SerializedName("overall_rating")
    private String overallRating;

    public Place(int id, String name, String description, String photo, String overallRating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.photo = photo;
        this.overallRating = overallRating;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPhoto() {
        return photo;
    }

    public String getOverallRating() {
        return overallRating;
    }
}
