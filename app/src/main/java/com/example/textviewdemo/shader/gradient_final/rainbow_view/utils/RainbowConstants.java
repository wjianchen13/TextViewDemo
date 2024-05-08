package com.example.textviewdemo.shader.gradient_final.rainbow_view.utils;

import com.example.textviewdemo.BaseApp;

public class RainbowConstants {

    public static boolean isTest = true;

    /**
     * Shader的宽度是否固定
     * true  固定,由mShaderWidth决定
     * false 不固定，等于span字体的宽度
     * GradientAnimSpanV2 的Shader宽度
     * GradientAnimTextViewV2 滚动的Shader宽度
     */
    public static boolean isShaderWidthFix = true;

    /**
     * 固定shader的宽度
     */
    public static int shaderWidth = GradientUtils.dip2px(BaseApp.getInstance(), 100);

    /**
     * 动态创建Bitmap的高度
     */
    public static final int createBitmapHeight = 150;

    /**
     * 滚动或彩虹渐变的动画宽度
     */
    public static final int mAnimWidth = 300;

    /**
     * 根据配置返回Shader的实际宽度
     * @return
     */
    public static int getShaderRealWidth(int width) {
        return isShaderWidthFix ? shaderWidth : width;
    }

}
