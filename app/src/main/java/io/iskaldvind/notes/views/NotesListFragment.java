package io.iskaldvind.notes.views;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Objects;

import io.iskaldvind.notes.R;
import io.iskaldvind.notes.models.Note;

public class NotesListFragment extends Fragment {
    
    static final String KEY_NOTES = "NoteFragment.notes";
    private HashMap<Integer, Note> notes;
    private int mCurrentNoteId = 0;

    public NotesListFragment() {}
    
    NotesListFragment(HashMap<Integer, Note> notes, int lastNoteIndex) {
        this.notes = notes;
        mCurrentNoteId = lastNoteIndex;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (bundle != null) {
            mCurrentNoteId = bundle.getInt(NoteFragment.KEY_NOTE_ID, 0);
            //noinspection unchecked
            notes = (HashMap<Integer, Note>) bundle.getSerializable(KEY_NOTES);
        } else {
            mCurrentNoteId = ((MainActivity) Objects.requireNonNull(getActivity())).lastNoteIndex;
            notes = ((MainActivity) getActivity()).notes;
        }
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && notes.get(mCurrentNoteId) != null) {
            ((MainActivity) Objects.requireNonNull(getActivity())).showNoteLandscape(mCurrentNoteId);
        }
        initList(view);
    }

    private void initList(View view) {
        LinearLayout layoutView = (LinearLayout) view;
        for (int index = 0; index < notes.size(); index++) {
            Note note = notes.get(index);
            TextView tvTitle = new TextView(getContext());
            assert note != null;
            String title = note.getTitle();
            tvTitle.setText(title);
            tvTitle.setTextSize(24);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(16, 16, 16, 0);
            tvTitle.setLayoutParams(layoutParams);
            final int id = index;
            tvTitle.setOnClickListener((it) -> {
                mCurrentNoteId = id;
                ((MainActivity) Objects.requireNonNull(getActivity())).lastNoteIndex = id;
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    ((MainActivity) Objects.requireNonNull(getActivity())).showNotePortrait(mCurrentNoteId);
                } else {
                    ((MainActivity) Objects.requireNonNull(getActivity())).showNoteLandscape(mCurrentNoteId);
                }
            });
            layoutView.addView(tvTitle);
        }
    }
    
    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(NoteFragment.KEY_NOTE_ID, mCurrentNoteId);
        bundle.putSerializable(KEY_NOTES, notes);
    }
}
