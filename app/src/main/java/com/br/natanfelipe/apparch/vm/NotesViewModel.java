package com.br.natanfelipe.apparch.vm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.br.natanfelipe.apparch.model.Note;
import com.br.natanfelipe.apparch.repository.NoteRepository;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    private NoteRepository repository;
    private LiveData<List<Note>> allNotes;

    public NotesViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
    }


    public void delete(Note note){
        repository.delete(note);
    }

    public void deleteAll(){
        repository.deleteAllNotes();
    }

    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }
}
