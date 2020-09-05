package com.example.grandjeana.calculatrice;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class Main2Activity extends Activity {
    private TextView textView;
    private String calcul;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Resultat");
        setContentView(R.layout.activity_main2);
        textView=findViewById(R.id.textView2);
        Intent intent=getIntent();
        String res = intent.getStringExtra("res");
        String op = intent.getStringExtra("op");
        String c1 = intent.getStringExtra("c1");
        String c2 = intent.getStringExtra("c2");
        calcul =c1+" "+op+" "+c2+" = "+res;
        textView.setText(calcul);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        Uri sms = Uri.parse("sms:?body="+calcul);
        Intent intent= new Intent(Intent.ACTION_SENDTO, sms );
        intent.putExtra("calcul", calcul);
        return super.onOptionsItemSelected(item);

    }
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }


}
