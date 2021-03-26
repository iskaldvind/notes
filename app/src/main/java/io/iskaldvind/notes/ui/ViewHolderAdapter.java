package io.iskaldvind.notes.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

import io.iskaldvind.notes.R;
import io.iskaldvind.notes.data.CardDataSource;
import io.iskaldvind.notes.models.CardData;
import io.iskaldvind.notes.views.NotesListFragment;

public class ViewHolderAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final NotesListFragment mFragment;
    private final LayoutInflater mInflater;
    private final CardDataSource mDataSource;

    private NotesListFragment.OnClickListener mOnClickListener;

    public ViewHolderAdapter(NotesListFragment fragment, CardDataSource dataSource) {
        mFragment = fragment;
        mInflater = fragment.getLayoutInflater();
        mDataSource = dataSource;
    }

    public void setOnClickListener(NotesListFragment.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.note_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        android.util.Log.e(NotesListFragment.class.getCanonicalName(),
                String.format(Locale.getDefault(), "created holder id=%d", viewHolder.id));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardData cardData = mDataSource.getItemAt(position);
        holder.populate(mFragment, cardData);
        holder.itemView.setOnClickListener((v) -> {
            if (mOnClickListener != null) {
                mOnClickListener.onItemClick(v, position);
            }
        });
        android.util.Log.e(NotesListFragment.class.getCanonicalName(),
                String.format(Locale.getDefault(), "used holder id=%d", holder.id));
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
        holder.clear(mFragment);
        android.util.Log.e(NotesListFragment.class.getCanonicalName(),
                String.format(Locale.getDefault(), "recycled holder id=%d", holder.id));
    }

    @Override
    public int getItemCount() {
        return mDataSource.getItemsCount();
    }
}

