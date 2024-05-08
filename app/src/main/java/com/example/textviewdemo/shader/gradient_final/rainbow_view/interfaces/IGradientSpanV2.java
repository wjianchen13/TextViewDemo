package com.example.textviewdemo.shader.gradient_final.rainbow_view.interfaces;

import android.graphics.Bitmap;

import com.example.textviewdemo.shader.gradient_final.rainbow_view.bean.GradientInfo;


public interface IGradientSpanV2 {

    String getSpanText(); // 获取当前span的显示内容
    void onDrawBefore(GradientInfo info); // 开始绘制
    Bitmap  getGradientBitmap();
    int getStartIndex(); // 开始位置
    GradientInfo getGradientInfo();

}
