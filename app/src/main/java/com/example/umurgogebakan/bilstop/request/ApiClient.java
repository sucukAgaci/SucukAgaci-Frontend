package com.example.umurgogebakan.bilstop.request;

import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.concurrent.TimeUnit;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fkocak on 5.01.2018.
 */

public class ApiClient {

    public static final String BASE_URL = "http://10.10.13.47:8080/SucukAgaciBackend/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit==null) {

            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


            CookieHandler cookieHandler = new CookieManager();
            OkHttpClient okHttpClient = new OkHttpClient.Builder().addNetworkInterceptor(httpLoggingInterceptor)
                    .cookieJar(new JavaNetCookieJar(cookieHandler))
                    //.addInterceptor(new AddCookiesInterceptor(context))
                    //.addInterceptor(new ReceivedCookiesInterceptor(context))
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .writeTimeout(100,TimeUnit.SECONDS)
                    .readTimeout(100,TimeUnit.SECONDS)
                    .addNetworkInterceptor(new StethoInterceptor())
                    .addInterceptor(httpLoggingInterceptor)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    //.addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}