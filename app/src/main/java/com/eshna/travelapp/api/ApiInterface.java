package com.eshna.travelapp.api;

import com.eshna.travelapp.apiResponse.BookHotelResponse;
import com.eshna.travelapp.apiResponse.BookPackageResponse;
import com.eshna.travelapp.apiResponse.HotelBookingsResponse;
import com.eshna.travelapp.apiResponse.HotelReviewResponse;
import com.eshna.travelapp.apiResponse.HotelsResponse;
import com.eshna.travelapp.apiResponse.PackageBookingsResponse;
import com.eshna.travelapp.apiResponse.PackageReviewResponse;
import com.eshna.travelapp.apiResponse.PackagesResponse;
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
    Call<HotelReviewResponse> getReviewsForAHotel(
            @Path("hotel_id") int hotelId,
            @Header("Authorization") String apiToken
    );

    //get reviews for the current package
    @Headers({"Content-type: application/json", "Accept: application/json"})
    @GET("api/package/{package_id}/review")
    Call<PackageReviewResponse> getReviewsForAPackage(
            @Path("package_id") int packageId,
            @Header("Authorization") String apiToken
    );

    //book a hotel
    @Headers({"Content-type: application/json", "Accept: application/json"})
    @POST("api/hotelbooking")
    Call<BookHotelResponse> bookHotel(
            @Header("Authorization") String apiToken,
            @Body Map<String, String> bookingDetails
    );

    //book a package
    @Headers({"Content-type: application/json", "Accept: application/json"})
    @POST("api/packagebooking")
    Call<BookPackageResponse> bookPackage(
            @Header("Authorization") String apiToken,
            @Body Map<String, String> bookingDetails
    );

    //get hotel bookings for a user
    @Headers({"Content-type: application/json", "Accept: application/json"})
    @GET("api/hotelbooking")
    Call<HotelBookingsResponse> getHotelBookings(
            @Header("Authorization") String apiToken
    );

    //get package bookings for a user
    @Headers({"Content-type: application/json", "Accept: application/json"})
    @GET("api/packagebooking")
    Call<PackageBookingsResponse> getPackageBookings(
            @Header("Authorization") String apiToken
    );

}
