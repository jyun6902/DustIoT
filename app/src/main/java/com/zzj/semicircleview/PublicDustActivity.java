package com.zzj.semicircleview;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.res.TypedArray;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zzj.library.SemiCircleView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

public class PublicDustActivity extends AppCompatActivity implements LocationListener{

    Retrofit retrofit;
    ApiServicePublicDust apiService_public;
    Gson gson;
    String field1="";
    private SemiCircleView semiCircleView;
    private TypedArray typedArray;
    SwipeRefreshLayout mSwipeRefreshLayout;
    int intfield1=0;

    private LocationManager locationManager;//////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Dust");
        setContentView(R.layout.activity_public_dust);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0, 0, this);
        ////////////////////////////////////////////////////////////////////////////////
        semiCircleView = (SemiCircleView) findViewById(R.id.test);
        semiCircleView.setmProgressValue(0);
        semiCircleView.setAnimation(true);
        retrofit = new Retrofit.Builder()
                .baseUrl("http://openapi.airkorea.or.kr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService_public = retrofit.create(ApiServicePublicDust.class);

        final String key =  "1Eyqv5D0Qcsb%2Bge0PR7Jnrm%2Bphhjj0gZpfS7vJHhvF%2B58hjPG9ABfgIgrLbxJji2dCE341pRFMK02R5q31zzEA%3D%3D";
        Call<DustApiData> apiServiceData = apiService_public.getList("종로구", "month", "1","1", key, "1.3", "json");
        apiServiceData.enqueue(new Callback<DustApiData>() {
            @Override
            public void onResponse(Call<DustApiData> call, retrofit2.Response<DustApiData> response) {
                gson = new Gson();
                DustApiData repo = response.body();
                Log.e("LOG created_at", gson.toJson(response.body().list));

                String json = gson.toJson(response.body().list);
                field1 = "";//미세먼지
                Log.e("LOG json", json);

                try {
                    JSONArray jsonarray = new JSONArray(json);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        // int entry_id = jsonobject.getInt("entry_id");
                        field1 = jsonobject.getString("pm10Value");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                intfield1 = Integer.parseInt(field1);
                int graphColor = 0;
                if(intfield1 < 30) {//좋음
                    graphColor = getResources().getColor(R.color.level1);
                }
                else if( intfield1 < 80 ) { //보통
                    graphColor = getResources().getColor(R.color.level2);
                }
                else if( intfield1 < 150 ) {//나쁨
                    graphColor = getResources().getColor(R.color.level3);
                }
                else {//매우나쁨
                    graphColor = getResources().getColor(R.color.level4);
                }

                semiCircleView.setmProgressValue((int)(100*(float)intfield1/150));
                semiCircleView.setmProgressColor(graphColor);
                semiCircleView.setAnimation(true);
                semiCircleView.setmCenterText(field1);
            }

            @Override
            public void onFailure(Call<DustApiData> call, Throwable t) {
             Log.e("fail","에러내용");
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        double lat =  location.getLatitude();
        double lng = location.getLongitude();
        String msg = "New Latitude: " + lat
                + "New Longitude: " + lng;

       // Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(getBaseContext(), "Gps is turned on!! ",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
        Toast.makeText(getBaseContext(), "Gps is turned off!! ",
                Toast.LENGTH_SHORT).show();

    }
}