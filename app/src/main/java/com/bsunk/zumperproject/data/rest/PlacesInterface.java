package com.bsunk.zumperproject.data.rest;

import com.bsunk.zumperproject.data.model.Restaurants;
import com.bsunk.zumperproject.data.model.place.Place;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Bharat on 1/27/2017.
 */

public interface PlacesInterface {

    @GET("nearbysearch/json?type=restaurant" )
    Call<Restaurants> listRestaurants(@Query("key") String apikey, @Query("location") String latlong, @Query("radius") String radius);

    @GET("nearbysearch/json?type=restaurant&rankby=distance" )
    Call<Restaurants> listRestaurantsByDistance(@Query("key") String apikey, @Query("location") String latlong);

    @GET("details/json" )
    Call<Place> getDetails(@Query("key") String apikey, @Query("placeid") String placeid);

}
