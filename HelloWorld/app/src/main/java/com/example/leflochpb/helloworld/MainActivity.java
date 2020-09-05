package com.example.leflochpb.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

    private EditText editText;

    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=findViewById(R.id.editText);
        button=findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
                    public void onClick(View view) {
                    Log.d("HelloActivity", "a message");
                    String name = editText.getText().toString();
                    Log.d("HelloActivity", "a message");
                    sayHello(name);
            }
        });


    }
    private void  sayHello(String name)
    {
        Intent intent= new Intent( this, Main2Activity.class);
        intent.putExtra("name", name);
        startActivity(intent);
    }
}
