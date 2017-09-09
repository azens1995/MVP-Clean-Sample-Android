package com.davidecirillo.mvpcleansample.presentation.notes.add;


import android.icu.util.Calendar;
import android.support.annotation.NonNull;

import com.davidecirillo.mvpcleansample.domain.notes.usecase.ValidateFieldsUseCase;
import com.davidecirillo.mvpcleansample.domain.common.BaseUseCase;
import com.davidecirillo.mvpcleansample.domain.common.EmptyUseCaseCallback;
import com.davidecirillo.mvpcleansample.domain.common.UseCaseHandler;
import com.davidecirillo.mvpcleansample.domain.notes.model.NoteDomainModel;
import com.davidecirillo.mvpcleansample.domain.notes.usecase.SaveNoteUseCase;
import com.davidecirillo.mvpcleansample.presentation.common.BasePresenter;
import com.davidecirillo.mvpcleansample.presentation.notes.model.NoteViewModel;
import com.davidecirillo.mvpcleansample.presentation.notes.model.NoteViewModelMapper;

class AddNotePresenter extends BasePresenter<AddNoteContract.View> implements AddNoteContract.Presenter {

    private ValidateFieldsUseCase mValidateFieldsUseCase;
    @NonNull private final SaveNoteUseCase mSaveNoteUseCase;

    AddNotePresenter(@NonNull UseCaseHandler useCaseHandler, @NonNull ValidateFieldsUseCase validateFieldsUseCase, @NonNull SaveNoteUseCase saveNoteUseCase) {
        super(useCaseHandler);
        mValidateFieldsUseCase = validateFieldsUseCase;
        mSaveNoteUseCase = saveNoteUseCase;
    }

    @Override
    public void validateFields(final String text, final String title) {
        mUseCaseHandler.execute(mValidateFieldsUseCase,
                new ValidateFieldsUseCase.RequestValues(title, text),
                new BaseUseCase.UseCaseCallback<ValidateFieldsUseCase.ResponseValues>() {


                    @Override
                    public void onSuccess(ValidateFieldsUseCase.ResponseValues response) {
                        NoteViewModel noteViewModel = new NoteViewModel(title, text, Calendar.getInstance().getTime());

                        saveNoteToMemory(noteViewModel);

                        getView().closeSectionWithPositiveResult();
                    }

                    @Override
                    public void onError() {
                        getView().showError("Please complete all the fields");
                    }
                });
    }

    @Override
    public void saveNoteToMemory(NoteViewModel noteViewModel) {
        // Save note to prefs
        NoteDomainModel noteDomainModel = NoteViewModelMapper.toDomainModel(noteViewModel);
        mUseCaseHandler.execute(mSaveNoteUseCase, new SaveNoteUseCase.RequestValues(noteDomainModel), new EmptyUseCaseCallback<SaveNoteUseCase.ResponseValues>());
    }
}
