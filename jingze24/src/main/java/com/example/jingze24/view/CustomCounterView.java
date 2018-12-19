package com.example.jingze24.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.jingze24.R;
import com.example.jingze24.adapter.ProductsAdapter;
import com.example.jingze24.bean.ShopBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 加减数量
 */
public class CustomCounterView extends RelativeLayout implements View.OnClickListener {
    private EditText mEditCar;
    private Context mContext;

    private List<ShopBean.DataBean.ListBean> mList = new ArrayList<>();
    private int position;
    private ProductsAdapter mProductsAdapter;

    public CustomCounterView(Context context) {
        super(context);
        init(context);
    }

    public CustomCounterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomCounterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        View view = View.inflate(context, R.layout.shop_car_price_layout, null);
        ImageView addIamge = (ImageView) view.findViewById(R.id.add_car);
        ImageView jianIamge = (ImageView) view.findViewById(R.id.jian_car);
        mEditCar = (EditText) view.findViewById(R.id.edit_shop_car);
        addIamge.setOnClickListener(this);
        jianIamge.setOnClickListener(this);
        addView(view);

        mEditCar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //TODO:改变数量
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private int num;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_car:
                //改变数量，设置数量，改变对象内容，回调，局部刷新
                num++;
                mEditCar.setText(num + "");
                mList.get(position).setNum(num);
                mCallBackListener.callBack();
                mProductsAdapter.notifyItemChanged(position);
                break;
            case R.id.jian_car:
                if (num > 1) {
                    num--;
                } else {
                    toast("我是有底线的啊");
                }
                mEditCar.setText(num + "");
                mList.get(position).setNum(num);
                mCallBackListener.callBack();
                mProductsAdapter.notifyItemChanged(position);
                break;
            default:
                break;
        }
    }

    private void toast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }

    public void setData(ProductsAdapter mProductsAdapter, List<ShopBean.DataBean.ListBean> list, int i) {
        this.mList = list;
        this.mProductsAdapter = mProductsAdapter;
        position = i;
        num = list.get(i).getNum();
        mEditCar.setText(num + "");
    }

    private CallBackListener mCallBackListener;

    public void setOnCallBack(CallBackListener listener) {
        this.mCallBackListener = listener;
    }

    public interface CallBackListener {
        void callBack();
    }
}