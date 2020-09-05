package fr.iut_amiens.weatherapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;

import fr.iut_amiens.weatherapplication.openweathermap.WeatherManager;

import static android.content.ContentValues.TAG;

/**
 * Created by Pierre-Baptiste on 08/04/2018.
 */

public class Main2Activity extends Activity {

    private RecyclerView recyclerView;
    private WeatherManager weatherManager;
    private WeatherTask2 weatherTask2 ;
    private WeatherTask4 weatherTask4 ;
    private String ville;
    private Double latitude, longitude;
    private boolean valid;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Log.d("init", "initialisation");
        Intent intent=getIntent();
        ville=intent.getStringExtra("ville");
        valid=intent.getBooleanExtra("valid", false);
        latitude=0.0;
        longitude=0.0;
        weatherTask2 = null;
        weatherTask4 = null;
        if(valid) {
            latitude = intent.getDoubleExtra("latitude", 0.0);
            longitude = intent.getDoubleExtra("longitude", 0.0);
        }
        Log.d("init", "Recup intent");
        weatherManager = new WeatherManager();
        Log.d("init", "init recycler");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new NameAdapter(this));
        if (!valid) {
            weatherTask2 = new WeatherTask2(recyclerView, ville);
            weatherTask2.execute();
        }
        else {
            weatherTask4 = new WeatherTask4(recyclerView, latitude,longitude);
            weatherTask4.execute();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(weatherTask2!=null)
        {
            weatherTask2.cancel(true);
        }
        if(weatherTask4!=null)
        {
            weatherTask4.cancel(true);
        }
    }
}
