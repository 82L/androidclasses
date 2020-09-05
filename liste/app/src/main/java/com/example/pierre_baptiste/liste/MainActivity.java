package com.example.pierre_baptiste.liste;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class MainActivity extends Activity {

    private  DatabaseOpenHelper openHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openHelper = new DatabaseOpenHelper(this);
        SQLiteDatabase database= openHelper.getReadableDatabase();
        Cursor cursor=database.rawQuery( "SELECT * FROM todo", );
        int idIndex = cursor.getColumnIndex("id");
        int nameIndex= cursor.getColumnIndex("name");
        while(cursor.moveToNext()){

            int id=cursor.getInt(idIndex);
            String name = cursor.getString(nameIndex);

        }
        cursor.close();
    }
}
