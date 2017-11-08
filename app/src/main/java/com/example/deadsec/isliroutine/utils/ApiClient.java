package com.example.deadsec.isliroutine.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by deadsec on 11/8/17.
 */

public class ApiClient {
    public static final String BASE_URL="http://192.168.0.102/api/";
    public static Retrofit sRetrofit =null;

    public static Retrofit getApiClient() {
        if(sRetrofit == null) {
            sRetrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return sRetrofit;
    }

}
