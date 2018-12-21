package com.example.jingze25.activity;

public interface IView<T> {

    void  showResponseData(T data);
    void  showResponseFail(T data);
}
