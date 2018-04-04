package com.zzj.semicircleview;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
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

public class DustActivity extends AppCompatActivity implements LocationListener {

    Retrofit retrofit,retrofit_outside;
    ApiServiceDust apiService;
    Gson gson,gson_public;
    String field1="";
    private SemiCircleView semiCircleView;
    private TypedArray typedArray;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ApiServicePublicDust apiService_public;
    private LocationManager locationManager;//////////////////////////
    int intfield1=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Dust");
        setContentView(R.layout.activity_dust);
        semiCircleView = (SemiCircleView) findViewById(R.id.test2);
        semiCircleView.setmProgressValue(0);
        //semiCircleView.setmCenterText("22");
        semiCircleView.setAnimation(true);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.thingspeak.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiServiceDust.class);
        Call<ResponseData> apiServiceData = apiService.getData(1);
        apiServiceData.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, retrofit2.Response<ResponseData> response) {
                gson = new Gson();
                ResponseData repo = response.body();
                Log.e("LOG created_at", gson.toJson(response.body().feeds));

                //TextView textViewIndex = (TextView) findViewById(R.id.textViewIndex);
                String json = gson.toJson(response.body().feeds);
                field1 = "";//온도

                try {
                    JSONArray jsonarray = new JSONArray(json);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        int entry_id = jsonobject.getInt("entry_id");
                        field1 = jsonobject.getString("field1");
                        //   Log.e("LOG field1", field1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //  textViewIndex.setText(field1);//온도
                intfield1 = Integer.parseInt(field1);
                int graphColor = 0;
                if(intfield1 < 30) {
                    graphColor = getResources().getColor(R.color.level1);
                }
                else if( intfield1 < 80 ) {
                    graphColor = getResources().getColor(R.color.level2);
                }
                else if( intfield1 < 150 ) {
                    graphColor = getResources().getColor(R.color.level3);
                }
                else {
                    graphColor = getResources().getColor(R.color.level4);
                }


                semiCircleView.setmProgressValue((int)(100*(float)intfield1/150));
                semiCircleView.setmProgressColor(graphColor);
                semiCircleView.setAnimation(true);
                semiCircleView.setmCenterText(field1);

            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
        //////////////////////////////////////////////////////////////////////
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0, 0, this);
        ////////////////////////////////////////////////////////////////////////////////
        retrofit_outside = new Retrofit.Builder()
                .baseUrl("http://openapi.airkorea.or.kr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService_public = retrofit_outside.create(ApiServicePublicDust.class);

        //?stationName=종로구&dataTerm=month&pageNo=1&numOfRows=10&ServiceKey=1Eyqv5D0Qcsb%2Bge0PR7Jnrm%2Bphhjj0gZpfS7vJHhvF%2B58hjPG9ABfgIgrLbxJji2dCE341pRFMK02R5q31zzEA%3D%3D&ver=1.3&_returnType=json
        //"1Eyqv5D0Qcsb%252Bge0PR7Jnrm%252Bphhjj0gZpfS7vJHhvF%252B58hjPG9ABfgIgrLbxJji2dCE341pRFMK02R5q31zzEA%253D%253D"
        //"1Eyqv5D0Qcsb%2Bge0PR7Jnrm%2Bphhjj0gZpfS7vJHhvF%2B58hjPG9ABfgIgrLbxJji2dCE341pRFMK02R5q31zzEA%3D%3D"

        final String key =  "1Eyqv5D0Qcsb%2Bge0PR7Jnrm%2Bphhjj0gZpfS7vJHhvF%2B58hjPG9ABfgIgrLbxJji2dCE341pRFMK02R5q31zzEA%3D%3D";
        // final String key =  "PP6%2F7Pf7VrZ1p7I5507Bgf%2BqQYRnP4lPb9Ws3upcDzRshDvpRcImp54aluAD00mKKj%2BYgb14nIFFuoyS4eR1fw%3D%3D";
        Call<DustApiData> apiServiceData2 = apiService_public.getList("종로구", "month", "1","1", key, "1.3", "json");
        apiServiceData2.enqueue(new Callback<DustApiData>() {
            @Override
            public void onResponse(Call<DustApiData> call, retrofit2.Response<DustApiData> response) {
                gson_public = new Gson();
                DustApiData repo = response.body();
                Log.e("LOG created_at", gson_public.toJson(response.body().list));

                TextView tv1 = (TextView) findViewById(R.id.tv2);
                String json = gson_public.toJson(response.body().list);
                field1 = "";//온도
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
                //  textViewIndex.setText(field1);//온도
                intfield1 = Integer.parseInt(field1);
                tv1.setText("현재 미세먼지 농도 : " + field1 + " ㎍/m³");

            }

            @Override
            public void onFailure(Call<DustApiData> call, Throwable t) {
                Log.e("fail","에러내용");
            }
        });

        //////////////////////////////////////////////////////////////////////
        String url = "<iframe width=\"440\" height=\"260\" style=\"border: 1px solid #ffffff;\" src=\"https://thingspeak.com/channels/422154/charts/1?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&type=line\"></iframe>";
//400 260
        final WebView webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setInitialScale(280);


        //webView.loadUrl(url);
        webView.loadData(url, "text/html", null);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mSwipeRefreshLayout.setRefreshing(true);
                //semiCircleView.setmCenterText(field1);

                apiService = retrofit.create(ApiServiceDust.class);
                Call<ResponseData> apiServiceData = apiService.getData(1);
                apiServiceData.enqueue(new Callback<ResponseData>() {
                    @Override
                    public void onResponse(Call<ResponseData> call, retrofit2.Response<ResponseData> response) {
                        gson = new Gson();
                        ResponseData repo = response.body();
                        //Log.e("LOG created_at", gson.toJson(response.body().feeds));

                        //TextView textViewIndex = (TextView) findViewById(R.id.textViewIndex);
                        String json = gson.toJson(response.body().feeds);
                        field1 = "";//온도

                        try {
                            JSONArray jsonarray = new JSONArray(json);
                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject jsonobject = jsonarray.getJSONObject(i);
                                int entry_id = jsonobject.getInt("entry_id");
                                field1 = jsonobject.getString("field1");
                                //   Log.e("LOG field1", field1);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //  textViewIndex.setText(field1);//온도
                        intfield1 = Integer.parseInt(field1);
                        int graphColor = 0;
                        if(intfield1 < 30) {
                            graphColor = getResources().getColor(R.color.level1);
                        }
                        else if( intfield1 < 80 ) {
                            graphColor = getResources().getColor(R.color.level2);
                        }
                        else if( intfield1 < 150 ) {
                            graphColor = getResources().getColor(R.color.level3);
                        }
                        else {
                            graphColor = getResources().getColor(R.color.level4);
                        }

                        semiCircleView.setmProgressValue((int)(100*(float)intfield1/150));
                        semiCircleView.setmProgressColor(graphColor);
                        semiCircleView.setAnimation(true);
                        semiCircleView.setmCenterText(field1);
                    }

                    @Override
                    public void onFailure(Call<ResponseData> call, Throwable t) {

                    }
                });


                //새로고침시 실행 할 작업 실행...
                webView.reload();

                mSwipeRefreshLayout.setRefreshing(false);

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