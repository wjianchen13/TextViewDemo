package com.example.textviewdemo.shader;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.textviewdemo.R;
import com.example.textviewdemo.shader.bitmap.TestViewActivity2;
import com.example.textviewdemo.shader.bitmapshader.BitmapShaderActivity;
import com.example.textviewdemo.shader.bitmapshaderspan.BitmapShaderSpanActivity;
import com.example.textviewdemo.shader.canvas.TestViewActivity;
import com.example.textviewdemo.shader.gradient_final.FinalGradientAnimActivity;
import com.example.textviewdemo.shader.gradientanimspan.GradientAnimSpanActivity;
import com.example.textviewdemo.shader.gradienttextview.GradientActivity2;
import com.example.textviewdemo.shader.lineargradient.LinearGradientActivity;
import com.example.textviewdemo.shader.lineargradientspan.LinearGradientSpanActivity;
import com.example.textviewdemo.shader.mix.MixSpanActivity;
import com.example.textviewdemo.shader.textview_test.TextViewTestActivity;
import com.example.textviewdemo.shader.textview_test.TextViewTestActivityV2;
import com.example.textviewdemo.shader.utils.shaderanim.BitmapShaderAnimActivity;

public class ShaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shader);
    }

    /**
     * canvas 基础测试
     * @param v
     */
    public void onTest1(View v) {
        startActivity(new Intent(this, TestViewActivity.class));
    }

    /**
     * Bitmap生成测试
     * @param v
     */
    public void onTest2(View v) {
        startActivity(new Intent(this, TestViewActivity2.class));
    }

    /**
     * BitmapShader 测试
     * @param v
     */
    public void onTest3(View v) {
        startActivity(new Intent(this, BitmapShaderActivity.class));
    }

    /**
     * LinearGradient Span基础使用
     * @param v
     */
    public void onTest4(View v) {
        startActivity(new Intent(this, LinearGradientSpanActivity.class));
    }

    /**
     * BitmapShader Span测试
     * @param v
     */
    public void onTest5(View v) {
        startActivity(new Intent(this, BitmapShaderSpanActivity.class));
    }

    /**
     * 字体Span混合测试 GradientSpan BitmapShaderSpan
     * @param v
     */
    public void onTest6(View v) {
        startActivity(new Intent(this, MixSpanActivity.class));
    }

    /**
     * BitmapShader Span 动画
     * @param v
     */
    public void onTest7(View v) {
        startActivity(new Intent(this, BitmapShaderAnimActivity.class));
    }

    /**
     * LinearGradient 测试
     * @param v
     */
    public void onTest8(View v) {
        startActivity(new Intent(this, LinearGradientActivity.class));
    }

    /**
     * TextView 测试
     * @param v
     */
    public void onTest9(View v) {
        startActivity(new Intent(this, TextViewTestActivityV2.class));
    }

    /**
     * 渐变TextView 首尾相接
     * @param v
     */
    public void onTest10(View v) {
        startActivity(new Intent(this, GradientActivity2.class));
    }

    /**
     * 支持渐变，动画TextView，使用BitmapShader
     * @param v
     */
    public void onTest11(View v) {
        startActivity(new Intent(this, GradientAnimSpanActivity.class));
    }

    /**
     * 支持渐变，动画TextView 最终版
     * @param v
     */
    public void onTest12(View v) {
        startActivity(new Intent(this, FinalGradientAnimActivity.class));
    }

}