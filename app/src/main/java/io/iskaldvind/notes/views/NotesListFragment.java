package io.iskaldvind.notes.views;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import io.iskaldvind.notes.R;
import io.iskaldvind.notes.data.CardDataSource;
import io.iskaldvind.notes.data.CardDataSourceFirebaseImpl;
import io.iskaldvind.notes.ui.ViewHolderAdapter;

@SuppressWarnings("FieldCanBeLocal")
public class NotesListFragment extends Fragment {
    public static final String ARG_NOTE_IDX = "NotesListFragment.arg_note_idx";
    private int mCurrentIdx = -1;
    private int mLastSelectedPosition = -1;
    private CardDataSource mCardDataSource;
    private ViewHolderAdapter viewHolderAdapter;
    private RecyclerView mRecyclerView;
    private MainActivity parent;

    private final CardDataSource.CardDataSourceListener mListener = new CardDataSource.CardDataSourceListener() {
        @Override
        public void onItemAdded(int idx) {
            if (viewHolderAdapter != null) {
                viewHolderAdapter.notifyItemInserted(idx);
            }
        }

        @Override
        public void onItemRemoved(int idx) {
            if (viewHolderAdapter != null) {
                viewHolderAdapter.notifyItemRemoved(idx);
            }
        }

        @Override
        public void onItemUpdated(int idx) {
            if (viewHolderAdapter != null) {
                viewHolderAdapter.notifyItemChanged(idx);
            }
        }

        @Override
        public void onDataSetChanged() {
            if (viewHolderAdapter != null) {
                viewHolderAdapter.notifyDataSetChanged();
            }
        }
    };

    public NotesListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parent = (MainActivity) requireActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_notes_list, container, false);
        mRecyclerView.setHasFixedSize(true);

        DividerItemDecoration decorator = new DividerItemDecoration(requireActivity(),
                LinearLayoutManager.VERTICAL);
        decorator.setDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.separator, null));
        mRecyclerView.addItemDecoration(decorator);

        DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        mRecyclerView.setItemAnimator(itemAnimator);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        mCardDataSource = CardDataSourceFirebaseImpl.getInstance();
        viewHolderAdapter = new ViewHolderAdapter(this, mCardDataSource);
        mCardDataSource.addCardDataSourceListener(mListener);
        viewHolderAdapter.setOnClickListener((v, position) -> {
            parent.lastNoteIndex = position;
            mCurrentIdx = position;
            if (getResources().getConfiguration().orientation ==
                    Configuration.ORIENTATION_PORTRAIT) {
                parent.showNotePortrait();
            } else {
                parent.showNoteLandscape();
            }
        });
        mRecyclerView.setAdapter(viewHolderAdapter);

        return mRecyclerView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCardDataSource.removeCardDataSourceListener(mListener);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);

        mCurrentIdx = bundle != null ? bundle.getInt(ARG_NOTE_IDX, -1) : parent.lastNoteIndex;
        if (mCurrentIdx != -1 &&
                getResources().getConfiguration().orientation ==
                        Configuration.ORIENTATION_LANDSCAPE) {
            parent.showNoteLandscape();
        }
    }
    
    
    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v,
                                    @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = requireActivity().getMenuInflater();
        menuInflater.inflate(R.menu.button_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.edit) {
            if (mLastSelectedPosition != -1) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container,
                        NoteEditFragment.newInstance(mLastSelectedPosition, false));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        } else if (item.getItemId() == R.id.delete) {
            if (mLastSelectedPosition != -1) {
                mCardDataSource.remove(mLastSelectedPosition);
                viewHolderAdapter.notifyItemRemoved(mLastSelectedPosition);
            }
        } else {
            return super.onContextItemSelected(item);
        }
        return true;
    }

    public void setLastSelectedPosition(int lastSelectedPosition) {
        mLastSelectedPosition = lastSelectedPosition;
    }

    public interface OnClickListener {
        void onItemClick(View v, int position);
    }

}