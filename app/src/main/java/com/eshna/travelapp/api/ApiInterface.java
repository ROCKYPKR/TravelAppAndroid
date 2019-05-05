package com.eshna.travelapp.api;

import com.eshna.travelapp.apiResponse.UserResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiInterface {
    /*
    Registration
     */
    @Headers({"Content-type: application/json", "Accept: application/json"})
    @POST("api/register")
    Call<UserResponse> getUserAfterRegistration(
            @Body Map<String, String> userDetails
    );

    /*
    Login
     */
    @Headers({"Content-type: application/json", "Accept: application/json"})
    @POST("api/login")
    Call<UserResponse> getUserLogin(
            @Body Map<String, String> userCredentials
    );

    /*
    Update User
     */
    @Headers({"Content-type: application/json", "Accept: application/json"})
    @PUT("api/user")
    Call<UserResponse> getUpdatedUser(
            @Header("Authorization") String apiToken,
            @Body Map<String, String> updatedUserDetails
    );


}
