package com.br.natanfelipe.apparch.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.br.natanfelipe.apparch.ui.adapter.NoteAdapter;
import com.br.natanfelipe.apparch.vm.NotesViewModel;
import com.br.natanfelipe.apparch.R;
import com.br.natanfelipe.apparch.model.Note;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NotesViewModel notesViewModel;

    RecyclerView recyclerView;

    NoteAdapter noteAdapter = new NoteAdapter();


    public static final int ADD_NOTE = 1;
    public static final int EDIT_NOTE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CalendarActivity.class);
                startActivityForResult(i, ADD_NOTE);
            }
        });

        recyclerView.setAdapter(noteAdapter);


        //THIS BLOCK HANDLE THE DELETE VIA SWIPE TO RIGHT
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //CALL VIEWMODEL DELETE METHOD TO REMOVE THE RECYCLERVIEW ITEM
                notesViewModel.delete(noteAdapter.getNoteAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);//ATTACH THIS ACTION TO RECYCLERVIEW

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        //DEFINE AN OBSERVER TO UPDATE RECYCLERVIEW ADAPTER
        notesViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                noteAdapter.setNotes(notes);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all:
                notesViewModel.deleteAll();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
