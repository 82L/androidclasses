package fr.iut_amiens.weatherapplication;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import fr.iut_amiens.weatherapplication.openweathermap.WeatherManager;
import fr.iut_amiens.weatherapplication.openweathermap.WeatherResponse;

public class MainActivity extends AppCompatActivity {

    private WeatherManager weatherManager;
    private LocationManager locationManager;
    private EditText editText;
    private Button button;
    private TextView textView;
    private  static  final int REQUEST_LOCATION_PERMISSION=1;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_LOCATION_PERMISSION){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                loglocation();
            }
            else {
                Toast.makeText(this, "permission refusée", Toast.LENGTH_LONG);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=findViewById(R.id.editText);
        weatherManager = new WeatherManager();
        textView= findViewById(R.id.textView);
        Log.d("init", "initialisation ok");
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Log.d("init", "création location manager");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("init", "lzncement request permission");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);


        }else {
            Log.d("init", "lancement loglocation");
            loglocation();
        }
        // Récupération de la météo actuelle :
        Log.d("init", "création et lancement bouton");
        button=findViewById(R.id.button);
        Log.d("init", "bouton initialisé");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    WeatherResponse weather = weatherManager.findWeatherByCityName(editText.getText().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        //WeatherResponse weather = weatherManager.findWeatherByGeographicCoordinates(49.8942, 2.2957);

        // documentation : https://openweathermap.org/current

        // Récupération des prévisions par nom de la ville :

        // ForecastResponse forecast = weatherManager.findForecastByCityName("Amiens");
        // ForecastResponse forecast = weatherManager.findForecastByGeographicCoordinates(49.8942, 2.2957);

        // documentation : https://openweathermap.org/forecast5
    }


    @SuppressLint("MissingPermission")
private void loglocation(){
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
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
        WeatherResponse weather=null;
        try {
            weather = weatherManager.findWeatherByGeographicCoordinates(location.getLatitude(), location.getLongitude());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (weather!=null)
        {
          //  textView.setText(weather.getWeather().toString());
        }
    }

}