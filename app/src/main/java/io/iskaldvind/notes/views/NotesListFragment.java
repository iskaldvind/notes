package io.iskaldvind.notes.views;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Locale;

import io.iskaldvind.notes.R;
import io.iskaldvind.notes.data.CardDataSource;
import io.iskaldvind.notes.data.CardDataSourceFirebaseImpl;
import io.iskaldvind.notes.ui.ViewHolderAdapter;

@SuppressWarnings("FieldCanBeLocal")
public class NotesListFragment extends Fragment {
    private static final String ARG_NOTE_IDX = "NotesListFragment.arg_note_idx";
    private int mCurrentIdx = -1;
    private int mLastSelectedPosition = -1;
    private CardDataSource mCardDataSource;
    private ViewHolderAdapter mViewHolderAdapter;
    private RecyclerView mRecyclerView;

    private CardDataSource.CardDataSourceListener mListener = new CardDataSource.CardDataSourceListener() {
        @Override
        public void onItemAdded(int idx) {
            if (mViewHolderAdapter != null) {
                mViewHolderAdapter.notifyItemInserted(idx);
            }
        }

        @Override
        public void onItemRemoved(int idx) {
            if (mViewHolderAdapter != null) {
                mViewHolderAdapter.notifyItemRemoved(idx);
            }
        }

        @Override
        public void onItemUpdated(int idx) {
            if (mViewHolderAdapter != null) {
                mViewHolderAdapter.notifyItemChanged(idx);
            }
        }

        @Override
        public void onDataSetChanged() {
            if (mViewHolderAdapter != null) {
                mViewHolderAdapter.notifyDataSetChanged();
            }
        }
    };

    public NotesListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        decorator.setDrawable(getResources().getDrawable(R.drawable.separator));
        mRecyclerView.addItemDecoration(decorator);

        DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        mRecyclerView.setItemAnimator(itemAnimator);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        mCardDataSource = CardDataSourceFirebaseImpl.getInstance();
        mViewHolderAdapter = new ViewHolderAdapter(this, mCardDataSource);
        mCardDataSource.addCardDataSourceListener(mListener);
        mViewHolderAdapter.setOnClickListener((v, position) -> {
            if (getResources().getConfiguration().orientation ==
                    Configuration.ORIENTATION_PORTRAIT) {
                ((MainActivity) requireActivity()).showNotePortrait(position, mViewHolderAdapter);
            } else {
                ((MainActivity) requireActivity()).showNoteLandscape(position, mViewHolderAdapter);
            }
        });
        mRecyclerView.setAdapter(mViewHolderAdapter);

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

        if (bundle != null) {
            mCurrentIdx = bundle.getInt(ARG_NOTE_IDX, -1);
            if (mCurrentIdx != -1 &&
                    getResources().getConfiguration().orientation ==
                            Configuration.ORIENTATION_LANDSCAPE) {
                ((MainActivity) requireActivity()).showNoteLandscape(mCurrentIdx, mViewHolderAdapter);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);

        bundle.putInt(ARG_NOTE_IDX, mCurrentIdx);
    }

    private void setCurrentIdx(int idx) {
        mCurrentIdx = idx;
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
                mViewHolderAdapter.notifyItemRemoved(mLastSelectedPosition);
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