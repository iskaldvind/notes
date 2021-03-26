package io.iskaldvind.notes.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.iskaldvind.notes.R;
import io.iskaldvind.notes.data.CardSource;
import io.iskaldvind.notes.models.Note;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.ViewHolder> {
    
    private static OnItemClickListener itemClickListener;
    private CardSource dataSource;

    public NotesListAdapter(CardSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public void SetOnItemClickListener(OnItemClickListener itemClickListener) {
        NotesListAdapter.itemClickListener = itemClickListener;
    }
    
    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }
    
    @NonNull
    @Override
    public NotesListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_list_item, viewGroup, false);
        return new ViewHolder(v);
    }
    
    @Override
    public void onBindViewHolder(@NonNull NotesListAdapter.ViewHolder viewHolder, int i) {
        viewHolder.setData(dataSource.getNote(i));
    }
    
    @Override
    public int getItemCount() {
        return dataSource.size();
    }
    
    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.note_list_item_label);
            title.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(v, getAdapterPosition());
                }
            });

        }

        void setData(Note cardData){
            title.setText(cardData.getTitle());
        }
    }
}
