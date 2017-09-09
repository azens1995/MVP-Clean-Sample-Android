package com.davidecirillo.mvpcleansample.presentation.notes.show;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ViewSwitcher;

import com.davidecirillo.mvpcleansample.R;
import com.davidecirillo.mvpcleansample.domain.notes.usecase.DeleteNoteUseCase;
import com.davidecirillo.mvpcleansample.domain.notes.usecase.GetNotesUseCase;
import com.davidecirillo.mvpcleansample.presentation.notes.add.AddNoteActivity;
import com.davidecirillo.mvpcleansample.presentation.common.BaseActivity;
import com.davidecirillo.mvpcleansample.presentation.notes.show.notelist.NotesRecyclerViewAdapter;
import com.davidecirillo.mvpcleansample.presentation.notes.show.notelist.SimpleItemTouchHelperCallback;
import com.davidecirillo.mvpcleansample.presentation.notes.model.NoteViewModel;

public class NotesActivity extends BaseActivity implements NotesContract.View, NotesRecyclerViewAdapter.NoteChangeObserver {

    private static final int ADD_NOTE_RESULT_CODE = 1234;
    private static final int EMPTY_PLACEHOLDER_POSITION = 0;
    private static final int RECYCLER_VIEW_POSITION = 1;

    private NotesRecyclerViewAdapter mAdapter;
    private Menu mOptionMenu;

    // Views
    private ViewSwitcher mViewSwitcher;
    private RecyclerView mRecyclerView;

    private NotesPresenter mNotesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        createPresenter();

        setupNoteRecyclerView();

        mViewSwitcher = (ViewSwitcher) findViewById(R.id.view_switcher);
    }
    @Override
    protected void onResume() {
        super.onResume();

        mNotesPresenter.populateNotesFromPrefs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_list_menu, menu);
        mOptionMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.switch_view:
                handleSwitchRecyclerViewLayout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void clearNoteList() {
        mAdapter.clearList();
    }

    @Override
    public void showNewNote(NoteViewModel noteViewModel) {
        mAdapter.addNote(noteViewModel);
    }

    @Override
    public void showList() {
        mViewSwitcher.setDisplayedChild(RECYCLER_VIEW_POSITION);
    }

    @Override
    public void showEmptyPlaceholder() {
        mViewSwitcher.setDisplayedChild(EMPTY_PLACEHOLDER_POSITION);
    }

    @Override
    public void onNoteListChanged(int oldItemCount, int newItemCount) {
        mNotesPresenter.showListOrEmptyPlaceholder(oldItemCount, newItemCount);
    }

    @Override
    public void onItemDeleted(NoteViewModel noteViewModel) {
        mNotesPresenter.deleteNoteFromMemory(noteViewModel);
    }

    public void onFabButtonClick(View view) {
        startActivityForResult(AddNoteActivity.getIntent(this), ADD_NOTE_RESULT_CODE);
    }

    private void createPresenter() {
        GetNotesUseCase getNotesUseCase = new GetNotesUseCase(mPrefsNoteRepository);
        DeleteNoteUseCase deleteNoteUseCase = new DeleteNoteUseCase(mPrefsNoteRepository);
        mNotesPresenter = new NotesPresenter(mUseCaseHandler, getNotesUseCase, deleteNoteUseCase);
        bindPresenterToView(mNotesPresenter);
    }

    private void setupNoteRecyclerView() {
        mAdapter = new NotesRecyclerViewAdapter();
        mAdapter.setNoteChangeObserver(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.notes_recycler_view);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

        // Swipe item touch helper
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRecyclerView);
    }

    private void handleSwitchRecyclerViewLayout() {
        // This is not persisted in memory atm
        if (mRecyclerView != null && mOptionMenu != null) {

            int icon;
            if (mRecyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                icon = R.drawable.ic_view_quilt_white_24dp;
            } else {
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
                icon = R.drawable.ic_view_stream_white_24dp;
            }
            mOptionMenu.getItem(0).setIcon(ContextCompat.getDrawable(this, icon));
        }
    }
}
