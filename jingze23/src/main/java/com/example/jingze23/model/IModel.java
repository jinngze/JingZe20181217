package com.example.jingze23.model;

import com.example.jingze23.callback.MyCallBack;

import java.util.Map;

public interface IModel {

    void  requestData(String url, Map<String,String> params, Class clazz, MyCallBack callBack);

}
