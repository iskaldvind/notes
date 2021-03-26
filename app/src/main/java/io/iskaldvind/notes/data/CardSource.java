package io.iskaldvind.notes.data;

import androidx.annotation.NonNull;

import io.iskaldvind.notes.models.Note;

public interface CardSource {

    Note getNote(int position);
    int size();
    
    void add(@NonNull Note note);
    void edit(int position, @NonNull Note note);
    void remove(int position);
}
