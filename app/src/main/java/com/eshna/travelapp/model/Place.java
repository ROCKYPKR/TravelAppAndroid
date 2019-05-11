package com.eshna.travelapp.model;

import com.eshna.travelapp.api.ApiClient;
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
    @SerializedName("latitude")
    private double latitude;
    @SerializedName("longitude")
    private double longitude;

    public Place(int id, String name, String description, String photo, String overallRating, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.photo = photo;
        this.overallRating = overallRating;
        this.latitude = latitude;
        this.longitude = longitude;
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
        return ApiClient.BASE_URL+"/"+photo;
    }

    public String getOverallRating() {
        return overallRating;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", photo='" + photo + '\'' +
                ", overallRating='" + overallRating + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
