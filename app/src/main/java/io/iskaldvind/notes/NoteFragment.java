package io.iskaldvind.notes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NoteFragment extends Fragment {

    public static final String KEY_NOTE_INDEX = "NoteFragment.note_idx";
    private int mNoteIndex;
    
    static NoteFragment newInstance(int index) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_NOTE_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNoteIndex = getArguments().getInt(KEY_NOTE_INDEX);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note, container, false);
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView title = view.findViewById(R.id.title);
        String[] titles = getResources().getStringArray(R.array.titles);
        title.setText(titles[mNoteIndex]);
        TextView date = view.findViewById(R.id.date);
        String[] dates = getResources().getStringArray(R.array.dates);
        date.setText(dates[mNoteIndex]);
        TextView description = view.findViewById(R.id.description);
        String[] descriptions = getResources().getStringArray(R.array.descriptions);
        description.setText(descriptions[mNoteIndex]);
    }
}
