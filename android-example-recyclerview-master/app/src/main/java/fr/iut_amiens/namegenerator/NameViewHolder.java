package fr.iut_amiens.namegenerator;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class NameViewHolder extends RecyclerView.ViewHolder {

    private final TextView textView;

    public NameViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.name);
    }

    public void bind(String name) {
        textView.setText(name);
    }

    public void recycle() {
        textView.setText("");
    }
}
