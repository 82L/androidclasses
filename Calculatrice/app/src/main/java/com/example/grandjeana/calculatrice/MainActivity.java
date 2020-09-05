package com.example.grandjeana.calculatrice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

        private Button button1, button2, button3, button4;
        private EditText number1, number2;
        private TextView textView;
        private Float res,c1, c2;
        private String op;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        number1 = findViewById(R.id.editText);
        number2 = findViewById(R.id.editText3);
        button1 = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        textView = findViewById(R.id.textView);

        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                Log.d("MainActivity", "Clique sur le bouton");
                c1 =Float.parseFloat(number2.getText().toString());
                c2= Float.parseFloat(number1.getText().toString()) ;
                res = c1+ c2;


                Log.d("MainActivity", "res");

               textView.setText(Float.toString(res));
                op="+";
                operation();

            }

        });
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                Log.d("MainActivity", "Clique sur le bouton");
                c1 =Float.parseFloat(number2.getText().toString());
                 c2= Float.parseFloat(number1.getText().toString()) ;
                 res =c1/c2 ;


                Log.d("MainActivity", "res");

                textView.setText(Float.toString(res));
                op="/";
                operation();

            }

        });
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                Log.d("MainActivity", "Clique sur le bouton");
                 c1 =Float.parseFloat(number2.getText().toString());
                c2= Float.parseFloat(number1.getText().toString()) ;
                 res =c1*c2;


                Log.d("MainActivity", "res");

                textView.setText(Float.toString(res));
                op="*";
                operation();

            }

        });
        button4.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                Log.d("MainActivity", "Clique sur le bouton");

                c1 =Float.parseFloat(number2.getText().toString());
                 c2= Float.parseFloat(number1.getText().toString()) ;
                res=c1-c2;

                Log.d("MainActivity", "res");

                textView.setText(Float.toString(res));
                op="-";
                operation();

            }

        });

    }

    private void operation()
    {
        Intent intent= new Intent( this, Main2Activity.class);
        intent.putExtra("c1", Float.toString(c1));
        intent.putExtra("c2", Float.toString(c2));
        intent.putExtra("op", op);
        intent.putExtra("res", Float.toString(res));
        startActivity(intent);
    }
}
