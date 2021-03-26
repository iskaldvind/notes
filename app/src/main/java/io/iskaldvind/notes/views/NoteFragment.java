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
import android.widget.Toast;
import java.util.Objects;
import io.iskaldvind.notes.R;
import io.iskaldvind.notes.models.Note;

public class NoteFragment extends Fragment {

    static final String KEY_NOTE_ID = "NoteFragment.note_id";
    private Note note;
    
    public NoteFragment() {}
    
    NoteFragment(Note note) {
        this.note = note;
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
        TextView title = view.findViewById(R.id.note_title);
        assert note != null;
        title.setText(note.getTitle());
        TextView date = view.findViewById(R.id.note_date);
        date.setText(note.getDate());
        TextView description = view.findViewById(R.id.note_description);
        description.setText(note.getDescription());
        ImageView noteImage = view.findViewById(R.id.note_image);
        String imageUrl = note.getPhoto();
        if (imageUrl != null) {
            noteImage.setVisibility(View.VISIBLE);
            ((MainActivity) Objects.requireNonNull(getActivity())).imageLoader.displayImage(imageUrl, noteImage);
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
                    case R.id.photo: {
                        Toast.makeText(getActivity(), "Setting photo is not implemented yet", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    case R.id.url: {
                        Toast.makeText(getActivity(), "Setting url is not implemented yet", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    case R.id.share: {
                        Toast.makeText(getActivity(), "Sharing is not implemented yet", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    case R.id.delete: {
                        Toast.makeText(getActivity(), "Deleting is not implemented yet", Toast.LENGTH_SHORT).show();
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
