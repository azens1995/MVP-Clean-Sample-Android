package com.davidecirillo.mvpcleansample.domain.notes.usecase;

import com.davidecirillo.mvpcleansample.data.notes.model.NoteDataModel;
import com.davidecirillo.mvpcleansample.data.notes.repository.api.NotesRepository;
import com.davidecirillo.mvpcleansample.domain.common.BaseUseCase;
import com.davidecirillo.mvpcleansample.domain.notes.model.NoteDomainModel;
import com.davidecirillo.mvpcleansample.domain.notes.model.NoteDomainModelMapper;

public class SaveNoteUseCase extends BaseUseCase<SaveNoteUseCase.RequestValues, SaveNoteUseCase.ResponseValues> {

    private NotesRepository mNotesRepository;

    public SaveNoteUseCase(NotesRepository notesRepository) {
        mNotesRepository = notesRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        NoteDomainModel noteDomainModel = requestValues.getNoteDomainModel();

        NoteDataModel noteDataModel = NoteDomainModelMapper.toDataModel(noteDomainModel);

        mNotesRepository.saveNote(noteDataModel);

        getUseCaseCallback().onSuccess(new ResponseValues());
    }

    public static class RequestValues implements BaseUseCase.RequestValues {

        private NoteDomainModel mNoteDomainModel;

        public RequestValues(NoteDomainModel noteDomainModel) {
            mNoteDomainModel = noteDomainModel;
        }

        NoteDomainModel getNoteDomainModel() {
            return mNoteDomainModel;
        }
    }

    public static class ResponseValues implements BaseUseCase.ResponseValues {

    }
}
