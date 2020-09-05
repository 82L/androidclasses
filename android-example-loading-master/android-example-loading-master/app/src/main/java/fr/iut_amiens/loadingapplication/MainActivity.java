package fr.iut_amiens.loadingapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressBar progressBar;

    private Button button;

    private LoadingTask loadingTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (loadingTask != null) {
            loadingTask.cancel(true);
            loadingTask = null;
            button.setText(R.string.start);
        } else {
            loadingTask = new LoadingTask(this, progressBar);
            loadingTask.execute();
            button.setText(R.string.cancel);
        }
    }
}
