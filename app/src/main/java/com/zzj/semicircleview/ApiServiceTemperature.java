package com.zzj.semicircleview;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by JY on 2018-02-08.
 */


//GET https://api.thingspeak.com/channels/402833/feeds.json?results=2
public interface ApiServiceTemperature {
    @GET("channels/402833/feeds.json")
    Call<ResponseData> getData(@Query("results") int result);

//    public static final Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl("https://api.thingspeak.com/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build();
}