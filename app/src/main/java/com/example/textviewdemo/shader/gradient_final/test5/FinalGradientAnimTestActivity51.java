package com.example.textviewdemo.shader.gradient_final.test5;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.textviewdemo.R;
import com.example.textviewdemo.shader.gradient_final.manager.AnimManager;
import com.example.textviewdemo.shader.gradient_final.rainbow_view.RainbowScrollTextViewV2;
import com.example.textviewdemo.thumb.Utils;

/**
 * RainbowScrollTextViewV2 使用，检查生命周期，是否回收
 */
public class FinalGradientAnimTestActivity51 extends AppCompatActivity {

    private RainbowScrollTextViewV2 tvTest1;
//    private RainbowScrollTextViewV2 tvTest2;
//    private RainbowScrollTextViewV2 tvTest3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_gradient_anim_test51);
        tvTest1 = findViewById(R.id.tv_test1);
//        tvTest2 = findViewById(R.id.tv_test2);
//        tvTest3 = findViewById(R.id.tv_test3);
    }

    /**
     * 滚动模式 滚动+渐变 xml设置
     * @param v
     */
    public void onTest1(View v) {
        String str = "测试滚动和渐变同时存在的情况，需要设置singleLine=true，设置之后Shader不起作用";
        int color1 = ContextCompat.getColor(this, R.color.cffde3d32);
        int color2 = ContextCompat.getColor(this, R.color.cfffeb702);
        int color3 = ContextCompat.getColor(this, R.color.cff80ff00);
        int color4 = ContextCompat.getColor(this, R.color.cff00bfcb);
        tvTest1.setContent(str, new int[]{color1, color2, color3, color4});
        Utils.log("onTest1");
    }

    /**
     * 回收测试
     * @param v
     */
    public void onTest2(View v) {
        AnimManager.getInstance().logAllView();
    }


    /**
     * 滚动模式 滚动+渐变 代码设置
     * @param v
     */
    public void onTest21(View v) {
        String str = "测试滚动和渐变同时存在的情况，需要设置singleLine=true，设置之后Shader不起作用";
        int color1 = ContextCompat.getColor(this, R.color.cffde3d32);
        int color2 = ContextCompat.getColor(this, R.color.cfffeb702);
        int color3 = ContextCompat.getColor(this, R.color.cff80ff00);
        int color4 = ContextCompat.getColor(this, R.color.cff00bfcb);
//        tvTest2.setContent(str, false);
    }

    /**
     * 开始滚动
     * @param v`
     */
    public void onTest3(View v) {
        String str = "测试滚动和渐变同时存在的情况，需要设置singleLine=true，设置之后Shader不起作用";
        int color1 = ContextCompat.getColor(this, R.color.cffde3d32);
        int color2 = ContextCompat.getColor(this, R.color.cfffeb702);
        int color3 = ContextCompat.getColor(this, R.color.cff80ff00);
        int color4 = ContextCompat.getColor(this, R.color.cff00bfcb);
//        tvTest3.setContent(str, true, color1, color2, color3, color4);
    }

    /**
     * 停止滚动
     * @param v
     */
    public void onTest4(View v) {
//        tvTest3.stopAnim();
    }

    /**
     * 设置新内容
     * @param v
     */
    public void onTest5(View v) {
//        tvTest3.setContent("测试滚动和渐变同时存在的情况，需要设置singleLine=true，设置之后Shader不起作用", false);
    }

    /**
     *
     * @param v
     */
    public void onTest6(View v) {

    }

    /**
     *
     * @param v
     */
    public void onTest7(View v) {

    }


}