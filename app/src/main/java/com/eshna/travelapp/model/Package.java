package com.eshna.travelapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Package {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("provider")
    private String provider;
    @SerializedName("description")
    private String description;
    @SerializedName("price")
    private String price;
    @SerializedName("overall_rating")
    private int rating;
    @SerializedName("reviews")
    private PackageReview[] packageReviews;

    public Package(int id, String name, String provider, String description, String price, int rating, PackageReview[] packageReviews) {
        this.id = id;
        this.name = name;
        this.provider = provider;
        this.description = description;
        this.price = price;
        this.rating = rating;
        this.packageReviews = packageReviews;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setPackageReviews(PackageReview[] packageReviews) {
        this.packageReviews = packageReviews;
    }

    @Override
    public String toString() {
        return "Package{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", provider='" + provider + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", rating=" + rating +
                ", packageReviews=" + Arrays.toString(packageReviews) +
                '}';
    }
}
