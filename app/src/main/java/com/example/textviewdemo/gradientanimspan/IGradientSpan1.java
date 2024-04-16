package com.example.textviewdemo.gradientanimspan;

import android.graphics.Bitmap;

import com.example.textviewdemo.textview_test.GradientInfo;

public interface IGradientSpan1 {

    String getSpanText(); // 获取当前span的显示内容
    void onDrawBefore(GradientInfo info); // 开始绘制
    Bitmap  getGradientBitmap();
    int getStartIndex(); // 开始位置
    GradientInfo getGradientInfo();

}
