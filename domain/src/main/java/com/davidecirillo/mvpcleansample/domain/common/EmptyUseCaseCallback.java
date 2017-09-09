package com.davidecirillo.mvpcleansample.domain.common;


public class EmptyUseCaseCallback<T extends BaseUseCase.ResponseValues> implements BaseUseCase.UseCaseCallback<T> {

    @Override
    public void onSuccess(BaseUseCase.ResponseValues response) {

    }

    @Override
    public void onError() {

    }
}
