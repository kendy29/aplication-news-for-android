package com.seputar.berita;

public class Model {

    String title;
    String text;
    String photo;
    String name;

    public Model(){}
    public Model(String title, String text, String photo, String name) {
        this.title = title;
        this.text = text;
        this.photo = photo;
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
