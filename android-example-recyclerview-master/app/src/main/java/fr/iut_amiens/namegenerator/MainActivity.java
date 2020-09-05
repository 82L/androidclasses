package fr.iut_amiens.namegenerator;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.kohsuke.randname.RandomNameGenerator;

public class MainActivity extends Activity implements NameTouchListener.OnNameClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RandomNameGenerator randomNameGenerator;

    private RecyclerView recyclerView;

    private Toast currentToast = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        randomNameGenerator = new RandomNameGenerator(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new NameAdapter(this));
        recyclerView.addOnItemTouchListener(new NameTouchListener(this, this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            addName();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addName() {
        final String name = randomNameGenerator.next();
        Log.d(TAG, String.format("name generated: %s", name));
        ((NameAdapter) recyclerView.getAdapter()).add(name);
    }

    @Override
    public void onSingleTap(String name) {
        if (currentToast != null) {
            currentToast.cancel();
        }
        currentToast = Toast.makeText(this, getString(R.string.name_clicked, name), Toast.LENGTH_SHORT);
        currentToast.show();
    }

    @Override
    public void onDoubleTap(String name) {
        if (currentToast != null) {
            currentToast.cancel();
        }
        currentToast = Toast.makeText(this, getString(R.string.name_double_clicked, name), Toast.LENGTH_SHORT);
        currentToast.show();
    }

    @Override
    public void onLongPress(String name) {
        if (currentToast != null) {
            currentToast.cancel();
        }
        currentToast = Toast.makeText(this, getString(R.string.name_long_clicked, name), Toast.LENGTH_SHORT);
        currentToast.show();
    }
}
