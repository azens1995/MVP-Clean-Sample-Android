package com.davidecirillo.mvpcleansample.presentation.common;


interface BasePresenterInterface<T extends BaseView> {

    void bind(T view);

    void unbind();

    T getView();
}
