package com.eshna.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("api_token")
    private String apiToken;
    @SerializedName("country")
    private String country;
    @SerializedName("gender")
    private String gender;

    public User(int id, String name, String email, String apiToken, String country, String gender) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.apiToken = apiToken;
        this.country = country;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getApiToken() {
        return apiToken;
    }

    public String getCountry() {
        return country;
    }

    public String getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", apiToken='" + apiToken + '\'' +
                ", country='" + country + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
