package com.davidecirillo.mvpcleansample.domain.notes.model;


import com.davidecirillo.mvpcleansample.data.notes.model.NoteDataModel;

public class NoteDomainModelMapper {

    public static NoteDataModel toDataModel(NoteDomainModel noteDomainModel) {
        return new NoteDataModel(noteDomainModel.getTitle(), noteDomainModel.getText(), noteDomainModel.getCreatedAt());
    }

    public static NoteDomainModel toDomainModel(NoteDataModel noteDataModel) {
        return new NoteDomainModel(noteDataModel.getTitle(), noteDataModel.getText(), noteDataModel.getCreatedAt());
    }
}
