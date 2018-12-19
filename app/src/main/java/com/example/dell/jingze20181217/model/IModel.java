package com.example.dell.jingze20181217.model;

import com.example.dell.jingze20181217.callback.MyCallback;

import java.util.Map;

public interface IModel {

    void requestData(String url, Map<String,String> params, Class clazz, MyCallback callBack);
}
