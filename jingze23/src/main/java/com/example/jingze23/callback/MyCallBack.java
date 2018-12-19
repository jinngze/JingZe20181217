package com.example.jingze23.callback;

public interface MyCallBack<T> {

    void success(T data);
    void failed(Exception e);

}
