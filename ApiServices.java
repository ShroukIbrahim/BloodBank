package com.example.dg.bloodbank.data.api;

import com.example.dg.bloodbank.data.model.login.Login;
import com.example.dg.bloodbank.data.model.notificationcount.Notificationcount;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServices {

    @POST("login")
    @FormUrlEncoded
    Call<Login> onLogin( @Field("phone") String email,
                         @Field("password") String pass );


    @GET("notifications-count")
    Call<Notificationcount> NotificationCount1( @Query("api_token") String api_token );


}
