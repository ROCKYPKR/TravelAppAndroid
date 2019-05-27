package com.eshna.travelapp.api;

import com.eshna.travelapp.apiResponse.BookHotelResponse;
import com.eshna.travelapp.apiResponse.BookPackageResponse;
import com.eshna.travelapp.apiResponse.HotelBookingsResponse;
import com.eshna.travelapp.apiResponse.HotelReviewResponse;
import com.eshna.travelapp.apiResponse.HotelReviewsResponse;
import com.eshna.travelapp.apiResponse.HotelsResponse;
import com.eshna.travelapp.apiResponse.MinimalResponse;
import com.eshna.travelapp.apiResponse.PackageBookingsResponse;
import com.eshna.travelapp.apiResponse.PackageReviewResponse;
import com.eshna.travelapp.apiResponse.PackageReviewsResponse;
import com.eshna.travelapp.apiResponse.PackagesResponse;
import com.eshna.travelapp.apiResponse.PlacesResponse;
import com.eshna.travelapp.apiResponse.UserResponse;
import com.eshna.travelapp.model.Place;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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

    //get list of places
    @Headers({"Content-type: application/json", "Accept: application/json"})
    @GET("api/place")
    Call<PlacesResponse> getPlaces(
            @Header("Authorization") String apiToken
    );

    //get reviews for the current hotel
    @Headers({"Content-type: application/json", "Accept: application/json"})
    @GET("api/hotel/{hotel_id}/review")
    Call<HotelReviewsResponse> getReviewsForAHotel(
            @Path("hotel_id") int hotelId,
            @Header("Authorization") String apiToken
    );

    //get reviews for the current package
    @Headers({"Content-type: application/json", "Accept: application/json"})
    @GET("api/package/{package_id}/review")
    Call<PackageReviewsResponse> getReviewsForAPackage(
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

    //post a review for hotel
    @Headers({"Content-type: application/json", "Accept: application/json"})
    @POST("api/hotel/review")
    Call<HotelReviewResponse> postHotelReview(
            @Header("Authorization") String apiToken,
            @Body Map<String, String> hotelReviewData
    );

    //post a review for package
    @Headers({"Content-type: application/json", "Accept: application/json"})
    @POST("api/package/review")
    Call<PackageReviewResponse> postPackageReview(
            @Header("Authorization") String apiToken,
            @Body Map<String, String> hotelReviewData
    );

    //another method for post place review


    //update hotel review
    @Headers({"Content-type: application/json", "Accept: application/json"})
    @PUT("api/hotel/review/{review_id}")
    Call<HotelReviewResponse> updateHotelReview(
            @Path("review_id") int reviewId,
            @Header("Authorization") String apiToken,
            @Body Map<String, String> hotelReviewData
    );

    //delete hotel review
    @Headers({"Content-type: application/json", "Accept: application/json"})
    @DELETE("api/hotel/review/{review_id}")
    Call<MinimalResponse> deleteHotelReview(
            @Path("review_id") int reviewId,
            @Header("Authorization") String apiToken
    );


    //update package review
    @Headers({"Content-type: application/json", "Accept: application/json"})
    @PUT("api/package/review/{review_id}")
    Call<PackageReviewResponse> updatePackageReview(
            @Path("review_id") int reviewId,
            @Header("Authorization") String apiToken,
            @Body Map<String, String> hotelReviewData
    );

    //delete package review
    @Headers({"Content-type: application/json", "Accept: application/json"})
    @DELETE("api/package/review/{review_id}")
    Call<MinimalResponse> deletePackageReview(
            @Path("review_id") int reviewId,
            @Header("Authorization") String apiToken
    );

    //delete hotel booking (cancel hotel booking)
    @Headers({"Content-type: application/json", "Accept: application/json"})
    @DELETE("api/hotelbooking/{booking_id}")
    Call<MinimalResponse> deleteHotelBooking(
            @Path("booking_id") int bookingId,
            @Header("Authorization") String apiToken
    );

    //delete package booking (cancel package booking)
    @Headers({"Content-type: application/json", "Accept: application/json"})
    @DELETE("api/packagebooking/{booking_id}")
    Call<MinimalResponse> deletePackageBooking(
            @Path("booking_id") int bookingId,
            @Header("Authorization") String apiToken
    );

}
