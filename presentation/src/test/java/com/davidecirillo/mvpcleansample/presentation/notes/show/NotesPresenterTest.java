package com.davidecirillo.mvpcleansample.presentation.notes.show;

import android.util.Log;

import com.davidecirillo.mvpcleansample.domain.common.BaseUseCase;
import com.davidecirillo.mvpcleansample.domain.common.UseCaseHandler;
import com.davidecirillo.mvpcleansample.domain.notes.model.NoteDomainModel;
import com.davidecirillo.mvpcleansample.domain.notes.usecase.DeleteNoteUseCase;
import com.davidecirillo.mvpcleansample.domain.notes.usecase.GetNotesUseCase;
import com.davidecirillo.mvpcleansample.domain.notes.usecase.SaveNoteUseCase;
import com.davidecirillo.mvpcleansample.presentation.notes.model.NoteViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Log.class})
public class NotesPresenterTest {

    @Mock UseCaseHandler mUseCaseHandler;
    @Mock SaveNoteUseCase mSaveNoteUseCase;
    @Mock DeleteNoteUseCase mDeleteNoteUseCase;
    @Mock GetNotesUseCase mGetNotesUseCase;
    @Mock NotesContract.View mView;
    @Mock NoteViewModel mNoteViewModel;

    private NotesPresenter mCut;

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(Log.class);

        given(mNoteViewModel.getCreatedAtDate()).willReturn(mock(Date.class));

        mCut = new NotesPresenter(mUseCaseHandler, mGetNotesUseCase, mDeleteNoteUseCase);
        mCut.bind(mView);
    }

    @Test
    public void testWhenPopulateNotesFromPrefsThenGetNotesUseCaseExecuted() throws Exception {
        // Given
        ArgumentCaptor<BaseUseCase> baseUseCaseArgumentCaptor = ArgumentCaptor.forClass(BaseUseCase.class);

        // When
        mCut.populateNotesFromPrefs();

        // Then
        verify(mUseCaseHandler, times(1)).execute(baseUseCaseArgumentCaptor.capture(), any(BaseUseCase.RequestValues.class), any(BaseUseCase.UseCaseCallback.class));
        assertTrue(baseUseCaseArgumentCaptor.getValue() instanceof GetNotesUseCase);
    }

    @Test
    public void testWhenPopulateNotesFromPrefs() throws Exception {
        // Given
        ArgumentCaptor<BaseUseCase.UseCaseCallback> argumentCaptor = ArgumentCaptor.forClass(BaseUseCase.UseCaseCallback.class);

        // When
        mCut.populateNotesFromPrefs();

        verify(mUseCaseHandler, times(1)).execute(any(BaseUseCase.class), any(BaseUseCase.RequestValues.class), argumentCaptor.capture());
        List<NoteDomainModel> notesFromPrefs = new ArrayList<>();
        notesFromPrefs.add(new NoteDomainModel("1", "1", 1L));
        notesFromPrefs.add(new NoteDomainModel("2", "2", 2L));
        argumentCaptor.getValue().onSuccess(new GetNotesUseCase.ResponseValues(notesFromPrefs));

        // Then
        verify(mView, times(2)).showNewNote(any(NoteViewModel.class));
    }

    @Test
    public void testWhenShowListOfEmptyPlaceholderCalledThenShowListOnView() throws Exception {
        // When
        mCut.showListOrEmptyPlaceholder(9, 1);

        // Then
        verify(mView, times(1)).showList();
    }

    @Test
    public void testWhenShowListOfEmptyPlaceholderCalledThenShoEmptyPlaceholder() throws Exception {
        // When
        mCut.showListOrEmptyPlaceholder(9, 0);

        // Then
        verify(mView, times(1)).showEmptyPlaceholder();
    }

    @Test
    public void testWhenDeleteNoteFromMemoryThenCorrectUseCaseExecuted() throws Exception {
        // Given
        ArgumentCaptor<BaseUseCase> argumentCaptor = ArgumentCaptor.forClass(BaseUseCase.class);

        // When
        mCut.deleteNoteFromMemory(mNoteViewModel);

        // Verify
        verify(mUseCaseHandler, times(1)).execute(argumentCaptor.capture(), any(BaseUseCase.RequestValues.class), any(BaseUseCase.UseCaseCallback.class));
        assertTrue(argumentCaptor.getValue() instanceof DeleteNoteUseCase);
    }
}