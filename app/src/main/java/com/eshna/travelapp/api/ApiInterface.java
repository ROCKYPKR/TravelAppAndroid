package com.eshna.travelapp.api;

import com.eshna.travelapp.apiResponse.HotelsResponse;
import com.eshna.travelapp.apiResponse.PackagesResponse;
import com.eshna.travelapp.apiResponse.ReviewResponse;
import com.eshna.travelapp.apiResponse.UserResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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
    Update User /Profile
     */
    @Headers({"Content-type: application/json", "Accept: application/json"})
    @PUT("api/user")
    Call<UserResponse> getUpdatedUser(
            @Header("Authorization") String apiToken,
            @Body Map<String, String> updatedUserDetails
    );

    /*
    Update Password
     */
    @Headers({"Content-type: application/json", "Accept: application/json"})
    @PUT("api/user/updatepassword")
    Call<UserResponse> updatePassword(
            @Header("Authorization") String apiToken,
            @Body Map<String, String> newPassword
    );


    //get list of hotels
    @Headers({"Content-type: application/json", "Accept: application/json"})
    @GET("api/hotel")
    Call<HotelsResponse> getHotels(
            @Header("Authorization") String apiToken
    );

    //get list of packages
    @Headers({"Content-type: application/json", "Accept: application/json"})
    @GET("api/package")
    Call<PackagesResponse> getPackages(
            @Header("Authorization") String apiToken
    );

    //get reviews for the current hotel
    @Headers({"Content-type: application/json", "Accept: application/json"})
    @GET("api/hotel/{hotel_id}/review")
    Call<ReviewResponse> getReviewsForAHotel(
            @Path("hotel_id") int hotelId,
            @Header("Authorization") String apiToken
    );

    //get reviews for the current package
    @Headers({"Content-type: application/json", "Accept: application/json"})
    @GET("api/package/{package_id}/review")
    Call<ReviewResponse> getReviewsForAPackage(
            @Path("package_id") int packageId,
            @Header("Authorization") String apiToken
    );

}
