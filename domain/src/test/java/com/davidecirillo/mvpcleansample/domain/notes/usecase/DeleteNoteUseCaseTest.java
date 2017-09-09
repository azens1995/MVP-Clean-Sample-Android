package com.davidecirillo.mvpcleansample.domain.notes.usecase;

import com.davidecirillo.mvpcleansample.data.notes.model.NoteDataModel;
import com.davidecirillo.mvpcleansample.data.notes.repository.api.NotesRepository;
import com.davidecirillo.mvpcleansample.domain.common.BaseUseCase;
import com.davidecirillo.mvpcleansample.domain.notes.model.NoteDomainModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;

@RunWith(PowerMockRunner.class)
public class DeleteNoteUseCaseTest {

    private DeleteNoteUseCase mCut;

    @Mock
    private NotesRepository mNotesRepository;

    @Mock
    private BaseUseCase.UseCaseCallback<DeleteNoteUseCase.ResponseValues> mResponseValueUseCaseCallback;

    @Before
    public void setUp() throws Exception {
        mCut = new DeleteNoteUseCase(mNotesRepository);
        mCut.setUseCaseCallback(mResponseValueUseCaseCallback);
    }

    @Test
    public void testGivenExpectedNoteWhenExecuteUseCaseThenDeleteNoteCalledOnRepository() throws Exception {
        // Given
        NoteDomainModel noteDomainModel = mock(NoteDomainModel.class);
        DeleteNoteUseCase.RequestValues requestValues = new DeleteNoteUseCase.RequestValues(noteDomainModel);

        // When
        mCut.executeUseCase(requestValues);

        // Then
        verify(mNotesRepository, times(1)).deleteNote(any(NoteDataModel.class));
    }

    @Test
    public void testGivenExpectedNoteWhenExecuteUseCaseThenCallbackSuccessCalled() throws Exception {
        // Given
        NoteDomainModel noteDomainModel = mock(NoteDomainModel.class);
        DeleteNoteUseCase.RequestValues requestValues = new DeleteNoteUseCase.RequestValues(noteDomainModel);

        // When
        mCut.executeUseCase(requestValues);

        // Then
        verify(mResponseValueUseCaseCallback, times(1)).onSuccess(any(DeleteNoteUseCase.ResponseValues.class));
    }
}