package io.iskaldvind.notes.models;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("WeakerAccess")
public class CardDataFromFirestore extends CardData {
    
    public static final String FIELD_ID = "id";
    public static final String FIELD_PHOTO = "photo";
    public static final String FIELD_URL = "url";
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_DATE = "date";
    

    public CardDataFromFirestore(String photo, String url, String title, String description, String date) {
        super(photo, url, title, description, date);
    }

    public CardDataFromFirestore(String id, String photo, String url, String title, String description, String date) {
        this(photo, url, title, description, date);
        setId(id);
    }
    
    public CardDataFromFirestore(String id, Map<String, Object> fields) {
        this(
                id, 
                (String) fields.get(FIELD_PHOTO),
                (String) fields.get(FIELD_URL),
                (String) fields.get(FIELD_TITLE),
                (String) fields.get(FIELD_DESCRIPTION),
                (String) fields.get(FIELD_DATE));
    }
    
    public final Map<String, Object> getFields() {
        HashMap<String, Object> fields = new HashMap<>();
        fields.put(FIELD_PHOTO, getPhoto());
        fields.put(FIELD_URL, getUrl());
        fields.put(FIELD_TITLE, getTitle());
        fields.put(FIELD_DESCRIPTION, getDescription());
        return Collections.unmodifiableMap(fields);
    }

    public CardDataFromFirestore(CardData cardData) {
        this(cardData.getId(), cardData.getPhoto(), cardData.getUrl(), cardData.getTitle(), cardData.getDescription(), cardData.getDate());
    }
}
