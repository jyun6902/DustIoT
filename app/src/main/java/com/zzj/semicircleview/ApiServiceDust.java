package com.zzj.semicircleview;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by JY on 2018-02-12.
 */

public interface ApiServiceDust {
    @GET("channels/422154/feeds.json")
    Call<ResponseData> getData(@Query("results") int result);

//    public static final Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl("https://api.thingspeak.com/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build();
}