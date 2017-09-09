package com.davidecirillo.mvpcleansample.data;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.davidecirillo.mvpcleansample.data.notes.model.NoteDataModel;
import com.davidecirillo.mvpcleansample.data.notes.repository.api.NotesRepository;
import com.davidecirillo.mvpcleansample.data.notes.repository.preferences.PreferenceNotesRepository;
import com.davidecirillo.mvpcleansample.data.notes.repository.preferences.Prefs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

public class PreferenceNotesRepositoryTest {

    private NotesRepository mCut;
    private Context mContext;

    @Before
    public void setUp() throws Exception {
        mContext = InstrumentationRegistry.getTargetContext();
        mCut = new PreferenceNotesRepository(mContext);
    }

    @After
    public void tearDown() throws Exception {
        Prefs.clearPreferencesSync(mContext);
    }

    @Test
    public void testWhenSaveNoteThenNoteSalvedInPrefs() throws Exception {
        // When
        String expectedText = "Test";
        String expectedTitle = "Title";
        Long expectedTimestamp = 5L;
        mCut.saveNote(new NoteDataModel(expectedTitle, expectedText, expectedTimestamp));

        // Then
        ArrayList<NoteDataModel> noteDomainModels = mCut.getNotes();
        assertEquals(1, noteDomainModels.size());
        assertEquals(expectedText, noteDomainModels.get(0).getText());
    }

    @Test
    public void testGivenOneNoteSavedWhenGetNotesThenSizeAndContentCorrectReturned() throws Exception {
        // Given
        String expectedText = "Test";
        String expectedTitle = "Title";
        Long expectedTimestamp = 5L;
        mCut.saveNote(new NoteDataModel(expectedTitle, expectedText, expectedTimestamp));

        // When
        ArrayList<NoteDataModel> noteDomainModels = mCut.getNotes();

        // Then
        assertEquals(1, noteDomainModels.size());
        assertEquals(expectedText, noteDomainModels.get(0).getText());
    }

    @Test
    public void testGivenTwoNotesAddedWhenDeleteFirstNoteThenOnlySecondNoteInMemory() throws Exception {
        // Given
        NoteDataModel noteDataModel = new NoteDataModel("Test", "Test", 0L);
        NoteDataModel noteDataModel1 = new NoteDataModel("Test1", "Test1", 1L);
        mCut.saveNote(noteDataModel);
        mCut.saveNote(noteDataModel1);
        assertEquals(2, mCut.getNotes().size());

        // When
        mCut.deleteNote(noteDataModel);

        // Then
        assertEquals(1, mCut.getNotes().size());
        assertEquals(noteDataModel1, mCut.getNotes().get(0));
    }
}