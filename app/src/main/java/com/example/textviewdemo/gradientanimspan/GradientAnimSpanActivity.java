package com.example.textviewdemo.gradientanimspan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.textviewdemo.R;
import com.example.textviewdemo.gradientanimspan.test3.GradientAnimSpanActivity3;

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

}