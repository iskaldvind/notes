package io.iskaldvind.notes.data;

import android.content.res.Resources;
import java.util.ArrayList;
import java.util.List;
import io.iskaldvind.notes.R;
import io.iskaldvind.notes.models.Note;

public class CardSourceImpl implements CardSource {

    private List<Note> dataSource;
    private Resources resources;

    public CardSourceImpl(Resources resources) {
        dataSource = new ArrayList<>(7);
        this.resources = resources;
    }

    public CardSourceImpl init(){
        String[] titles = resources.getStringArray(R.array.titles);
        String[] descriptions = resources.getStringArray(R.array.descriptions);
        String[] dates = resources.getStringArray(R.array.dates);
        for (int i = 0; i < titles.length; i++) {
            Note note = new Note(titles[i], descriptions[i], dates[i]);
            if (i == 0) {
                note.setPhoto(resources.getString(R.string.hello_photo_url));
                note.setUrl(resources.getString(R.string.hello_ling_url));
            }
            dataSource.add(note);
        }
        return this;
    }
    
    
    public Note getNote(int position) {
        return dataSource.get(position);
    }

    public int size(){
        return dataSource.size();
    }
}
