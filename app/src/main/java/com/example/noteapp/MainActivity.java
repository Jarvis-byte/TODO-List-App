package com.example.noteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
static ArrayList<String> notes=new ArrayList<>();
   static ArrayAdapter arrayAdapter;
Intent intent;
    SharedPreferences  sharedPreferences;


//MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuVar=getMenuInflater();
        menuVar.inflate(R.menu.menu,menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

         if(item.getItemId()==R.id.addNote)
         {
             intent=new Intent(getApplicationContext(),NoteEditorActivity.class);
             startActivity(intent);
             return true;

         }
         return false;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences=getApplication().getSharedPreferences("com.example.noteapp", Context.MODE_PRIVATE);

        ListView listview = findViewById(R.id.listView);
        HashSet<String>set=(HashSet<String>) sharedPreferences.getStringSet("notes",null);
        if(set==null)
        {
            notes.add("Example Notes");

        }else
        {
            notes=new ArrayList<>(set);
        }

         arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,notes);

        listview.setAdapter(arrayAdapter);
listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        intent =new Intent(getApplicationContext(),NoteEditorActivity.class);
         intent.putExtra("noteId",i);
         startActivity(intent);
    }
});
listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

        final int itemToDelete=i;

        new AlertDialog.Builder(MainActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are You Sure?")
                .setMessage("Do You want to delete this note?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        notes.remove(itemToDelete);
                        arrayAdapter.notifyDataSetChanged();


                        HashSet<String> set=new HashSet<>(MainActivity.notes);
                        sharedPreferences.edit().putStringSet("notes",set).apply();
                    }
                })
                .setNegativeButton("No",null)
                .show();

        return true;
    }
});


    }


}