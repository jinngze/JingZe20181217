package com.example.jingze26;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;


public class SecondActivity extends AppCompatActivity {

    private ImageView miv_main;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        //  找控件
        miv_main = findViewById(R.id.mImage);
        findViewById(R.id.mJump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  旋转动画
                ObjectAnimator animator = ObjectAnimator.ofFloat(miv_main, "rotation", 0f, 180f);
                //  透明 渐变动画
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(miv_main, "alpha", 0f, 0.8f);
                //  组合动画对象
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(animator1).with(animator);
                //  执行动画时长
                animatorSet.setDuration(2000);
                //  开始的动画
                animatorSet.start();
                //跳转
                animatorSet.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    //   End  代表 结束后  跳转
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                });
            }
        });
    }
}
