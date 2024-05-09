package com.example.textviewdemo.shader.gradient_final.rainbow_view.spans;

import android.graphics.Bitmap;
import android.text.style.UpdateAppearance;

import androidx.annotation.ColorInt;

import com.example.textviewdemo.BaseApp;
import com.example.textviewdemo.shader.gradient_final.rainbow_view.interfaces.IGradientAnimSpanV2;
import com.example.textviewdemo.shader.gradient_final.rainbow_view.utils.GradientUtils;

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
    private int mShaderWidth = GradientUtils.dip2px(BaseApp.getInstance(), 100);

    /**
     *
     * @param text
     * @param colors
     * @param maxWidth
     */
    public GradientAnimSpanV2(String text, @ColorInt int[] colors, int startIndex, int maxWidth) {
        super(text, colors, startIndex, maxWidth);
    }

    protected void setGradientColor(@ColorInt int[] color) {
        if(color != null && color.length > 0) {
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
                mTranslate -= GradientUtils.dip2px(BaseApp.getInstance(), 1);
            } else {
                mTranslate += GradientUtils.dip2px(BaseApp.getInstance(), 1);
            }
        }
        mProgress = progress;
    }

    @Override
    protected Bitmap createShaderBitmap() {
        return GradientUtils.createGradientBitmap(getRealWidth(), mCreateBitmapHeight, mColors);
    }

    private int getRealWidth() {
        return isShaderWidthFix ? mShaderWidth : mWidth;
    }
}

