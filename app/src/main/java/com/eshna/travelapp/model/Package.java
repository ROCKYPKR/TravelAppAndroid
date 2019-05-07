package com.eshna.travelapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Arrays;

public class Package implements Serializable {
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
    @SerializedName("photo")
    private String photo;
    @SerializedName("overall_rating")
    private int rating;
    @SerializedName("reviews")
    private PackageReview[] packageReviews;

    public Package(int id, String name, String provider, String description, String price, String photo, int rating, PackageReview[] packageReviews) {
        this.id = id;
        this.name = name;
        this.provider = provider;
        this.description = description;
        this.price = price;
        this.photo = photo;
        this.rating = rating;
        this.packageReviews = packageReviews;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProvider() {
        return provider;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getPhoto() {
        return photo;
    }

    public int getRating() {
        return rating;
    }

    public PackageReview[] getPackageReviews() {
        return packageReviews;
    }

    @Override
    public String toString() {
        return "Package{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", provider='" + provider + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", photo='" + photo + '\'' +
                ", rating=" + rating +
                ", packageReviews=" + Arrays.toString(packageReviews) +
                '}';
    }
}
