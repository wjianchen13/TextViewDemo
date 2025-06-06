package com.example.textviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.textviewdemo.ellipsise.EllipsizeActivity;
import com.example.textviewdemo.gradient.GradientActivity;
import com.example.textviewdemo.gradient1.GradientActivity1;
import com.example.textviewdemo.gradient_textview.TestGradientActivity;
import com.example.textviewdemo.gradient_vertical.GradientVerticalActivity;
import com.example.textviewdemo.shader.gradientanimspan.GradientAnimSpanActivity;
import com.example.textviewdemo.random_text.RandomTextActivity;
import com.example.textviewdemo.shader.ShaderActivity;
import com.example.textviewdemo.thumb.ThumbActivity;
import com.example.textviewdemo.vertical.CenterActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 生成缩略图
     * @param v
     */
    public void onTest1(View v) {
        startActivity(new Intent(this, ThumbActivity.class));
    }

    /**
     * 字体渐变动画
     * @param v
     */
    public void onTest2(View v) {
        startActivity(new Intent(this, GradientActivity.class));
    }

    /**
     * 垂直居中TextView
     * @param v
     */
    public void onTest3(View v) {
        startActivity(new Intent(this, CenterActivity.class));
    }

    /**
     * 垂直居中+渐变TextView
     * @param v
     */
    public void onTest4(View v) {
        startActivity(new Intent(this, GradientVerticalActivity.class));
    }

    /**
     * 渐变TextView
     * @param v
     */
    public void onTest5(View v) {
        startActivity(new Intent(this, GradientActivity1.class));
    }

    /**
     * TextView 滚动
     * @param v
     */
    public void onTest6(View v) {
        startActivity(new Intent(this, RandomTextActivity.class));
    }

    /**
     * TextView Shader测试，字体颜色渐变
     * @param v
     */
    public void onTest7(View v) {
        startActivity(new Intent(this, ShaderActivity.class));
    }

    /**
     * 超长显示省略号
     * @param v
     */
    public void onTest8(View v) {
        startActivity(new Intent(this, EllipsizeActivity.class));
    }

    /**
     * 超长显示省略号
     * @param v
     */
    public void onTest9(View v) {
        startActivity(new Intent(this, TestGradientActivity.class));
    }


}