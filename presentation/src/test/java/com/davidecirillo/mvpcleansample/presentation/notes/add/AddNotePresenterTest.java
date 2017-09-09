package com.davidecirillo.mvpcleansample.presentation.notes.add;

import android.icu.util.Calendar;
import android.util.Log;

import com.davidecirillo.mvpcleansample.domain.common.BaseUseCase;
import com.davidecirillo.mvpcleansample.domain.common.UseCaseHandler;
import com.davidecirillo.mvpcleansample.domain.notes.usecase.SaveNoteUseCase;
import com.davidecirillo.mvpcleansample.domain.notes.usecase.ValidateFieldsUseCase;
import com.davidecirillo.mvpcleansample.presentation.notes.model.NoteViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Date;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Log.class, Calendar.class})
public class AddNotePresenterTest {

    public static final String VALIDATION_ERROR_MESSAGE = "Please complete all the fields";
    private AddNotePresenter mCut;

    @Mock private UseCaseHandler mUseCaseHandler;
    @Mock private ValidateFieldsUseCase mValidateFieldsUseCase;
    @Mock private SaveNoteUseCase mSaveNoteUseCase;
    @Mock private AddNoteContract.View mView;
    @Mock private Calendar mCalendar;

    @Before
    public void setUp() throws Exception {
        mockStatic(Log.class, Calendar.class);
        given(Calendar.getInstance()).willReturn(mCalendar);
        given(mCalendar.getTime()).willReturn(mock(Date.class));

        mCut = new AddNotePresenter(mUseCaseHandler, mValidateFieldsUseCase, mSaveNoteUseCase);
        mCut.bind(mView);
    }

    @Test
    public void testWhenValidateFieldsThenCorrectUseCaseExecuted() throws Exception {
        // When
        mCut.validateFields("", "");

        //Then
        verify(mUseCaseHandler, times(1)).execute(
                eq(mValidateFieldsUseCase),
                any(ValidateFieldsUseCase.RequestValues.class),
                any(BaseUseCase.UseCaseCallback.class));
    }

    @Test
    public void testGivenValidateFieldsWhenUseCaseSuccessThenSubmitResults() throws Exception {
        ArgumentCaptor<BaseUseCase.UseCaseCallback> argumentCaptor = ArgumentCaptor.forClass(BaseUseCase.UseCaseCallback.class);

        // Given
        mCut.validateFields("", "");

        // When
        verify(mUseCaseHandler, times(1)).execute(
            eq(mValidateFieldsUseCase),
            any(ValidateFieldsUseCase.RequestValues.class),
            argumentCaptor.capture());
        argumentCaptor.getValue().onSuccess(mock(ValidateFieldsUseCase.ResponseValues.class));

        //Then
        verify(mView, times(1)).closeSectionWithPositiveResult();
    }

    @Test
    public void testGivenValidateFieldsWhenUseCaseErrorThenShowError() throws Exception {
        ArgumentCaptor<BaseUseCase.UseCaseCallback> argumentCaptor = ArgumentCaptor.forClass(BaseUseCase.UseCaseCallback.class);

        // When
        mCut.validateFields("", "");

        //Then
        verify(mUseCaseHandler, times(1)).execute(
                eq(mValidateFieldsUseCase),
                any(ValidateFieldsUseCase.RequestValues.class),
                argumentCaptor.capture());
        argumentCaptor.getValue().onError();
        verify(mView, times(1)).showError(VALIDATION_ERROR_MESSAGE);
    }

    @Test
    public void testGivenValidateFieldsWhenUseCaseOnSuccessCurrentTimeUsed() throws Exception {
        ArgumentCaptor<BaseUseCase.UseCaseCallback> argumentCaptor = ArgumentCaptor.forClass(BaseUseCase.UseCaseCallback.class);

        // When
        mCut.validateFields("", "");

        //Then
        verify(mUseCaseHandler, times(1)).execute(
                eq(mValidateFieldsUseCase),
                any(ValidateFieldsUseCase.RequestValues.class),
                argumentCaptor.capture());
        argumentCaptor.getValue().onSuccess(mock(ValidateFieldsUseCase.ResponseValues.class));

        verify(mCalendar, times(1)).getTime();
    }

    @Test
    public void testWhenSaveNoteToMemoryThenUseCaseExecuted() throws Exception {
        NoteViewModel noteViewModel = mock(NoteViewModel.class);
        given(noteViewModel.getCreatedAtDate()).willReturn(PowerMockito.mock(Date.class));

        // When
        mCut.saveNoteToMemory(noteViewModel);

        // Then
        verify(mUseCaseHandler, times(1)).execute(
            eq(mSaveNoteUseCase),
            any(SaveNoteUseCase.RequestValues.class),
            any(BaseUseCase.UseCaseCallback.class));

    }
}