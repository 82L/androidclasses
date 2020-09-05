package com.example.pierre_baptiste.blocnote;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends Activity implements NameTouchListener.OnNameClickListener {
    private EditText editText;
    private RecyclerView recyclerView;

    private Toast currentToast = null;
    private TextView textView;
    private DatabaseOpenHelper openHelper;
    private int currentId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=findViewById(R.id.textView);
        textView.setText("");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new NameAdapter(this));
        recyclerView.addOnItemTouchListener(new NameTouchListener(this, this));
        openHelper = new DatabaseOpenHelper(this);
        SQLiteDatabase database= openHelper.getReadableDatabase();
        Cursor cursor=database.rawQuery( "SELECT id, name FROM todo ", new String[] {});
        int idIndex = cursor.getColumnIndex("id");
        int nameIndex= cursor.getColumnIndex("name");

        while(cursor.moveToNext()){

            int id=cursor.getInt(idIndex);
            String name = cursor.getString(nameIndex);
            ((NameAdapter) recyclerView.getAdapter()).add(id+" "+name);
            currentId =id;
        }
        cursor.close();
    }
    private void addNote(String name) {

        SQLiteDatabase db =openHelper.getWritableDatabase();
        currentId +=1;
        ContentValues values = new ContentValues();
        values.put("name", name);
        db.insert("todo", null, values);

        ((NameAdapter) recyclerView.getAdapter()).add(currentId +" "+name);

        operation(currentId +" "+name);
    }

    @Override
    public void onSingleTap(String name) {
        if (currentToast != null) {
            currentToast.cancel();
        }
        currentToast = Toast.makeText(this, getString(R.string.name_clicked, name), Toast.LENGTH_SHORT);
        currentToast.show();
        operation(name);
    }
    @Override
    public void onDoubleTap(String name) {
        if (currentToast != null) {
            currentToast.cancel();
        }
        currentToast = Toast.makeText(this, getString(R.string.name_double_clicked, name), Toast.LENGTH_SHORT);
        currentToast.show();
        operation(name);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        showDialog(1);
        return true;

    }
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_result, menu);

        return true;
    }
    @Override
    public void onLongPress(String name) {
        if (currentToast != null) {
            currentToast.cancel();
        }
        currentToast = Toast.makeText(this, getString(R.string.name_long_clicked, name), Toast.LENGTH_SHORT);
        currentToast.show();
        operation(name);
    }
    @Override
    protected  Dialog onCreateDialog(int id){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch(id) {
            case 1:

                builder.setMessage(R.string.dialog_message);
                builder.setTitle(R.string.dialog_title);
                LayoutInflater inflater = this.getLayoutInflater();

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder.setView(inflater.inflate(R.layout.dialog_signin, null));

                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editText = (EditText) ((AlertDialog) dialog).findViewById(R.id.notename);
                        String name = editText.getText().toString();
                        addNote(name);
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                break;
        }
        return builder.create();
    }
    private void operation(String name)
    {
        String[] temp=  name.split(" ");
        String id=temp[0];
        name=temp[1];
        for(int i=2; i<temp.length; i++) {
            name += temp[i];
        }
        Intent intent= new Intent( this, Main2Activity.class);
        intent.putExtra("id", id);
        intent.putExtra("nom", name);


        startActivity(intent);
    }

}
