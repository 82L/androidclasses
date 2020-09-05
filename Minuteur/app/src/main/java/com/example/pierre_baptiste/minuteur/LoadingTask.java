package com.example.pierre_baptiste.minuteur;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;


import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class LoadingTask extends AsyncTask<Object, Integer, String> {

    @SuppressLint("StaticFieldLeak")
    private final Context context;

    private  int minute;
    private  int second;
    private int millis;
    @SuppressLint("StaticFieldLeak")
    private final TextView textView;
    @SuppressLint("StaticFieldLeak")
    private  final Button button;

    LoadingTask(Context context, int minute, int second, TextView textView, Button button) {
        this.context = context;
        this.minute=minute;
        this.second=second;
        this.millis=0;
        this.textView = textView;
        this.button=button;
    }

    @Override
    protected String doInBackground(Object[] params) {
        try {
            int progress = 1;

            while (minute > 0 || second > 0){
                while (second >= 0) {
                    while (millis >= 0) {
                        publishProgress(minute, second, millis);
                        Thread.sleep(100);
                        millis=millis-progress;
                    }
                    millis=9;

                    second = second -progress;
                    }
                if(minute>0) {
                    second = 59;
                    minute = minute -progress;
                    }
                }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "terminé";
    }

    @Override
    protected void onPreExecute() {
        textView.setText(minute+":"+second+"."+millis);
        Toast.makeText(context, "démarrage", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostExecute(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onProgressUpdate(Integer... values) {
        textView.setText(values[0]+":"+values[1]+"."+values[2]);
    }

    @Override
    protected void onCancelled() {
        Toast.makeText(context, "cancelled", Toast.LENGTH_SHORT).show();
    }
}
