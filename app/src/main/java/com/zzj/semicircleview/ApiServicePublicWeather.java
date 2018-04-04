package com.zzj.semicircleview;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by JY on 2018-02-27.
 */

public interface ApiServicePublicWeather {
    @GET("/data/2.5/weather")
      //?lat=37&lon=127&id=524901&APPID=4444b13febbcab8d10591ee113add8e0
    Call<WeatherApiData> getList(@Query("lat")double lat, @Query("lon")double lon,
                              @Query("id")String id, @Query(value = "APPID", encoded = true)final String APPID);

}
