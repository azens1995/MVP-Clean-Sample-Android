package com.davidecirillo.mvpcleansample.domain.notes.usecase;

import com.davidecirillo.mvpcleansample.data.notes.model.NoteDataModel;
import com.davidecirillo.mvpcleansample.data.notes.repository.api.NotesRepository;
import com.davidecirillo.mvpcleansample.domain.common.BaseUseCase;
import com.davidecirillo.mvpcleansample.domain.notes.model.NoteDomainModel;
import com.davidecirillo.mvpcleansample.domain.notes.model.NoteDomainModelMapper;

public class DeleteNoteUseCase extends BaseUseCase<DeleteNoteUseCase.RequestValues, DeleteNoteUseCase.ResponseValues> {

    private NotesRepository mNotesRepository;

    public DeleteNoteUseCase(NotesRepository notesRepository) {
        mNotesRepository = notesRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        // Get input value
        NoteDomainModel noteDomainModel = requestValues.getNoteDomainModel();

        // Map to data model
        NoteDataModel noteDataModel = NoteDomainModelMapper.toDataModel(noteDomainModel);
        mNotesRepository.deleteNote(noteDataModel);

        getUseCaseCallback().onSuccess(new ResponseValues());
    }

    public static class RequestValues implements BaseUseCase.RequestValues {
        private NoteDomainModel mNoteDomainModel;

        public RequestValues(NoteDomainModel noteDomainModel) {
            mNoteDomainModel = noteDomainModel;
        }

        public NoteDomainModel getNoteDomainModel() {
            return mNoteDomainModel;
        }
    }

    public static class ResponseValues implements BaseUseCase.ResponseValues {

    }
}
