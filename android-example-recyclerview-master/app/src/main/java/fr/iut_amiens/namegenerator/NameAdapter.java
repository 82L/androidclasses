package fr.iut_amiens.namegenerator;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class NameAdapter extends RecyclerView.Adapter<NameViewHolder> {

    private static final String TAG = NameAdapter.class.getSimpleName();

    private LayoutInflater layoutInflater;

    private List<String> names = new ArrayList<>();

    public NameAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    public void add(String name) {
        Log.d(TAG, "Add element");
        names.add(name);
        notifyItemInserted(names.size() - 1);
    }

    @Override
    public NameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "Create view holder");
        return new NameViewHolder(layoutInflater.inflate(R.layout.listitem_name, parent, false));
    }

    @Override
    public void onBindViewHolder(NameViewHolder holder, int position) {
        Log.d(TAG, "Bind view holder " + position);
        holder.bind(getItem(position));
    }

    @Override
    public void onViewRecycled(NameViewHolder holder) {
        Log.d(TAG, "Recycle view holder");
        holder.recycle();
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public String getItem(int position) {
        return names.get(position);
    }
}
