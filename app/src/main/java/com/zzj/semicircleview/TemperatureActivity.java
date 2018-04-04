package com.zzj.semicircleview;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import com.google.gson.Gson;
import com.zzj.library.SemiCircleView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

public class TemperatureActivity extends AppCompatActivity {

    Retrofit retrofit,retrofit_outside;
    ApiServiceTemperature apiService;
    ApiServicePublicWeather apiService_publicweather;
    Gson gson, gson_outside;
    String field1="", nowtemp="";
    private SemiCircleView semiCircleView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    int intfield1=0;
    double dounowtemp=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Temperature");
        setContentView(R.layout.activity_temperature);

        ///////////////////////////////////////////////////////////////////////
        semiCircleView = (SemiCircleView) findViewById(R.id.test);//실내온도
       semiCircleView.setmProgressValue(0);
        semiCircleView.setAnimation(true);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.thingspeak.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiServiceTemperature.class);
        Call<ResponseData> apiServiceData = apiService.getData(1);
        apiServiceData.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, retrofit2.Response<ResponseData> response) {
                gson = new Gson();
                ResponseData repo = response.body();
                Log.e("LOG created_at", gson.toJson(response.body().feeds));

                //TextView textViewIndex = (TextView) findViewById(R.id.tv1);
                String json = gson.toJson(response.body().feeds);
                field1 = "";//온도

                try {
                    JSONArray jsonarray = new JSONArray(json);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        int entry_id = jsonobject.getInt("entry_id");
                        field1 = jsonobject.getString("field1");
                           Log.e("LOG field1", field1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                intfield1 = Integer.parseInt(field1);
                semiCircleView.setmProgressValue(intfield1);
                semiCircleView.setmCenterText(field1);
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
        /////////////////////////////////
        retrofit_outside = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService_publicweather = retrofit_outside.create(ApiServicePublicWeather.class);

        final String key =  "4444b13febbcab8d10591ee113add8e0";
        // getList(@Query("lat")double lat, @Query("lon")double lon,
        Call<WeatherApiData> weaapiServiceData = apiService_publicweather.getList(37.56, 126.97, "524901",key);
        final TextView tv= (TextView) findViewById(R.id.tv1);//실외온도
        weaapiServiceData.enqueue(new Callback<WeatherApiData>() {

            @Override
            public void onResponse(Call<WeatherApiData> call, retrofit2.Response<WeatherApiData> response) {
                gson_outside = new Gson();

                if ( response.isSuccessful()) {
                    WeatherApiData getList = response.body();

                    nowtemp = String.valueOf(getList.getMain().getTemp());// string
                    dounowtemp = Double.parseDouble(nowtemp);//
                    dounowtemp-=273.0;
                    nowtemp = Double.toString(dounowtemp);
                    nowtemp = String.format("%.1f", dounowtemp);//소수점 한자리까지만 출력
                    Log.e("LOG field1dounowtemp",nowtemp);
                    tv.setText("실외온도 : " );
                    tv.append( nowtemp);
                    tv.append(" ℃");
                   // tv.setText(nowtemp);
                }
                else {
                    tv.setText("실외온도 : ? " );
                }
            }

            @Override
            public void onFailure(Call<WeatherApiData> call, Throwable t) {
                Log.e("fail","에러내용");
            }
        });
        /////////////////////////
        String url = "<iframe width=\"440\" height=\"260\" style=\"border: 1px solid #ffffff;\" src=\"https://thingspeak.com/channels/402833/charts/1?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&type=line\"></iframe>";
//400 260
        final WebView webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setInitialScale(280);

        //webView.loadUrl(url);
        webView.loadData(url, "text/html", null);
        /////////////////////////////////////////////////////////////////////////////////////////
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mSwipeRefreshLayout.setRefreshing(true);
                //semiCircleView.setmCenterText(field1);

                apiService = retrofit.create(ApiServiceTemperature.class);
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
                        semiCircleView.setmProgressValue(intfield1);
                        semiCircleView.setmCenterText(field1);

                    }

                    @Override
                    public void onFailure(Call<ResponseData> call, Throwable t) {

                    }
                });
                ////////////////////////////
                retrofit_outside = new Retrofit.Builder()
                        .baseUrl("https://api.openweathermap.org")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                apiService_publicweather = retrofit_outside.create(ApiServicePublicWeather.class);

                final String key =  "4444b13febbcab8d10591ee113add8e0";
                // getList(@Query("lat")double lat, @Query("lon")double lon,
                Call<WeatherApiData> weaapiServiceData = apiService_publicweather.getList(37.56, 126.97, "524901",key);
                final TextView tv= (TextView) findViewById(R.id.tv1);//실외온도
                weaapiServiceData.enqueue(new Callback<WeatherApiData>() {

                    @Override
                    public void onResponse(Call<WeatherApiData> call, retrofit2.Response<WeatherApiData> response) {
                        gson_outside = new Gson();

                        if ( response.isSuccessful()) {
                            WeatherApiData getList = response.body();

                            nowtemp = String.valueOf(getList.getMain().getTemp());// string
                            dounowtemp = Double.parseDouble(nowtemp);//
                            dounowtemp-=273.0;
                            nowtemp = Double.toString(dounowtemp);
                            nowtemp = String.format("%.1f", dounowtemp);//소수점 한자리까지만 출력
                            Log.e("LOG field1dounowtemp",nowtemp);

                            tv.setText("실외온도 : " );
                            tv.append( nowtemp);
                            tv.append(" ℃");

                        }
                        else {
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherApiData> call, Throwable t) {
                        Log.e("fail","에러내용");
                    }
                });



                //새로고침시 실행 할 작업 실행...
                webView.reload();

                mSwipeRefreshLayout.setRefreshing(false);

            }
        });

    }
   /////////////////////
}