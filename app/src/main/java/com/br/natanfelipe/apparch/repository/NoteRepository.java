package com.br.natanfelipe.apparch.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.br.natanfelipe.apparch.model.Note;
import com.br.natanfelipe.apparch.dao.NoteDao;
import com.br.natanfelipe.apparch.dao.NoteDatabase;

import java.util.List;

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application) {
        NoteDatabase noteDatabase = NoteDatabase.getInstance(application);
        noteDao = noteDatabase.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    public void insert(Note note){
        new insertAsyncTask(noteDao).execute(note);
    }
    public void update(Note note){
        new updateAsyncTask(noteDao).execute(note);
    }
    public void delete(Note note){
        new deleteAsyncTask(noteDao).execute(note);
    }
    public void deleteAllNotes(){
        new deleteAllNotesAsyncTask(noteDao).execute();
    }
    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }


    public static class insertAsyncTask extends AsyncTask<Note, Void, Void>{

        private NoteDao noteDao;

        public insertAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.addNote(notes[0]);
            return null;
        }
    }
    public static class updateAsyncTask extends AsyncTask<Note, Void, Void>{

        private NoteDao noteDao;

        public updateAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.updateNote(notes[0]);
            return null;
        }
    }
    public static class deleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void>{

        private NoteDao noteDao;

        public deleteAllNotesAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }
    public static class deleteAsyncTask extends AsyncTask<Note, Void, Void>{

        private NoteDao noteDao;

        public deleteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.deleteNote(notes[0]);
            return null;
        }
    }
}
