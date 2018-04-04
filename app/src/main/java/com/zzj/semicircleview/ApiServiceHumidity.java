package com.zzj.semicircleview;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by JY on 2018-02-13.
 */

public interface ApiServiceHumidity {
    @GET("channels/426041/feeds.json")
    Call<ResponseData> getData(@Query("results") int result);
}
