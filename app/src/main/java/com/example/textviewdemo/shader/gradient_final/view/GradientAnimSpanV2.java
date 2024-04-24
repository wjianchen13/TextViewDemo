package com.example.textviewdemo.shader.gradient_final.view;

import android.text.style.UpdateAppearance;

import androidx.annotation.ColorInt;

import com.example.textviewdemo.BaseApp;
import com.example.textviewdemo.thumb.Utils;

public class GradientAnimSpanV2 extends GradientSpanV2
        implements UpdateAppearance, IGradientAnimSpanV2 {

    private int mProgress;

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
            if(mTranslate > mWidth) { // 超出范围，重新开始
                mTranslate = 0;
            }
            mTranslate += Utils.dip2px(BaseApp.getInstance(), 1);
        }
        mProgress = progress;
    }
}

