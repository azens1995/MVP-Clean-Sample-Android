package com.davidecirillo.mvpcleansample.presentation.notes.show;


import com.davidecirillo.mvpcleansample.presentation.common.BaseView;
import com.davidecirillo.mvpcleansample.presentation.notes.model.NoteViewModel;

interface NotesContract {

    interface View extends BaseView {
        void clearNoteList();
        void showNewNote(NoteViewModel noteViewModel);
        void showList();
        void showEmptyPlaceholder();
    }

    interface Presenter {
        void populateNotesFromPrefs();
        void showListOrEmptyPlaceholder(int oldItemCount, int newItemCount);
        void deleteNoteFromMemory(NoteViewModel noteViewModel);
    }

}
