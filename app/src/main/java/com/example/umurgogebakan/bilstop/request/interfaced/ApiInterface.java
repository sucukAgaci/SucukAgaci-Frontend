package com.example.umurgogebakan.bilstop.request.interfaced;


import com.example.umurgogebakan.bilstop.model.Response4Login;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("Dagitici")
    Call<Response4Login> loginRequest(@Query("s") String s, @Query("username") String username, @Query("password") String password);

    @GET("Dagitici")
    Call<Response4Login> signUpRequest(@Query("s") String s, @Query("name") String name, @Query("surname") String surname,
                                       @Query("password") String password, @Query("email") String email, @Query("age") int age);

    @GET("Dagitici")
    Call<Response4Login> verifRequest(@Query("s") String s,  @Query("verificationCode") String verifCode);

    @GET("Dagitici")
    Call<Response4Login> driverRegistryRequest(@Query("s") String s, @Query("isDriver") boolean isDriver, @Query("model") String model, @Query("color") String color,
                                               @Query("plates") String plates, @Query("seats") int seats);

    @GET("Dagitici")
    Call<Response4Login> showCarsRequest(@Query("s") String s);



}