package io.iskaldvind.notes.views;

import android.annotation.SuppressLint;
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
import io.iskaldvind.notes.data.CardDataSourceFirebaseImpl;
import io.iskaldvind.notes.models.CardData;



public class NoteFragment extends Fragment {

    static final String KEY_NOTE_ID = "NoteFragment.note_id";
    private int mIndex;
    private MainActivity parent;
    
    public NoteFragment() {}
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parent = (MainActivity) requireActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note, container, false);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CardDataSourceFirebaseImpl data = parent.notes;
        mIndex = parent.lastNoteIndex;
        TextView title = view.findViewById(R.id.note_title);
        CardData cardData = data.getItemAt(mIndex);
        if (cardData != null) {
            title.setText(cardData.getTitle());
            TextView date = view.findViewById(R.id.note_date);
            date.setText(cardData.getDate());
            TextView description = view.findViewById(R.id.note_description);
            description.setText(cardData.getDescription());
            ImageView noteImage = view.findViewById(R.id.note_image);
            String imageUrl = cardData.getPhoto();
            noteImage.setVisibility(View.VISIBLE);
            ((MainActivity) requireActivity()).imageLoader.displayImage(imageUrl, noteImage);
            TextView noteUrl = view.findViewById(R.id.note_link);
            String url = cardData.getUrl();
            noteUrl.setVisibility(View.VISIBLE);
            noteUrl.setText(url);
            ImageView settingsButton = view.findViewById(R.id.note_menu_button);
            settingsButton.setOnClickListener((view1 -> {
                PopupMenu popupMenu = new PopupMenu(requireActivity(), settingsButton);
                popupMenu.getMenuInflater().inflate(R.menu.button_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.edit: {
                            parent.showNoteEdit(mIndex, false);
                            return true;
                        }
                        case R.id.delete: {
                            parent.showDeleteDialog();
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
