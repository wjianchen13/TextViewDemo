package com.example.textviewdemo.shader.textview_test;

import android.graphics.Bitmap;

public interface IGradientSpan {

    String getSpanText(); // 获取当前span的显示内容
    void onDrawBefore(float start, float end); // 开始绘制
    Bitmap  getGradientBitmap();
    int getStartIndex(); // 开始位置

}
