package com.example.todoapp.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.todoapp.ModalClass.NotesData;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="notes.db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table_query="CREATE TABLE note (id INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR, note_descr VARCHAR)";
        db.execSQL(create_table_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public boolean add_data_inDB(String title, String descr)
    {
        SQLiteDatabase database= this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("title",title);
        cv.put("note_descr",descr);
        long note = database.insert("note", null, cv);
        if(note==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public ArrayList<NotesData> fetch_data()
    {
        ArrayList<NotesData> notesData= new ArrayList<>();
        String query="SELECT * FROM note";
        SQLiteDatabase database=this.getReadableDatabase();
        Cursor c=database.rawQuery(query,null);
        int id_index=c.getColumnIndex("id");
        int title_index=c.getColumnIndex("title");
        int note_descr_index=c.getColumnIndex("note_descr");
        if(c.moveToFirst())
        {
            do {
                NotesData notesData1= new NotesData(c.getInt(id_index),
                        c.getString(title_index),c.getString(note_descr_index));
                notesData.add(notesData1);
            }while(c.moveToNext());
        }
        return notesData;
    }

    public void update_data(int id,String title, String descr)
    {
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("title",title);
        cv.put("note_descr",descr);
        database.update("note",cv,"id=?",new String[]{String.valueOf(id)});
    }

    public void delete_data(int id)
    {
        SQLiteDatabase database= this.getWritableDatabase();
        database.delete("note","id=?", new String[]{String.valueOf(id)});
    }


}
