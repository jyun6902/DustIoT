package com.zzj.semicircleview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.list);


        String[] values = new String[] {
                "Temperature",
                "Dust",
                "Humidity",
                "Control"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = new Intent ();
        switch (position) {
            case 0:
                intent.setClass(MainActivity.this, TemperatureActivity.class);//temp
                break;

            case 1:
                intent.setClass(MainActivity.this, DustActivity.class);
                break;

            case 2:
                intent.setClass(MainActivity.this, HumidityActivity.class);
                break;

            case 3:
                intent.setClass(MainActivity.this, ControlActivity.class);
                break;

        }
        startActivity(intent);
    }
}
