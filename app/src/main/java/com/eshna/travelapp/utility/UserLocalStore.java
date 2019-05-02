package com.eshna.travelapp.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.eshna.travelapp.model.User;


/**
 * Created by ravi on 1/28/18.
 */

public class UserLocalStore {
    public static final String SP_NAME = "userDetails";

    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeUserData(User user) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putInt("id", user.getId());
        userLocalDatabaseEditor.putString("name", user.getName());
        userLocalDatabaseEditor.putString("email", user.getEmail());
        userLocalDatabaseEditor.putString("api_token", user.getApiToken());
        userLocalDatabaseEditor.putString("country", user.getCountry());
        userLocalDatabaseEditor.putString("gender", user.getGender());
        userLocalDatabaseEditor.apply();
    }

    public void setUserLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putBoolean("loggedIn", loggedIn);
        userLocalDatabaseEditor.apply();
    }

    public void clearUserData() {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.clear();
        userLocalDatabaseEditor.apply();
    }

    public User getLoggedInUser() {
        if (!userLocalDatabase.getBoolean("loggedIn", false)) {
            return null;
        }

        int id = userLocalDatabase.getInt("id", -1);
        String name = userLocalDatabase.getString("name", "");
        String email = userLocalDatabase.getString("email", "");
        String apiToken = userLocalDatabase.getString("api_token", "");
        String country = userLocalDatabase.getString("country", "");
        String gender = userLocalDatabase.getString("gender", "");

        return new User(id, name, email, apiToken, country, gender);
    }
}
