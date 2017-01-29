package com.bsunk.zumperproject.data.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Bharat on 1/27/2017.
 */

public class ApiClient {

    public static final String BASE_URL = "https://maps.googleapis.com/maps/api/place/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if(retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}