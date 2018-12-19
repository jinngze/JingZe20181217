package com.example.jingze24.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.jingze24.Apis;
import com.example.jingze24.Constants;
import com.example.jingze24.R;
import com.example.jingze24.adapter.ShopAdapter;
import com.example.jingze24.bean.ShopBean;
import com.example.jingze24.presenter.ShowPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopCarActivity extends AppCompatActivity implements IView,View.OnClickListener {

    private ShopAdapter mshopAdapter;
    private CheckBox mIvCircle;
    private List<ShopBean.DataBean> mList = new ArrayList<>();
    private TextView mAllPriceTxt, nSumPrice;
    private ShowPresenter mshowPresenter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

      mshowPresenter = new ShowPresenter(this);
        initView();
        getData();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mshowPresenter.onDetach();
    }



    private void initView() {
        mIvCircle = findViewById(R.id.iv_cricle);
        mAllPriceTxt = findViewById(R.id.all_price);
        nSumPrice = findViewById(R.id.sum_price_txt);
        mIvCircle.setOnClickListener(this);


        mRecyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mshopAdapter = new ShopAdapter(this);
        mRecyclerView.setAdapter(mshopAdapter);

        mshopAdapter.setListener(new ShopAdapter.ShopCallBackListener() {
            @Override
            public void callBack(List<ShopBean.DataBean> list) {
                //在这里重新遍历已经改状态后的数据，
                // 这里不能break跳出，因为还需要计算后面点击商品的价格和数目，所以必须跑完整个循环
                double totalPrice = 0;

                //勾选商品的数量，不是该商品购买的数量
                int num = 0;
                //所有商品总数，和上面的数量做比对，如果两者相等，则说明全选
                int totalNum = 0;
                for (int a = 0; a<list.size(); a++){
                    //获取商家里商品
                   List<ShopBean.DataBean.ListBean> listAll = list.get(a).getList();
                  for (int i = 0; i<listAll.size(); i++){
                      totalNum = totalNum + listAll.get(i).getNum();
                      //取选中的状态
                      if(listAll.get(i).isCheck()) {
                          totalPrice = totalPrice + (listAll.get(i).getPrice() * listAll.get(i).getNum());
                          num = num + listAll.get(i).getNum();
                      }
                  }
                }

                if(num < totalNum) {
                    //不是全部选中
                    mIvCircle.setChecked(false);
                }else{
                    //是全部选中
                    mIvCircle.setChecked(true);
                }

                mAllPriceTxt.setText("合计:" + totalPrice);
                nSumPrice.setText("去结算("+ num +")");
            }
        });

    }

    private void getData() {
        Map<String,String> map = new HashMap<>();
        map.put(Constants.MAP_KEY_GET_PRODUCT_UID,"71");
        mshowPresenter.startRequest(Apis.URL_GET_SHOP_CAR_INFO,map,ShopBean.class);

    }



    @Override
    public void showResponseData(Object data) {

        if(data instanceof ShopBean) {
            ShopBean shopBean = (ShopBean) data;
            mList = shopBean.getData();
            if(mList != null){
                mList.remove(0);
                mshopAdapter.setmList(mList);
            }
        }

    }

    @Override
    public void showResponseFail(Object data) {

    }

    @Override
    public void onClick(View view) {
              switch (view.getId()) {
                  case R.id.iv_cricle:
                      checkSeller(mIvCircle.isChecked());
                      mshopAdapter.notifyDataSetChanged();
                      break;
                      default:
              }
    }

    /**
     * 修改选中状态，获取价格和数量
     */
    private void checkSeller(boolean checked) {
       double totalPrice = 0;
       int num = 0;
       for (int a = 0; a < mList.size(); a++){
           //遍历商家，改变状态
           ShopBean.DataBean dataBean = mList.get(a);
           dataBean.setCheck(checked);

           List<ShopBean.DataBean.ListBean> listAll = mList.get(a).getList();
           for (int i = 0 ; i < listAll.size(); i++) {
               //遍历商品，改变状态
               totalPrice = totalPrice + (listAll.get(i).getPrice() * listAll.get(i).getNum());
               num = num + listAll.get(i).getNum();
           }
       }

        if (checked) {
            mAllPriceTxt.setText("合计：" + totalPrice);
            nSumPrice.setText("去结算(" + num + ")");
        } else {
            mAllPriceTxt.setText("合计：0.00");
            nSumPrice.setText("去结算(0)");
        }

    }
}
