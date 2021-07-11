package com.example.todoapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todoapp.DataBase.DatabaseHelper;
import com.example.todoapp.MainActivity;
import com.example.todoapp.R;

public class add_note extends AppCompatActivity {

    EditText title, note_descr;
    Button button;
    String flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        init();
        Intent intent= getIntent();
        flag=intent.getStringExtra("add_update");
        button.setText(flag);
        int notes_id=intent.getIntExtra("ID",-1);

        if(flag.equals("Update Note"))
        {
            title.setText(intent.getStringExtra("TITLE"));
            note_descr.setText(intent.getStringExtra("DESCR"));
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag.equals("Add Note"))
                {
                    if(TextUtils.isEmpty(title.getText().toString()) ||
                            TextUtils.isEmpty(note_descr.getText().toString()))
                    {
                        Toast.makeText(getApplicationContext(),"Notes title and description can not be empty",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        //insert data in the data base
                        DatabaseHelper databaseHelper= new DatabaseHelper(getApplicationContext());
                        if(databaseHelper.add_data_inDB(title.getText().toString(),
                                note_descr.getText().toString()))
                        {
                            Toast.makeText(getApplicationContext(),"Data inserted in database",Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Data not inserted in db",Toast.LENGTH_LONG).show();
                        }

                    }
                }
                else if(flag.equals("Update Note"))
                {
                    if(TextUtils.isEmpty(title.getText().toString()) ||
                            TextUtils.isEmpty(note_descr.getText().toString()))
                    {
                        Toast.makeText(getApplicationContext(),"Notes title and description can not be empty",Toast.LENGTH_LONG).show();
                    }
                    else {
                        //Toast.makeText(getApplicationContext(), "Will update note", Toast.LENGTH_LONG).show();
                        DatabaseHelper databaseHelper= new DatabaseHelper(getApplicationContext());
                        databaseHelper.update_data(notes_id,title.getText().toString(),note_descr.getText().toString());
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private void init()
    {
        title=findViewById(R.id.title);
        note_descr=findViewById(R.id.note_descr);
        button=findViewById(R.id.add_update);
    }

}