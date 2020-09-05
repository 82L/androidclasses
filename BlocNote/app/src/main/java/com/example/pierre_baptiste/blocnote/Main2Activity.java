package com.example.pierre_baptiste.blocnote;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Main2Activity extends Activity {
    private String nom;
    private String content;
    private String id;
    private TextView textView;
    private EditText editText;
    private DatabaseOpenHelper openHelper;
    private Toast currentToast = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textView=findViewById(R.id.textView2);
        editText=findViewById(R.id.editText2);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        nom = intent.getStringExtra("nom");

        id=intent.getStringExtra("id");
        setTitle(nom);
        textView.setText(nom);
       openHelper = new DatabaseOpenHelper(this);
        SQLiteDatabase database= openHelper.getReadableDatabase();



        Cursor cursor=database.rawQuery( "SELECT content FROM todo WHERE id= ? ", new String[] {id});
        int idcontent = cursor.getColumnIndex("content");
        while(cursor.moveToNext()) {
            content = cursor.getString(idcontent);
            editText.setText(content);
        }
        cursor.close();
    }

    @Override
    protected void onStop() {
        save();
        super.onStop();

    }


    protected void save()
    {

        SQLiteDatabase db = openHelper.getWritableDatabase();
        currentToast = Toast.makeText(this, "Note sauvegardée", Toast.LENGTH_SHORT);
        currentToast.show();
        // New value for one colum
        ContentValues values = new ContentValues();
        values.put("content", editText.getText().toString());

        // Which row to update, based on the title
        String selection = "id" + " LIKE ?";
        String[] selectionArgs = { id };

        db.update("todo", values, selection,  selectionArgs);


    }


}
