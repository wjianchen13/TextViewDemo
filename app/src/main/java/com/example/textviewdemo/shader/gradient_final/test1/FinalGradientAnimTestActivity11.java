package com.example.textviewdemo.shader.gradient_final.test1;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.textviewdemo.R;
import com.example.textviewdemo.shader.gradient_final.rainbow_view.GradientAnimTextViewV2;
import com.example.textviewdemo.thumb.Utils;

/**
 * 1.彩虹，需要支持滚动
 * 单单使用GradientAnimTextViewV2可以实现渐变滚动，但是不能实现常规字体超长滚动，所以需要在改TextView外面再套一层FrameLayout
 * 也就是RainbowScrollTextViewV2
 */
public class FinalGradientAnimTestActivity11 extends AppCompatActivity {

    private GradientAnimTextViewV2 tvTest1;
    private GradientAnimTextViewV2 tvTest2;
    private GradientAnimTextViewV2 tvTest3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_gradient_anim_test11);
        tvTest1 = findViewById(R.id.tv_test1);
        tvTest2 = findViewById(R.id.tv_test2);
        tvTest3 = findViewById(R.id.tv_test3);
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
     * 滚动模式 滚动+渐变 代码设置
     * @param v
     */
    public void onTest2(View v) {
        String str = "测试滚动和渐变同时存在的情况，需要设置singleLine=true，设置之后Shader不起作用";
        int color1 = ContextCompat.getColor(this, R.color.cffde3d32);
        int color2 = ContextCompat.getColor(this, R.color.cfffeb702);
        int color3 = ContextCompat.getColor(this, R.color.cff80ff00);
        int color4 = ContextCompat.getColor(this, R.color.cff00bfcb);
        tvTest2.setScrollMode();
        tvTest2.setContent(str, new int[]{color1, color2, color3, color4});
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
        tvTest3.setScrollMode();
        tvTest3.setContent(str, new int[]{color1, color2, color3, color4});
    }

    /**
     * 停止滚动
     * @param v
     */
    public void onTest4(View v) {
        tvTest3.stopAnim();
    }

    /**
     * 设置新内容
     * @param v
     */
    public void onTest5(View v) {
        tvTest3.setSpanMode();
        tvTest3.setContent("测试滚动和渐变同时存在的情况，需要设置singleLine=true，设置之后Shader不起作用");
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