package com.example.jingze23.activity;

public interface IView<T> {

    void showResponseData(T data);
    void showResponseFail(T data);

}
