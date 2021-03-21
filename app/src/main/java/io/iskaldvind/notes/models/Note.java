package io.iskaldvind.notes.models;

import java.io.Serializable;

public class Note implements Serializable {
    
    private String title;
    private String description;
    private String date;
    private String photo;
    private String url;
    
    
    public Note(String title, String description, String date) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.photo = null;
        this.url = null;
    }
    
    
    public String getTitle() {
        return title;
    }
    

    public String getDescription() {
        return description;
    }

 
    public String getDate() {
        return date;
    }

    public String getPhoto() {
        return photo;
    }

    public String getUrl() {
        return url;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
