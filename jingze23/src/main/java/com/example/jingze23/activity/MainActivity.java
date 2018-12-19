package com.example.jingze23.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jingze23.R;
import com.example.jingze23.view.CustomFlowLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //TODO:将搜索记录读取出来，展示
    }

    private void initView() {
       final CustomFlowLayout customFlowLayout = findViewById(R.id.cfl_search);
      final EditText editText = findViewById(R.id.edit_search);
        findViewById(R.id.iv_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv = new TextView(MainActivity.this);
                tv.setTextColor(Color.RED);
               tv.setText(editText.getText());
               tv.setBackgroundResource(R.drawable.search_history_bg);
               customFlowLayout.addView(tv);

                //TODO:将搜索记录存入数据库

                Intent intent = new Intent(MainActivity.this, ShopCarActivity.class);
                startActivity(intent);
            }
        });

    }
}
