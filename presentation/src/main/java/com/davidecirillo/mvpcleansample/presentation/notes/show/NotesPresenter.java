package com.davidecirillo.mvpcleansample.presentation.notes.show;

import com.davidecirillo.mvpcleansample.domain.common.BaseUseCase;
import com.davidecirillo.mvpcleansample.domain.common.EmptyUseCaseCallback;
import com.davidecirillo.mvpcleansample.domain.common.UseCaseHandler;
import com.davidecirillo.mvpcleansample.domain.notes.model.NoteDomainModel;
import com.davidecirillo.mvpcleansample.domain.notes.usecase.DeleteNoteUseCase;
import com.davidecirillo.mvpcleansample.domain.notes.usecase.GetNotesUseCase;
import com.davidecirillo.mvpcleansample.presentation.common.BasePresenter;
import com.davidecirillo.mvpcleansample.presentation.notes.model.NoteViewModel;
import com.davidecirillo.mvpcleansample.presentation.notes.model.NoteViewModelMapper;

class NotesPresenter extends BasePresenter<NotesContract.View> implements NotesContract.Presenter {

    private final GetNotesUseCase mGetNotesUseCase;
    private final DeleteNoteUseCase mDeleteNoteUseCase;

    NotesPresenter(UseCaseHandler useCaseHandler,
                   GetNotesUseCase getNotesUseCase,
                   DeleteNoteUseCase deleteNoteUseCase) {
        super(useCaseHandler);
        mGetNotesUseCase = getNotesUseCase;
        mDeleteNoteUseCase = deleteNoteUseCase;
    }

    @Override
    public void populateNotesFromPrefs() {
        mUseCaseHandler.execute(mGetNotesUseCase, new GetNotesUseCase.RequestValues(), new BaseUseCase.UseCaseCallback<GetNotesUseCase.ResponseValues>() {

            @Override
            public void onSuccess(GetNotesUseCase.ResponseValues response) {
                getView().clearNoteList();

                // Iterate through the domain models, convert them into view models and show them to the screen
                for (NoteDomainModel noteDomainModel : response.getNoteDomainModels()) {
                    NoteViewModel noteViewModel = NoteViewModelMapper.toViewModel(noteDomainModel);

                    getView().showNewNote(noteViewModel);
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void showListOrEmptyPlaceholder(int oldItemCount, int newItemCount) {
        if (newItemCount > 0) {
            getView().showList();
        } else {
            getView().showEmptyPlaceholder();
        }
    }

    @Override
    public void deleteNoteFromMemory(NoteViewModel noteViewModel) {
        NoteDomainModel noteDomainModel = NoteViewModelMapper.toDomainModel(noteViewModel);
        mUseCaseHandler.execute(mDeleteNoteUseCase, new DeleteNoteUseCase.RequestValues(noteDomainModel), new EmptyUseCaseCallback<DeleteNoteUseCase.ResponseValues>());
    }
}
