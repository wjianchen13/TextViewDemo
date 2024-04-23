package com.example.textviewdemo.shader.gradient_final;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.appcompat.app.AppCompatActivity;

import com.example.textviewdemo.R;
import com.example.textviewdemo.shader.gradient_final.test1.FinalGradientAnimTestActivity1;
import com.example.textviewdemo.thumb.Utils;

/**
 * 基础测试
 */
public class FinalTestActivity extends AppCompatActivity {

    private ValueAnimator mAnimator;
    private long lastPlayTime;
    private float lastFraction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_test);
    }

    public void startAnim() {
        Utils.log("GradientAnimTextViewV2 startAnim1");

//        if(mAnimator != null) {
//            mAnimator.cancel();
//            mAnimator = null;
//        }
        if(mAnimator == null) {
            mAnimator = ValueAnimator.ofInt(0, 100);
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Utils.log("GradientAnimTextViewV2 animation value:" + animation.getAnimatedValue());
                }
            });
            ValueAnimator.setFrameDelay(50L);
            mAnimator.setDuration(30000);
            mAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mAnimator.setRepeatMode(ValueAnimator.RESTART);
            mAnimator.setInterpolator(new LinearInterpolator());
        }
//        if(lastPlayTime > 0) {
//            mAnimator.setCurrentPlayTime(lastPlayTime);
//            lastPlayTime = 0;
//        }
        mAnimator.start();
    }

    /**
     * 开始动画
     * @param v
     */
    public void onTest1(View v) {
        Utils.log("开始动画");
        startAnim();
    }

    /**
     * 暂停动画
     * @param v
     */
    public void onTest2(View v) {
        Utils.log("暂停动画");
        mAnimator.pause();
        lastFraction = mAnimator.getAnimatedFraction();
    }

    /**
     * 结束动画
     * @param v
     */
    public void onTest3(View v) {
        Utils.log("结束动画");
        mAnimator.cancel();
    }

    /**
     * 恢复动画
     * @param v
     */
    public void onTest4(View v) {
        Utils.log("恢复动画");
        mAnimator.resume();
    }

    /**
     *
     * @param v
     */
    public void onTest5(View v) {

    }

    /**
     *
     * @param v
     */
    public void onTest6(View v) {

    }

    /**
     * 基础测试
     * @param v
     */
    public void onTest7(View v) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mAnimator != null) {
            mAnimator.cancel();
            mAnimator = null;
        }
    }
}