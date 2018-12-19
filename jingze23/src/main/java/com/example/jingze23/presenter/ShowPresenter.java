package com.example.jingze23.presenter;

import com.example.jingze23.activity.IView;
import com.example.jingze23.callback.MyCallBack;
import com.example.jingze23.model.IModel;
import com.example.jingze23.model.ShowModel;

import java.util.Map;

public class ShowPresenter implements IPresenter {

    private IModel model;
    private IView iView;

    public ShowPresenter(IView iView) {
        this.iView = iView;
        model = new ShowModel();
    }


    @Override
    public void startRequest(String url, Map<String, String> params, Class clazz) {
        model.requestData(url, params, clazz, new MyCallBack() {
            @Override
            public void success(Object data) {
                iView.showResponseData(data);
            }

            @Override
            public void failed(Exception e) {

                iView.showResponseFail(e);
            }
        });
    }

    public void onDetach(){
        if(model != null){
            model = null;
        }
        if(iView != null){
            iView = null;
        }
    }
}
