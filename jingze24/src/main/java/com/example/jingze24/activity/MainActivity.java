package com.example.jingze24.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.jingze24.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        findViewById(R.id.gotoShop).setOnClickListener(this);
        findViewById(R.id.gotoShopType).setOnClickListener(this);
    }

    public void gotoShop() {
        startActivity(new Intent(this, ShopCarActivity.class));
    }


    public void gotoShopType() {
        startActivity(new Intent(this, ShopTypeActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gotoShop:
                gotoShop();
                break;
            case R.id.gotoShopType:
                gotoShopType();
                break;
            default:
                break;
        }
    }
}