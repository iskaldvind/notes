package io.iskaldvind.notes.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CardData {
    @Nullable
    private String mId;

    @NonNull
    private String mPhoto;

    @NonNull
    private String mUrl;
    
    @NonNull
    private String mTitle;
    
    @NonNull
    private String mDescription;
    
    @NonNull
    private String mDate;
    
    public CardData(@NonNull String photo, @NonNull String url, @NonNull String title, @NonNull String description, @NonNull String date) {
        mPhoto = photo;
        mUrl = url;
        mTitle = title;
        mDescription = description;
        mDate = date;
    }

    @Nullable
    public String getId() {
        return mId;
    }

    public void setId(@Nullable String id) {
        mId = id;
    }

    @NonNull
    public String getPhoto() {
        return mPhoto;
    }
    
    public void setPhoto(@NonNull String photo) {
        mPhoto = photo;
    }

    @NonNull
    public String getUrl() {
        return mUrl;
    }

    public void setUrl(@NonNull String url) {
        mUrl = url;
    }

    @NonNull
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(@NonNull String title) {
        mTitle = title;
    }

    @NonNull
    public String getDescription() {
        return mDescription;
    }

    public void setDescription(@NonNull String description) {
        mDescription = description;
    }

    @NonNull
    public String getDate() {
        return mDate;
    }

    public void setDate(@NonNull String date) {
        mDate = date;
    }
}
