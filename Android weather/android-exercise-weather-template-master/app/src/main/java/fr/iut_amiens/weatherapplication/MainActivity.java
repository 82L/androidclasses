package fr.iut_amiens.weatherapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import fr.iut_amiens.weatherapplication.openweathermap.WeatherManager;
import fr.iut_amiens.weatherapplication.openweathermap.WeatherResponse;

public class MainActivity extends AppCompatActivity {

    private WeatherManager weatherManager;
    private LocationManager locationManager;
    private EditText editText;
    private Button button, button2, button3;
    private TextView textView;
    private String ville="";
    private ImageView imageView;
    private  static  final int REQUEST_LOCATION_PERMISSION=1;
    private WeatherTask weatherTask ;
    private WeatherTask3 weatherTask3 ;
    private Location location;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_LOCATION_PERMISSION){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
            {
                loglocation();
            }
            else {
                Toast.makeText(this, "permission refusée", Toast.LENGTH_LONG);
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void loglocation(){
         location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(location==null){
            location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        }
        if(location!=null){
            Log.d("Activity", location.toString());
        }
        else{
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, new LocationListener(){

                @Override
                public void onLocationChanged(Location location) {
                    Log.d("Activity", location.toString());
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            }, null);
        }
        if(location!=null)
        {
            if(weatherTask3!=null)
            {
                weatherTask3.cancel(true);
                weatherTask3 = null;
                demarrage3();
            }
            else {
                demarrage3();
            }

        }
    }
private void demarrage3(){
    weatherTask3 = new WeatherTask3(textView, location, imageView, this);
    weatherTask3.execute();
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weatherTask = null;
        weatherTask3 = null;
        location=null;
        weatherManager = new WeatherManager();
        editText=findViewById(R.id.editText);
        textView= findViewById(R.id.textView);
        button=findViewById(R.id.button);
        button2=findViewById(R.id.button2);
        button3=findViewById(R.id.button3);
        imageView=findViewById(R.id.imageView);
        Log.d("init", "initialisation ok");
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Log.d("init", "création location manager");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("init", "lancement request permission");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }else {
            Log.d("init", "lancement loglocation");
            loglocation();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ville!=editText.getText().toString()) {
                    ville=editText.getText().toString();
                    if (weatherTask != null) {
                        close();
                        weatherTask.cancel(true);
                        weatherTask = null;
                        demarrage();
                    } else {
                        close();
                        demarrage();

                    }
                }

            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meteonow();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevision();
            }
        });
    }
    private void meteonow(){
        if (weatherTask!=null)
        {
            weatherTask.cancel(true);
            weatherTask = null;
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("init", "lancement request permission");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }else {
            Log.d("init", "lancement loglocation");
            loglocation();
        }
    }
    private void  prevision(){
        Intent intent= new Intent(this  ,Main2Activity.class);
        intent.putExtra("ville", editText.getText().toString());
        if(location!=null && weatherTask3!=null) {
            intent.putExtra("valid", true);
            intent.putExtra("latitude", location.getLatitude());
            intent.putExtra("longitude", location.getLongitude());
        }
        else {
            intent.putExtra("valid", false);
        }
        startActivity(intent);
    }
    private void demarrage(){
        weatherTask = new WeatherTask(textView, editText.getText().toString(), imageView, this);
        weatherTask.execute();
    }
    private void close(){
        if(weatherTask3!=null) {
            weatherTask3.cancel(true);
            weatherTask3 = null;
            location=null;
        }
    }
}
