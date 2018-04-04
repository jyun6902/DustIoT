package com.zzj.semicircleview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by JY on 2018-02-12.
 */

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        try{
            Thread.sleep(2000);//대기 초 설정
        }
        catch  (InterruptedException e){
            e.printStackTrace();
        }
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}

