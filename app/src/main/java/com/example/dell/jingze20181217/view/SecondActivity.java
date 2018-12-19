package com.example.dell.jingze20181217.view;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dell.jingze20181217.R;
import com.example.dell.jingze20181217.adapter.MyAdapter;
import com.example.dell.jingze20181217.adapter.TwoAdapter;
import com.example.dell.jingze20181217.bean.Bean;
import com.example.dell.jingze20181217.presenter.ShowPresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecondActivity extends AppCompatActivity implements IView {

    private final int  mSpanCount= 2;
    private XRecyclerView xRecyclerView;
    private TwoAdapter twoAdapter;
    private ShowPresenter iPresenter;
    private String urlStr = "http://www.zhaoapi.cn/product/searchProducts?keywords=%E7%AC%94%E8%AE%B0%E6%9C%AC&page=1";
    private Bean bean;
    private int mpage;
    private Button button;
    private List<Bean.DataBean> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iPresenter = new ShowPresenter(this);
        init();

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                   startActivity(new Intent(SecondActivity.this,MainActivity.class));
                finish();
            }
        });

        twoAdapter.setClickListeners(new MyAdapter.OnItemClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onItemClick(int i, String title) {
                Toast.makeText(SecondActivity.this,"点击了"+(title)+"数据",0).show();
            }

            //动画
            @Override
            public void onImageClick(View view) {

                ObjectAnimator alpha = ObjectAnimator.ofFloat(view,"alpha",new float[]{1f,0.5f,0.0f,0.5f,1.0f});
                alpha.setRepeatMode(ObjectAnimator.RESTART);
                alpha.setDuration(2000);
                alpha.setRepeatCount(0);
                alpha.start();
            }
        });

    }

    private void init() {

        xRecyclerView = findViewById(R.id.xRecyclerView1);
        iPresenter = new ShowPresenter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(layoutManager);

        twoAdapter = new TwoAdapter(this,list);
        xRecyclerView.setAdapter(twoAdapter);

        twoAdapter.setLongItemClickListeners(new MyAdapter.OnLongItemClickListener() {
            @Override
            public void onItemLongClick(int i) {
                showAlertDialog(i);
            }
        });


        GridLayoutManager gridLayoutManager = new GridLayoutManager(SecondActivity.this, mSpanCount);
        gridLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        xRecyclerView.setLayoutManager(gridLayoutManager);

        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingMoreEnabled(true);

        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mpage = 1;
                initData();
            }

            @Override
            public void onLoadMore() {

                initData();
            }
        });

        initData();
    }



    private void showAlertDialog(final int i) {

        AlertDialog.Builder builder = new AlertDialog.Builder(SecondActivity.this);
        builder.setTitle("提示：");
        builder.setMessage("请问是要删除吗?");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setCancelable(false);
        builder.setPositiveButton("是的", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                twoAdapter.deleteItem(i);
                twoAdapter.notifyDataSetChanged();
                dialogInterface.dismiss();
            }
        });

        builder.setNeutralButton("取消",null);
        builder.show();


    }


    private void initData() {

        Map<String,String> map = new HashMap<>();
        map.put("mpage","1");
        iPresenter.startRequest(urlStr,map,Bean.class);
    }

    @Override
    public void showResponseData(Object data) {

        bean = (Bean) data;
        if(mpage == 1){
            twoAdapter.setdata(bean.getData());
        }else{
            twoAdapter.adddata(bean.getData());
        }
        mpage++;
        xRecyclerView.refreshComplete();
        xRecyclerView.loadMoreComplete();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onDetach();
    }
}

