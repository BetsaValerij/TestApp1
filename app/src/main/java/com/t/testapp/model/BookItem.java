package com.t.testapp.model;



public class BookItem {
    int Id;
    String title;
    String url;
    String is_new;
    String thumb_ext;
    String book_ext;

    public BookItem(){}

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIs_new() {
        return is_new;
    }

    public void setIs_new(String is_new) {
        this.is_new = is_new;
    }

    public String getThumb_ext() {
        return thumb_ext;
    }

    public void setThumb_ext(String thumb_ext) {
        this.thumb_ext = thumb_ext;
    }

    public String getBook_ext() {
        return book_ext;
    }

    public void setBook_ext(String book_ext) {
        this.book_ext = book_ext;
    }
}
