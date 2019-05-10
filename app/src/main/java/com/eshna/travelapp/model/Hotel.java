package com.eshna.travelapp.model;

import com.eshna.travelapp.api.ApiClient;
import com.google.android.gms.common.api.Api;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Arrays;

public class Hotel implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("photo")
    private String photo;
    @SerializedName("description")
    private String description;
//    @SerializedName("review_count")
//    private int reviewCount;
    @SerializedName("overall_rating")
    private String overallRating;
    @SerializedName("contact_number")
    private String contact;
    @SerializedName("price")
    private String price;
    @SerializedName("latitude")
    private double latitude;
    @SerializedName("longitude")
    private double longitude;

    public Hotel(int id, String name, String photo, String description, String overallRating, String contact, String price, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.description = description;
        this.overallRating = overallRating;
        this.contact = contact;
        this.price = price;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return ApiClient.BASE_URL+"/"+photo;
    }

    public String getDescription() {
        return description;
    }

    public String getOverallRating() {
        return overallRating;
    }

    public String getContact() {
        return contact;
    }

    public String getPrice() {
        return price;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", photo='" + photo + '\'' +
                ", description='" + description + '\'' +
                ", overallRating='" + overallRating + '\'' +
                ", contact='" + contact + '\'' +
                ", price='" + price + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
