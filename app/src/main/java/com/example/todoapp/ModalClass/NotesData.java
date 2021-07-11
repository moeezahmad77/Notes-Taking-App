package com.example.todoapp.ModalClass;

public class NotesData {
    int id;
    String title, notes_descr;

    public NotesData() {
    }

    public NotesData(int id, String title, String notes_descr) {
        this.id = id;
        this.title = title;
        this.notes_descr = notes_descr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes_descr() {
        return notes_descr;
    }

    public void setNotes_descr(String notes_descr) {
        this.notes_descr = notes_descr;
    }
}
