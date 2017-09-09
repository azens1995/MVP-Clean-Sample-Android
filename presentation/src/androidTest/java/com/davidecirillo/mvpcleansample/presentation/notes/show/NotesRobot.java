package com.davidecirillo.mvpcleansample.presentation.notes.show;

import android.content.Context;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import com.davidecirillo.mvpcleansample.R;
import com.davidecirillo.mvpcleansample.data.notes.model.NoteDataModel;
import com.davidecirillo.mvpcleansample.data.notes.repository.preferences.PreferenceNotesRepository;
import com.davidecirillo.mvpcleansample.data.notes.repository.preferences.Prefs;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.davidecirillo.mvpcleansample.presentation.common.recyclerview.TestUtils.withRecyclerView;
import static org.hamcrest.Matchers.not;

class NotesRobot {

    private final Intent mNotesActivityIntent;
    private final Context mContext;
    private final PreferenceNotesRepository mNotesRepository;
    private ActivityTestRule<NotesActivity> mActivityTestRule;

    NotesRobot(final ActivityTestRule<NotesActivity> activityTestRule) {
        mActivityTestRule = activityTestRule;
        mContext = getInstrumentation().getTargetContext();
        mNotesActivityIntent = new Intent(mContext, NotesActivity.class);
        mNotesRepository = new PreferenceNotesRepository(mContext);
    }

    public void setUp() {

    }

    public void tearDown() {
        clearNotesList();
    }

    /* Actions */

    NotesRobot clearNotesList(){
        Prefs.clearPreferences(mContext);
        return this;
    }

    NotesRobot showView() {
        mActivityTestRule.launchActivity(mNotesActivityIntent);
        return this;
    }

    NotesRobot addNoteToPrefs(final String title, final String text, final long createdAt) {
        mNotesRepository.saveNote(new NoteDataModel(title, text, createdAt));
        return this;
    }

    NotesRobot swipeToRemoveAtPosition(int position){
        onView(withRecyclerView(R.id.notes_recycler_view).atPosition(position))
            .perform(swipeRight());
        return this;
    }

    /* Assertions */

    NotesRobot isEmptyPlaceholderDisplayed() {
        onView(withId(R.id.empty_label)).check(matches(isDisplayed()));
        return this;
    }

    NotesRobot isEmptyPlaceholderNotDisplayed() {
        onView(withId(R.id.empty_label)).check(matches(not(isDisplayed())));
        return this;
    }

    NotesRobot isNoteDisplayedAtPositionWithTitleAndContent(final int positionInList, final String title, final String text) {
        onView(withRecyclerView(R.id.notes_recycler_view).atPositionOnView(positionInList, R.id.title))
            .check(matches(withText(title)));
        onView(withRecyclerView(R.id.notes_recycler_view).atPositionOnView(positionInList, R.id.content))
            .check(matches(withText(text)));
        return this;
    }

    NotesRobot isNoteNotDisplayedAtPositionWithTitleAndContent(final int positionInList, final String title, final String text) {
        onView(withRecyclerView(R.id.notes_recycler_view).atPositionOnView(positionInList, R.id.title))
            .check(matches(not(withText(title))));
        onView(withRecyclerView(R.id.notes_recycler_view).atPositionOnView(positionInList, R.id.content))
            .check(matches(not(withText(text))));
        return this;
    }}
