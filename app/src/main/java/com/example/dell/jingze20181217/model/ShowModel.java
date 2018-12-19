package com.example.dell.jingze20181217.model;

import com.example.dell.jingze20181217.callback.MyCallback;
import com.example.dell.jingze20181217.util.ICallBack;
import com.example.dell.jingze20181217.util.OkHttpUtils;

import java.util.Map;

public class ShowModel implements IModel {
    @Override
    public void requestData(String url, Map<String, String> params, Class clazz, final MyCallback callBack) {

        OkHttpUtils.getInstance().postEnqueue(url, params, clazz, new ICallBack() {
            @Override
            public void success(Object obj) {
                callBack.setData(obj);
            }

            @Override
            public void failed(Exception e) {
                   callBack.setData(e.getMessage());
            }
        });
    }
}
