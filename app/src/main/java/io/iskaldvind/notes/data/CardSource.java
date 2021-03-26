package io.iskaldvind.notes.data;

import io.iskaldvind.notes.models.Note;

public interface CardSource {

    Note getNote(int position);
    int size();
}
