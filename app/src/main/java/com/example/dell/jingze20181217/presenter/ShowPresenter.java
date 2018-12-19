package com.example.dell.jingze20181217.presenter;

import com.example.dell.jingze20181217.callback.MyCallback;
import com.example.dell.jingze20181217.model.ShowModel;
import com.example.dell.jingze20181217.view.IView;


import java.util.Map;

public class ShowPresenter implements IPresenter {

    private ShowModel model;
    private IView iView;

    public ShowPresenter(IView iView) {
        this.iView = iView;
        model = new ShowModel();
    }

    @Override
    public void startRequest(String url, Map<String, String> params, Class clazz) {

        model.requestData(url, params, clazz, new MyCallback() {
            @Override
            public void setData(Object data) {

                iView.showResponseData(data);
            }
        });

    }

    //防止内存泄漏
    public void onDetach(){
        if(model != null){
            model = null;
        }

        if(iView != null){
            iView = null;
        }
    }

}
