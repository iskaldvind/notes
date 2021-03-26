package io.iskaldvind.notes.views;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import io.iskaldvind.notes.R;
import io.iskaldvind.notes.data.CardSourceImpl;
import io.iskaldvind.notes.models.Note;
import io.iskaldvind.notes.ui.NotesListAdapter;

public class NoteFragment extends Fragment {

    static final String KEY_NOTE_ID = "NoteFragment.note_id";
    private NotesListAdapter adapter;
    private int index;
    private CardSourceImpl data;
    
    public NoteFragment() {}
    
    NoteFragment(int index, NotesListAdapter adapter) {
        this.index = index;
        this.adapter = adapter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        data = ((MainActivity) requireActivity()).notes;
        TextView title = view.findViewById(R.id.note_title);
        Note note = data.getNote(index);
        if (note != null) {
            title.setText(note.getTitle());
            TextView date = view.findViewById(R.id.note_date);
            date.setText(note.getDate());
            TextView description = view.findViewById(R.id.note_description);
            description.setText(note.getDescription());
            ImageView noteImage = view.findViewById(R.id.note_image);
            String imageUrl = note.getPhoto();
            if (imageUrl != null) {
                noteImage.setVisibility(View.VISIBLE);
                ((MainActivity) requireActivity()).imageLoader.displayImage(imageUrl, noteImage);
            } else {
                noteImage.setVisibility(View.GONE);
            }
            TextView noteUrl = view.findViewById(R.id.note_link);
            String url = note.getUrl();
            if (url != null) {
                noteUrl.setVisibility(View.VISIBLE);
                noteUrl.setText(url);
            } else {
                noteUrl.setVisibility(View.GONE);
            }
            ImageView settingsButton = view.findViewById(R.id.note_menu_button);
            settingsButton.setOnClickListener((view1 -> {
                PopupMenu popupMenu = new PopupMenu(requireActivity(), settingsButton);
                popupMenu.getMenuInflater().inflate(R.menu.button_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.edit: {
                            ((MainActivity) requireActivity()).showNoteEdit(note, index, adapter);
                            return true;
                        }
                        case R.id.delete: {
                            data.remove(index);
                            adapter.notifyDataSetChanged();
                            ((MainActivity) requireActivity()).showNotes();
                            return true;
                        }
                        default: {
                            return false;
                        }
                    }
                });

                popupMenu.show();
            }));
        }
    }
}
