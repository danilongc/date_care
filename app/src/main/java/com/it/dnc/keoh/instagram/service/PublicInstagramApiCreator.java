package com.it.dnc.keoh.instagram.service;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class PublicInstagramApiCreator {

    private static Retrofit retrofit;
    private static OkHttpClient client;
    public final static String BASE_INSTAGRAM_URL = "https://www.instagram.com/";
    public final static String INSTA_TOKEN = "1989964257.227f806.e40115244d8e493087e656bb7d7d0c15";

    static{

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_INSTAGRAM_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client)
                .build();
    }

    public static Retrofit getRetrofitInstance(){
        return retrofit;
    }

}