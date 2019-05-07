package com.eshna.travelapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

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
    private int overallRating;
    @SerializedName("contact_number")
    private String contact;
    @SerializedName("price")
    private String price;

    public Hotel(int id, String name, String photo, String description, int overallRating, String contact, String price) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.description = description;
        this.overallRating = overallRating;
        this.contact = contact;
        this.price = price;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }

    public String getDescription() {
        return description;
    }

    public int getOverallRating() {
        return overallRating;
    }

    public String getContact() {
        return contact;
    }

    public String getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", photo='" + photo + '\'' +
                ", description='" + description + '\'' +
                ", overallRating=" + overallRating +
                ", contact='" + contact + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
