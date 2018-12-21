package com.example.jingze25.callback;

public interface MyCallBack<T> {

    void success(T data);
    void failed(Exception e);
}
