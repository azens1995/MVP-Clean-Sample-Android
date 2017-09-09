package com.davidecirillo.mvpcleansample.presentation.notes.model;

import com.davidecirillo.mvpcleansample.domain.notes.model.NoteDomainModel;

import java.util.Calendar;

public final class NoteViewModelMapper {

    public static NoteViewModel toViewModel(NoteDomainModel noteDomainModel) {
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(noteDomainModel.getCreatedAt());

        return new NoteViewModel(noteDomainModel.getTitle(), noteDomainModel.getText(), cl.getTime());
    }

    public static NoteDomainModel toDomainModel(NoteViewModel noteViewModel) {
        return new NoteDomainModel(noteViewModel.getTitle(), noteViewModel.getText(), noteViewModel.getCreatedAtDate().getTime());
    }
}
