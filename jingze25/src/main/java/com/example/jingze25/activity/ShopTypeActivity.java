package com.example.jingze25.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.jingze25.Apis;
import com.example.jingze25.Constants;
import com.example.jingze25.R;
import com.example.jingze25.adapter.ShopTypeAdapter;
import com.example.jingze25.adapter.ShopTypeProductAdapter;
import com.example.jingze25.bean.ShopTypeBean;
import com.example.jingze25.bean.ShopTypeProductBean;
import com.example.jingze25.presenter.ShowPresenter;

import java.util.HashMap;
import java.util.Map;

public class ShopTypeActivity extends AppCompatActivity implements IView {
    private ShowPresenter showPresenter;
    private ShopTypeAdapter mShopTypeAdapter;
    private ShopTypeProductAdapter mShopTypeProductAdapter;
    private RecyclerView mRecyclerViewShopType, mRecyclerViewShop;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_type);
        showPresenter = new ShowPresenter(this);
        initShopTypeView();
        initShopTypeProductView();
        getTypeData();
    }


    /**
     * 初始化左侧recyclerView,加载左侧adapter
     */

    private void initShopTypeView() {
        mRecyclerViewShop = findViewById(R.id.recyclerview_shop_type);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerViewShop.setLayoutManager(linearLayoutManager);
        mRecyclerViewShop.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mShopTypeAdapter = new ShopTypeAdapter(this);
        mRecyclerViewShop.setAdapter(mShopTypeAdapter);

        //添加接口回调，用来接收左侧recyclerView的cid
        mShopTypeAdapter.setOnClickListener(new ShopTypeAdapter.OnClickListener() {
            @Override
            public void onClick(int position, String cid) {
                //拿到cid之后，通过接口获得对应的数据，展示在右侧列表中
                getShopData(cid);
            }
        });

    }


    /**
     * 初始化右侧recyclerView,加载右侧adapter
     */
    private void initShopTypeProductView() {
        mRecyclerViewShopType = findViewById(R.id.recyclerview_shop);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerViewShopType .setLayoutManager(linearLayoutManager);
        mRecyclerViewShopType .addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mShopTypeProductAdapter = new ShopTypeProductAdapter(this);
        mRecyclerViewShopType.setAdapter(mShopTypeProductAdapter);

    }



    private void getShopData(String cid) {
     Map<String,String> map = new HashMap<>();
     map.put(Constants.MAP_KEY_PRODUCT_GET_CATAGORY_CID,cid);
     showPresenter.startRequest(Apis.URL_PRODUCT_GET_PRODUCT_CATAGORY,map,ShopTypeProductBean.class);

    }

    /**
     * 获取左侧列表数据
     */
    private void getTypeData() {
        Map<String,String> map = new HashMap<>();
        showPresenter.startRequest(Apis.URL_PRODUCT_GET_CATAGORY,map,ShopTypeBean.class);


    }

    @Override
    public void showResponseData(Object data) {

        if(data instanceof ShopTypeBean) {
            //获取数据后，展示左侧列表
            ShopTypeBean shopTypeBean = (ShopTypeBean) data;
            mShopTypeAdapter.setData(shopTypeBean.getData());
        }else if(data instanceof ShopTypeProductBean) {
            //获取数据后，展示右侧列表
            ShopTypeProductBean shopTypeProductBean = (ShopTypeProductBean) data;
            mShopTypeProductAdapter.setData(shopTypeProductBean.getData());

            //将右侧列表滚到顶部(这行不加也无所谓)
            mRecyclerViewShopType.scrollToPosition(0);
        }

    }

    @Override
    public void showResponseFail(Object data) {

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        showPresenter.onDetach();
    }
}
