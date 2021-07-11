package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.todoapp.Activities.add_note;
import com.example.todoapp.DataBase.DatabaseHelper;
import com.example.todoapp.ModalClass.NotesData;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<NotesData> notesData;
    ArrayList<String> title;
    ArrayAdapter adapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.notes);
        DatabaseHelper databaseHelper= new DatabaseHelper(this);
        notesData=databaseHelper.fetch_data();
        title= new ArrayList<>();
        if(!notesData.isEmpty())
        {
            //title= new ArrayList<>();
            for(int i=0;i<notesData.size();i++)
            {
                title.add(notesData.get(i).getTitle());
            }
            adapter= new ArrayAdapter(this, android.R.layout.simple_list_item_1,title);
            listView.setAdapter(adapter);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(getApplicationContext(),add_note.class);
                intent.putExtra("add_update","Update Note");
                intent.putExtra("ID",notesData.get(position).getId());
                intent.putExtra("TITLE",notesData.get(position).getTitle());
                intent.putExtra("DESCR",notesData.get(position).getNotes_descr());
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Are you sure?")
                        .setMessage("You really want to delete")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                databaseHelper.delete_data(notesData.get(position).getId());
                                notesData=databaseHelper.fetch_data();
                                title.remove(position);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No",null).show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.new_note)
        {
            Intent intent= new Intent(getApplicationContext(), add_note.class);
            intent.putExtra("add_update","Add Note");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}