package com.davidecirillo.mvpcleansample.data.notes.repository.preferences;

import android.content.Context;

import com.davidecirillo.mvpcleansample.data.R;
import com.davidecirillo.mvpcleansample.data.notes.model.NoteDataModel;
import com.davidecirillo.mvpcleansample.data.notes.repository.api.NotesRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PreferenceNotesRepository implements NotesRepository {

    private Gson mGson;
    private Type mNoteListType;
    private Context mContext;

    public PreferenceNotesRepository(Context context) {
        mContext = context;
        mGson = new Gson();
        mNoteListType = new TypeToken<ArrayList<NoteDataModel>>() {
        }.getType();
    }

    @Override
    public void saveNote(NoteDataModel noteDataModel) {
        ArrayList<NoteDataModel> noteDataModels = getNotes();
        noteDataModels.add(noteDataModel);
        Prefs.savePreference(mContext, R.string.prefs_note_list, mGson.toJson(noteDataModels, mNoteListType));
    }

    @Override
    public void deleteNote(NoteDataModel noteDataModel) {
        ArrayList<NoteDataModel> noteDataModels = getNotes();
        noteDataModels.remove(noteDataModel);
        Prefs.savePreference(mContext, R.string.prefs_note_list, mGson.toJson(noteDataModels, mNoteListType));
    }

    @Override
    public ArrayList<NoteDataModel> getNotes() {
        String stringPreference = Prefs.getStringPreference(mContext, R.string.prefs_note_list);
        ArrayList<NoteDataModel> noteDataModels = mGson.fromJson(stringPreference, mNoteListType);

        if (noteDataModels == null) {
            noteDataModels = new ArrayList<>();
        }
        return noteDataModels;
    }
}
