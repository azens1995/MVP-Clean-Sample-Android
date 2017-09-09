package com.davidecirillo.mvpcleansample.presentation.notes.show;

import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class NotesActivityTest {

    @Rule
    public ActivityTestRule<NotesActivity> mActivityTestRule = new ActivityTestRule<>(NotesActivity.class, true, false);

    private NotesRobot mNotesRobot;

    @Before
    public void setUp() throws Exception {
        mNotesRobot = new NotesRobot(mActivityTestRule);
        mNotesRobot.setUp();
    }

    @After
    public void tearDown() throws Exception {
        mNotesRobot.tearDown();
    }

    @Test
    public void givenEmptyPreferencesList_whenLoadActivity_thenEmptyMessageShown() throws Exception {
        mNotesRobot
            // Given
            .clearNotesList()
            // When
            .showView()
            // Then
            .isEmptyPlaceholderDisplayed();
    }

    @Test
    public void givenTwoItemsInPrefs_whenLoadActivity_thenTwoItemDisplayedWithCorrectInfo() throws Exception {
        mNotesRobot
            // Given
            .addNoteToPrefs("title1", "text1", 5L)
            .addNoteToPrefs("title2", "text2", 10L)
            // When
            .showView()
            // Then
            .isNoteDisplayedAtPositionWithTitleAndContent(0, "title1", "text1")
            .isNoteDisplayedAtPositionWithTitleAndContent(1, "title2", "text2");
    }

    @Test
    public void givenSomeNotesInList_whenSwipeToRemoveOneNote_thenNoteRemoved() throws Exception {
        mNotesRobot
            // Given
            .addNoteToPrefs("title1", "text1", 5L)
            .addNoteToPrefs("title2", "text2", 10L)
            .addNoteToPrefs("title3", "text3", 15L)
            .addNoteToPrefs("title4", "text4", 16L)
            .showView()
            // When
            .swipeToRemoveAtPosition(1)
            // Then
            .isNoteDisplayedAtPositionWithTitleAndContent(0, "title1", "text1")
            .isNoteDisplayedAtPositionWithTitleAndContent(1, "title3", "text3")
            .isNoteDisplayedAtPositionWithTitleAndContent(2, "title4", "text4");
    }

    @Test
    public void givenOneNoteInList_whenRemoveNote_thenEmptyPlaceholderShown() throws Exception {
        mNotesRobot
            // Given
            .addNoteToPrefs("test", "test", 5L)
            .showView()
            // When
            .swipeToRemoveAtPosition(0)
            // Then
            .isEmptyPlaceholderDisplayed();
    }
}