package fr.iut_amiens.weatherapplication;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideContext;
import com.bumptech.glide.load.model.GlideUrl;

import java.io.IOException;

import fr.iut_amiens.weatherapplication.openweathermap.WeatherManager;
import fr.iut_amiens.weatherapplication.openweathermap.WeatherResponse;

/**
 * Created by Pierre-Baptiste on 16/03/2018.
 */

public class WeatherTask extends AsyncTask<Object, String, String> {

    private final TextView textView;
    private final String text;
    private final WeatherManager weatherManager;
    private WeatherResponse weather;
    private final Context context;
    private ImageView imageView;
    public WeatherTask(TextView textView, String text, ImageView imageView, Context context) {
        this.textView = textView;
        this.text = text;
        this.weatherManager=new WeatherManager();
        this.imageView=imageView;
        this.context=context;
    }

    @Override
    protected String doInBackground(Object[] objects) {
        weather=null;
        try {
            weather = weatherManager.findWeatherByCityName(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (weather!= null){

            publishProgress(Double.toString(weather.getMain().getTemp()), (Integer.toString(weather.getMain().getHumidity())), Double.toString(weather.getMain().getPressure()),Double.toString(weather.getWind().getSpeed()), weather.getWeather().get(0).getMain() );
        }
        return "Fin";
    }

    protected void onProgressUpdate(String... values) {

        Glide.with(context).load(weather.getWeather().get(0).getIconUri()).into(imageView);
        textView.setText("La température est de "+values[0]+ "°\n Pour un taux d'humidité de "+ values[1]+"% dans l'air \n Avec une pression atmosphérique de "+values[2]+" bar \n Le vent a une vitesse de "+values[3]+" km/h \n et le temps est "+values[4]);
    }

}
