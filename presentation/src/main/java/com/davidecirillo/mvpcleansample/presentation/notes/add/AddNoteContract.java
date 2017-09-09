package com.davidecirillo.mvpcleansample.presentation.notes.add;


import com.davidecirillo.mvpcleansample.presentation.common.BaseView;
import com.davidecirillo.mvpcleansample.presentation.notes.model.NoteViewModel;

interface AddNoteContract {

    interface View extends BaseView {
        void closeSectionWithPositiveResult();
        void showError(String error);
    }

    interface Presenter {
        void validateFields(String text, String title);
        void saveNoteToMemory(NoteViewModel noteViewModel);
    }
}
