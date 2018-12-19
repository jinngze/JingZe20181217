package com.example.jingze24.activity;

public interface IView<T> {

    void showResponseData(T data);
    void showResponseFail(T data);
}
