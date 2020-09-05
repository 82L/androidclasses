package fr.iut_amiens.weatherapplication;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.joda.time.DateTime;

import java.io.IOException;

import fr.iut_amiens.weatherapplication.openweathermap.ForecastResponse;
import fr.iut_amiens.weatherapplication.openweathermap.WeatherManager;

/**
 * Created by Pierre-Baptiste on 16/03/2018.
 */

public class WeatherTask4 extends AsyncTask<Object, String, String> {

    private final RecyclerView recyclerView;
    private final WeatherManager weatherManager;
    private final Double latitude, longitude;


    public WeatherTask4(RecyclerView recyclerView, Double latitude, Double longitude) {
        this.recyclerView = recyclerView;
        this.latitude = latitude;
        this.longitude = longitude;
        this.weatherManager=new WeatherManager();

    }

    @Override
    protected String doInBackground(Object[] objects) {
        ForecastResponse weather=null;
        try {
            weather =  weatherManager.findForecastByGeographicCoordinates(latitude,longitude);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (weather!= null){
            for(int i=0; i<weather.getList().size(); i++) {
                if((weather.getList().get(i).getDatetime().getHourOfDay()<=13 && weather.getList().get(i).getDatetime().getHourOfDay()>=11) && weather.getList().get(i).getDatetime().getDayOfYear()!= DateTime.now().getDayOfYear()) {
                    Log.d("test", Integer.toString( weather.getList().get(i).getDatetime().getHourOfDay())  );
                    publishProgress(weather.getList().get(i).getDatetime().toString("dd/MM/yyyy"), Double.toString(weather.getList().get(i).getMain().getTemp()), (Integer.toString(weather.getList().get(i).getMain().getHumidity())), Double.toString(weather.getList().get(i).getMain().getPressure()), Double.toString(weather.getList().get(i).getWind().getSpeed()), weather.getList().get(i).getWeather().get(0).getMain());
                }
            }
        }
        return "Fin";
    }

    protected void onProgressUpdate(String... values) {
        ((NameAdapter) recyclerView.getAdapter()).add("Pour le "+values[0]+"\n La température sera de "+values[1]+ "° vers midi \n Pour un taux d'humidité de "+ values[2]+"% dans l'air \n Avec une pression atmosphérique de "+values[3]+" bar \n Le vent aura une vitesse de "+values[4]+" km/h \n et le temps sera "+values[5]);
    }

}
