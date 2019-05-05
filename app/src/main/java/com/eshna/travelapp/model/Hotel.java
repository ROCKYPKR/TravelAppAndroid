package com.eshna.travelapp.model;

public class Hotel {
    private int id;
    private String name;
    private String description;
    private double latitude;
    private double longitude;
    private int reviewCount;
    private int overallRating;
    private String contact;

    public Hotel(int id, String name, String description, double latitude, double longitude, int reviewCount, int overallRating, String contact) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.reviewCount = reviewCount;
        this.overallRating = overallRating;
        this.contact = contact;
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

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public int getOverallRating() {
        return overallRating;
    }

    public String getContact() {
        return contact;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", reviewCount=" + reviewCount +
                ", overallRating=" + overallRating +
                ", contact='" + contact + '\'' +
                '}';
    }
}
