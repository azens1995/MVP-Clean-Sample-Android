package com.davidecirillo.mvpcleansample.domain.notes.usecase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;

@RunWith(PowerMockRunner.class)
public class ValidateFieldsUseCaseTest {

    private ValidateFieldsUseCase mCut;

    @Mock
    private ValidateFieldsUseCase.UseCaseCallback<ValidateFieldsUseCase.ResponseValues> mResponseValueUseCaseCallback;

    @Before
    public void setUp() throws Exception {
        mCut = new ValidateFieldsUseCase();
        mCut.setUseCaseCallback(mResponseValueUseCaseCallback);
    }

    @Test
    public void testGivenEmptyTextWhenExecuteUseCaseThenOnError() throws Exception {
        ValidateFieldsUseCase.RequestValues requestValues = mock(ValidateFieldsUseCase.RequestValues.class);
        given(requestValues.getText()).willReturn("");
        given(requestValues.getTitle()).willReturn("Valid");

        // When
        mCut.executeUseCase(requestValues);

        // Then
        verify(mResponseValueUseCaseCallback, times(1)).onError();
    }

    @Test
    public void testGivenNullTextWhenExecuteUseCaseThenOnError() throws Exception {
        ValidateFieldsUseCase.RequestValues requestValues = mock(ValidateFieldsUseCase.RequestValues.class);
        given(requestValues.getText()).willReturn(null);
        given(requestValues.getTitle()).willReturn("Valid");

        // When
        mCut.executeUseCase(requestValues);

        // Then
        verify(mResponseValueUseCaseCallback, times(1)).onError();
    }

    @Test
    public void testGivenValidTextWhenExecuteUseCaseThenOnSuccess() throws Exception {
        ValidateFieldsUseCase.RequestValues requestValues = mock(ValidateFieldsUseCase.RequestValues.class);
        given(requestValues.getText()).willReturn("Valid text");
        given(requestValues.getTitle()).willReturn("Valid");

        // When
        mCut.executeUseCase(requestValues);

        // Then
        verify(mResponseValueUseCaseCallback, times(1)).onSuccess(any(ValidateFieldsUseCase.ResponseValues.class));
    }

    @Test
    public void testGivenEmptyTitleWhenExecuteUseCaseThenOnError() throws Exception {
        ValidateFieldsUseCase.RequestValues requestValues = mock(ValidateFieldsUseCase.RequestValues.class);
        given(requestValues.getTitle()).willReturn("");
        given(requestValues.getText()).willReturn("Valid text");

        // When
        mCut.executeUseCase(requestValues);

        // Then
        verify(mResponseValueUseCaseCallback, times(1)).onError();
    }

    @Test
    public void testGivenNullTitleWhenExecuteUseCaseThenOnError() throws Exception {
        ValidateFieldsUseCase.RequestValues requestValues = mock(ValidateFieldsUseCase.RequestValues.class);
        given(requestValues.getTitle()).willReturn(null);
        given(requestValues.getText()).willReturn("Valid text");

        // When
        mCut.executeUseCase(requestValues);

        // Then
        verify(mResponseValueUseCaseCallback, times(1)).onError();
    }

    @Test
    public void testGivenValidTitleWhenExecuteUseCaseThenOnSuccess() throws Exception {
        ValidateFieldsUseCase.RequestValues requestValues = mock(ValidateFieldsUseCase.RequestValues.class);
        given(requestValues.getTitle()).willReturn("Valid text");
        given(requestValues.getText()).willReturn("Valid text");

        // When
        mCut.executeUseCase(requestValues);

        // Then
        verify(mResponseValueUseCaseCallback, times(1)).onSuccess(any(ValidateFieldsUseCase.ResponseValues.class));
    }
}