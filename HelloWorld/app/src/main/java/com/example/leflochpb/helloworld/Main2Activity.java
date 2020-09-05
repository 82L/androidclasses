package com.example.leflochpb.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class Main2Activity extends Activity {

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textView=findViewById(R.id.textView);
        Intent intent=getIntent();
        String name = intent.getStringExtra("name");
        textView.setText("hello " + name);

    }

}
