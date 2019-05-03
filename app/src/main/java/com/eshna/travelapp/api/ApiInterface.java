package com.eshna.travelapp.api;

import com.eshna.travelapp.apiResponse.RegistrationResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {
//    @FormUrlEncoded
//    @POST("api/v1/login")
//    Call<UserLoginResponse> getUserLogin(
//            @Field("email") String email,
//            @Field("password") String password,
//            @Field("device_token") String deviceToken
//    );
//
    /*
    Registration
     */
    @Headers({"Content-type: application/json", "Accept: application/json"})
    @POST("api/register")
    Call<RegistrationResponse> getUserAfterRegistration(
            @Body Map<String, String> userDetails
    );
}
