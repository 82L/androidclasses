package com.example.pierre_baptiste.liste;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Pierre-Baptiste on 23/02/2018.
 */

class DatabaseOpenHelper extends SQLiteOpenHelper {
    public  DatabaseOpenHelper(Context context)
    {
        super(context, "database", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE todo(id INTEGER PRIMARY KEY AUTOINCREMENT )");
        db.execSQL("INSERT INTO todo(name) VALUES ('je ne sais pas')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
