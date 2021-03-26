package io.iskaldvind.notes.views;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;
import java.util.Objects;

import io.iskaldvind.notes.R;
import io.iskaldvind.notes.models.Note;
import io.iskaldvind.notes.ui.NotesListAdapter;

public class NoteEditFragment extends Fragment {
    
    private String picture;
    private String url;
    private String title;
    private String description;
    private String date;
    private int index;
    private NotesListAdapter adapter;
    private boolean isNewNote = false;
    
    public NoteEditFragment() {}
    
    NoteEditFragment (Note note, int index, NotesListAdapter adapter) {
        this.index = index;
        this.adapter = adapter;
        if (note == null) {
            picture = "";
            url = "";
            title = "";
            description = "";
            date = new Date().toString();
            isNewNote = true;
        } else {
            picture = note.getPhoto() != null ? note.getPhoto() : "";
            url = note.getUrl() != null ? note.getUrl() : "";
            title = note.getTitle();
            description = note.getDescription();
            date = note.getDate();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_edit, container, false);
        TextInputEditText photo = view.findViewById(R.id.note_edit_photo_field);
        if (picture.length() > 0) photo.setText(picture);
        TextInputEditText link = view.findViewById(R.id.note_edit_url_field);
        if (url.length() > 0) link.setText(url);
        TextInputEditText titleField = view.findViewById(R.id.note_edit_title_field);
        if (title.length() > 0) titleField.setText(title);
        TextInputEditText descriptionField = view.findViewById(R.id.note_edit_description_field);
        if (description.length() > 0) descriptionField.setText(description);
        MaterialButton cancel = view.findViewById(R.id.note_edit_cancel);
        cancel.setOnClickListener((v) -> {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                ((MainActivity) requireActivity()).showNoteLandscape(index, adapter);
            } else {
                ((MainActivity) requireActivity()).showNotePortrait(index, adapter);
            }
        });
        MaterialButton save = view.findViewById(R.id.note_edit_save);
        save.setOnClickListener((v) -> {
            title = Objects.requireNonNull(titleField.getText()).toString();
            description = Objects.requireNonNull(descriptionField.getText()).toString();
            if (title.length() == 0) {
                TextInputLayout field = view.findViewById(R.id.note_edit_title_container);
                field.setError("Required");
            } else if (description.length() == 0) {
                TextInputLayout field = view.findViewById(R.id.note_edit_description_container);
                field.setError("Required");
            } else {
                Note newNote = new Note(title, description, date);
                if (picture.length() > 0) newNote.setPhoto(picture);
                if (url.length() > 0) newNote.setUrl(url);
                if (!isNewNote) {
                    ((MainActivity) requireActivity()).notes.edit(index, newNote);
                } else {
                    ((MainActivity) requireActivity()).notes.add(newNote);
                }
                if (adapter != null) adapter.notifyDataSetChanged();
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    ((MainActivity) requireActivity()).showNoteLandscape(index, adapter);
                } else {
                    ((MainActivity) requireActivity()).showNotePortrait(index, adapter);
                }
            }
        });
        return view;
    }
}
