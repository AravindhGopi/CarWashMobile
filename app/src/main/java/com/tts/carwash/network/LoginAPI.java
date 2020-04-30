package com.tts.carwash.network;

import com.google.gson.JsonObject;
import com.tts.carwash.model.LoginModel;
import com.tts.carwash.model.SMSModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;
public interface LoginAPI {

    @GET("/send/?apikey=qMLIiLNqm50-non9D9QlKpOpafTjTDcNEplRCCPAXQ&sender=TXTLCL")
    Call<List<SMSModel>> getOTPResponse(@Query("numbers") String mobile, @Query("message") String pin);
//    Call<List<LoginModel>> getLoginResponse(@Query("mobile") String mobileno, @Query("password") String password);
    @POST("/carwash/public/api/register")
    @FormUrlEncoded
    Call<Object> getSignUpResponse(@Field("mobile") String mobile, @Field("otp") String pin);
    @POST("/carwash/public/api/verify_otp")
    @FormUrlEncoded
    Call<LoginModel> verifyOTP(@Field("mobile") String mobile,@Field("otp") String pin);

    @POST("/carwash/public/api/login")
    @FormUrlEncoded
    Call<LoginModel> getLoginResponse(@Field("mobile") String mobile, @Field("password") String password);

    @POST("/carwash/public/api/forgot_password")
    @FormUrlEncoded
    Call<Object> forgotPassword(@Field("mobile") String mobile, @Field("otp") String pin);

//    @Headers("Content-Type: application/json")
    @POST("/carwash/public/api/save_profile")
    @FormUrlEncoded
    Call<Object> saveProfile(@Header("Authorization") String token, @Field("first_name") String firstName, @Field("email") String email, @Field("password") String password);
}
