package com.example.textviewdemo.shader.gradientanimspan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.textviewdemo.R;
import com.example.textviewdemo.shader.gradientanimspan.test3.GradientAnimSpanActivity3;
import com.example.textviewdemo.shader.gradientanimspan.test5.GradientAnimSpanActivity5;
import com.example.textviewdemo.shader.gradientanimspan.test6.GradientAnimSpanActivity6;
import com.example.textviewdemo.shader.gradientanimspan.test7.GradientAnimSpanActivity7;

/**
 * 支持渐变，动画TextView，使用BitmapShader
 */
public class GradientAnimSpanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gradient_anim_span);
    }

    /**
     * 渐变，渐变动画基础
     * @param v
     */
    public void onTest1(View v) {
        startActivity(new Intent(this, GradientAnimSpanActivity1.class));
    }

    /**
     * 渐变，渐变动画基础测试
     * @param v
     */
    public void onTest2(View v) {
        startActivity(new Intent(this, GradientAnimSpanActivity2.class));
    }

    /**
     * 渐变，渐变动画列表使用
     * @param v
     */
    public void onTest3(View v) {
        startActivity(new Intent(this, GradientAnimSpanActivity3.class));
    }

    /**
     * 生命周期测试
     * @param v
     */
    public void onTest4(View v) {
        startActivity(new Intent(this, GradientAnimSpanActivity4.class));
    }

    /**
     * 彩虹滚动和常规滚动兼容
     * @param v
     */
    public void onTest5(View v) {
        startActivity(new Intent(this, GradientAnimSpanActivity5.class));
    }

    /**
     * TextView默认值测试
     * @param v
     */
    public void onTest6(View v) {
        startActivity(new Intent(this, GradientAnimSpanActivity6.class));
    }

    /**
     * 显示省略号
     * @param v
     */
    public void onTest7(View v) {
        startActivity(new Intent(this, GradientAnimSpanActivity7.class));
    }


}