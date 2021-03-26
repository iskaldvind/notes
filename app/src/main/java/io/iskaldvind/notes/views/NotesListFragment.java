package io.iskaldvind.notes.views;

import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.Objects;
import io.iskaldvind.notes.R;
import io.iskaldvind.notes.data.CardSource;
import io.iskaldvind.notes.data.CardSourceImpl;
import io.iskaldvind.notes.ui.NotesListAdapter;

public class NotesListFragment extends Fragment {
    
    private int mCurrentNoteId = 0;
    private RecyclerView mRecyclerView;

    public NotesListFragment() {}
    
    NotesListFragment(int lastNoteIndex) {
        mCurrentNoteId = lastNoteIndex;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_notes_list, container, false);
        mRecyclerView = view.findViewById(R.id.notes_list);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (bundle != null) {
            mCurrentNoteId = bundle.getInt(NoteFragment.KEY_NOTE_ID, 0);
        } else {
            mCurrentNoteId = ((MainActivity) Objects.requireNonNull(getActivity())).lastNoteIndex;
        }
        CardSourceImpl data = ((MainActivity) Objects.requireNonNull(getActivity())).notes;
        initRecyclerView(mRecyclerView, data);
    }

    private void initRecyclerView(RecyclerView recyclerView, CardSource data) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        NotesListAdapter adapter = new NotesListAdapter(data);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(requireActivity(),  LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        recyclerView.addItemDecoration(itemDecoration);
        adapter.SetOnItemClickListener((view, position) -> {
            mCurrentNoteId = position;
            ((MainActivity) Objects.requireNonNull(getActivity())).lastNoteIndex = position;
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    ((MainActivity) Objects.requireNonNull(getActivity())).showNotePortrait(mCurrentNoteId, adapter);
                } else {
                    ((MainActivity) Objects.requireNonNull(getActivity())).showNoteLandscape(mCurrentNoteId, adapter);
                }
        });
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ((MainActivity) Objects.requireNonNull(getActivity())).showNoteLandscape(mCurrentNoteId, adapter);
        }
    }
    
    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(NoteFragment.KEY_NOTE_ID, mCurrentNoteId);
    }
}
