package com.eshna.travelapp.model;

public class User {
    private int id;
    private String name;
    private String email;
    private String apiToken;
    private String country;
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
