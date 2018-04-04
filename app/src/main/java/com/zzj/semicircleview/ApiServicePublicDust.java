package com.zzj.semicircleview;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by JY on 2018-02-26.
 */

public interface ApiServicePublicDust {
    @GET("openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty")
    //?stationName=종로구&dataTerm=month&pageNo=1&numOfRows=10&ServiceKey=1Eyqv5D0Qcsb%2Bge0PR7Jnrm%2Bphhjj0gZpfS7vJHhvF%2B58hjPG9ABfgIgrLbxJji2dCE341pRFMK02R5q31zzEA%3D%3D&ver=1.3&_returnType=json
    Call<DustApiData> getList(@Query("stationName")String stationName,  @Query("dataTerm")String dataTerm,
        @Query("pageNo")String pageNo, @Query("numOfRows")String numOfRows,
        @Query(value = "ServiceKey", encoded = true)final String ServiceKey, @Query("ver")String ver, @Query("_returnType")String _returnType);

}
