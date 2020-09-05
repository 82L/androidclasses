package fr.iut_amiens.loadingapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.Toast;

public class LoadingTask extends AsyncTask<Object, Integer, String> {

    private final Context context;

    private final ProgressBar progressBar;

    public LoadingTask(Context context, ProgressBar progressBar) {
        this.context = context;
        this.progressBar = progressBar;
    }

    @Override
    protected String doInBackground(Object[] params) {
        try {
            int progress = 0;
            while (progress < 100) {
                Thread.sleep(30);
                progress++;
                publishProgress(progress);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "terminé";
    }

    @Override
    protected void onPreExecute() {
        progressBar.setProgress(0);
        Toast.makeText(context, "démarrage", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostExecute(String s) {
        progressBar.setProgress(100);
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        progressBar.setProgress(values[0]);
    }

    @Override
    protected void onCancelled() {
        Toast.makeText(context, "cancelled", Toast.LENGTH_SHORT).show();
    }
}
