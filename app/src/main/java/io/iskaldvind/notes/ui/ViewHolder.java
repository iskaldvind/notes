package io.iskaldvind.notes.ui;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.concurrent.atomic.AtomicInteger;

import io.iskaldvind.notes.R;
import io.iskaldvind.notes.models.CardData;
import io.iskaldvind.notes.views.NotesListFragment;

@SuppressWarnings("WeakerAccess")
public class ViewHolder extends RecyclerView.ViewHolder {
    private static final AtomicInteger COUNTER = new AtomicInteger();
    public final int id;
    public final TextView text;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        id = COUNTER.incrementAndGet();
        text = itemView.findViewById(R.id.note_list_item_label);
    }

    public void populate(NotesListFragment fragment, CardData data) {
        text.setText(data.getTitle());
        itemView.setOnLongClickListener((v) -> {
            fragment.setLastSelectedPosition(getLayoutPosition());
            return false;
        });
        fragment.registerForContextMenu(itemView);
    }

    public void clear(Fragment fragment) {
        itemView.setOnLongClickListener(null);
        fragment.unregisterForContextMenu(itemView);
    }
}
