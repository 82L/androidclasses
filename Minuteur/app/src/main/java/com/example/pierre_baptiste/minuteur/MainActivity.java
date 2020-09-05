package com.example.pierre_baptiste.minuteur;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.TimePicker;




public class MainActivity extends Activity implements View.OnClickListener {

    private static final String STATE_TIME_MINUTE = "MinuteTime";
    private static final String STATE_TIME_SECOND = "SecondTime";
    private TextView textView;
    private Button button;
    private  LoadingTask loadingTask =null;
    private TimePicker timePicker;
    private  int minute, second;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button =  findViewById(R.id.button);
        textView= findViewById(R.id.textView);
        button.setOnClickListener(this);
        timePicker= findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            minute = savedInstanceState.getInt(STATE_TIME_MINUTE);
            second = savedInstanceState.getInt(STATE_TIME_SECOND);
        }
        else {
            minute=0;
            second=0;
        }
        timePicker.setCurrentHour(minute);
        timePicker.setCurrentMinute(second);

    }
    @Override
    public  void onClick(View view){
        if (loadingTask != null ) {
            loadingTask.cancel(true);
            loadingTask = null;
            button.setText("start");
        } else {
            minute=timePicker.getCurrentHour();
            second=timePicker.getCurrentMinute();
            loadingTask = new LoadingTask(this,minute, second, textView, button);
            loadingTask.execute();
            button.setText("cancel");
        }

    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putInt(STATE_TIME_MINUTE, minute);
        savedInstanceState.putInt(STATE_TIME_SECOND, second);
        super.onSaveInstanceState(savedInstanceState);
    }
    public void finprog()
    {
        loadingTask = null;
        button.setText("start");
    }



}

