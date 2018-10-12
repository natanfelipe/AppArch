package com.br.natanfelipe.apparch.dao;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.br.natanfelipe.apparch.model.Note;

@Database(entities = Note.class,version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    public static NoteDatabase instance;

    public abstract NoteDao noteDao();


    public static synchronized NoteDatabase getInstance(Context context){

        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),NoteDatabase.class,"note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }

        return instance;
    }

    public static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    public static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{

        private NoteDao noteDao;

        public PopulateDbAsyncTask(NoteDatabase database) {
            this.noteDao = database.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.addNote(new Note("Title 1", "Description 1",1));
            noteDao.addNote(new Note("Title 2", "Description 2",2));
            noteDao.addNote(new Note("Title 3", "Description 3",3));
            return null;
        }
    }
}
