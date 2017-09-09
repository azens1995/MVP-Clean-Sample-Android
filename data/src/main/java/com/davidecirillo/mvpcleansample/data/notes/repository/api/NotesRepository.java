package com.davidecirillo.mvpcleansample.data.notes.repository.api;

import com.davidecirillo.mvpcleansample.data.notes.model.NoteDataModel;

import java.util.ArrayList;

public interface NotesRepository {

    void saveNote(NoteDataModel noteDomainModel);

    void deleteNote(NoteDataModel noteDomainModel);

    ArrayList<NoteDataModel> getNotes();
}
