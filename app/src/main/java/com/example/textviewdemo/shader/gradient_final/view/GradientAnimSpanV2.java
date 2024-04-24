package com.example.textviewdemo.shader.gradient_final.view;

import android.graphics.Bitmap;
import android.text.style.UpdateAppearance;

import androidx.annotation.ColorInt;

import com.example.textviewdemo.BaseApp;
import com.example.textviewdemo.shader.utils.ShaderUtils;
import com.example.textviewdemo.thumb.Utils;

public class GradientAnimSpanV2 extends GradientSpanV2
        implements UpdateAppearance, IGradientAnimSpanV2 {

    private int mProgress;

    /**
     * 阿拉伯语反方向
     */
    private boolean isAr = true;

    /**
     * Shader的宽度是否固定
     * true  固定,由mShaderWidth决定
     * false 不固定，等于span字体的宽度
     */
    private boolean isShaderWidthFix = true;

    /**
     * 固定shader的宽度
     */
    private int mShaderWidth = Utils.dip2px(BaseApp.getInstance(), 100);

    /**
     *
     * @param text
     * @param colors
     * @param maxWidth
     */
    public GradientAnimSpanV2(String text, @ColorInt int[] colors, int startIndex, int maxWidth) {
        super(text, colors, startIndex, maxWidth);
    }

    public void setGradientColor(@ColorInt int[] color) {
        if(color.length > 0) {
            mColors = new int[color.length * 2 - 1];
            int i = 0;
            for (int c : color) {
                mColors[i] = c;
                mColors[2 * color.length - 2 - i] = c;
                i ++;
            }
        }
    }

    @Override
    public void onAnim(int progress) {
        if(mProgress != progress) {
            if(Math.abs(mTranslate) > getRealWidth()) { // 超出范围，重新开始
                mTranslate = 0;
            }
            if(isAr) {
                mTranslate -= Utils.dip2px(BaseApp.getInstance(), 1);
            } else {
                mTranslate += Utils.dip2px(BaseApp.getInstance(), 1);
            }
        }
        mProgress = progress;
    }

    @Override
    protected Bitmap createShaderBitmap() {
        return ShaderUtils.createGradientBitmap(getRealWidth(), mCreateBitmapHeight, mColors);
    }

    private int getRealWidth() {
        return isShaderWidthFix ? mShaderWidth : mWidth;
    }
}

