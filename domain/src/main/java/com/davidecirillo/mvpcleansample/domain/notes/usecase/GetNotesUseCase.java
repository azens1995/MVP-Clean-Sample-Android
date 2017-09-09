package com.davidecirillo.mvpcleansample.domain.notes.usecase;

import com.davidecirillo.mvpcleansample.data.notes.model.NoteDataModel;
import com.davidecirillo.mvpcleansample.data.notes.repository.api.NotesRepository;
import com.davidecirillo.mvpcleansample.domain.common.BaseUseCase;
import com.davidecirillo.mvpcleansample.domain.notes.model.NoteDomainModel;
import com.davidecirillo.mvpcleansample.domain.notes.model.NoteDomainModelMapper;

import java.util.ArrayList;
import java.util.List;

public class GetNotesUseCase extends BaseUseCase<GetNotesUseCase.RequestValues, GetNotesUseCase.ResponseValues> {

    private NotesRepository mNotesRepository;

    public GetNotesUseCase(NotesRepository notesRepository) {
        mNotesRepository = notesRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        ArrayList<NoteDataModel> noteDataModels = mNotesRepository.getNotes();

        // Map list to domain model
        List<NoteDomainModel> domainModels = new ArrayList<>();
        for (NoteDataModel noteDataModel : noteDataModels) {
            domainModels.add(NoteDomainModelMapper.toDomainModel(noteDataModel));
        }

        getUseCaseCallback().onSuccess(new ResponseValues(domainModels));
    }

    public static class RequestValues implements BaseUseCase.RequestValues {

    }

    public static class ResponseValues implements BaseUseCase.ResponseValues {
        private List<NoteDomainModel> mNoteDomainModels;

        public ResponseValues(List<NoteDomainModel> noteDomainModels) {
            mNoteDomainModels = noteDomainModels;
        }

        public List<NoteDomainModel> getNoteDomainModels() {
            return mNoteDomainModels;
        }
    }
}
