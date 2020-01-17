package com.example.dileit.model.network;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
    private static Retrofit sRetrofit;
    private static final String BASE_URL = "https://api.vajehyab.com/v3/";

    public static Retrofit getRetrofit() {
        if (sRetrofit == null) {
            synchronized (RetrofitClientInstance.class) {
                if (sRetrofit == null) {
                    sRetrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return sRetrofit;
    }
}
