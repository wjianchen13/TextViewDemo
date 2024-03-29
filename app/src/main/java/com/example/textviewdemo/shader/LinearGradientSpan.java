package com.example.textviewdemo.shader;

import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

import com.example.textviewdemo.thumb.Utils;

public class LinearGradientSpan extends CharacterStyle
        implements UpdateAppearance {

    private String mStart;
    private String mText;

    private int startColor;
    private int midColor;
    private int endColor;
    private int mMaxWidth;
    private int[] mColors;

    /**
     * 0 水平方向
     * 1 垂直方向
     */
    private int mType;

    public LinearGradientSpan(String start, String text, @ColorInt int[] colors, int maxWidth) {
        this.mStart = start;
        this.mText = text;
        this.mColors = colors;
        this.mMaxWidth = maxWidth;
    }

    public LinearGradientSpan(String start, String text, @ColorInt int[] colors, int maxWidth, int type) {
        this.mStart = start;
        this.mText = text;
        this.mColors = colors;
        this.mMaxWidth = maxWidth;
        this.mType = type;
    }

    @Override
    public void updateDrawState(TextPaint tp) {
        Utils.log("LinearGradientSpan updateDrawState");
        if(tp != null) {
            float start = !TextUtils.isEmpty(mStart) ? tp.measureText(mStart) - 14 : 0;
            float width = !TextUtils.isEmpty(mText) ? tp.measureText(mText) + 4 : 0;
            if(mMaxWidth > 0 && width > mMaxWidth)
                width = mMaxWidth;
            LinearGradient lg;
            if(startColor != 0 && midColor != 0 && endColor != 0) {
                lg = new LinearGradient(0 + start, 0, width + start, 0,
                        new int[]{startColor, midColor, endColor},
                        null, Shader.TileMode.REPEAT);
            } else if(mColors != null){
                lg = new LinearGradient(0 + start, 0, width + start, 0,
                        mColors, null, Shader.TileMode.REPEAT);
            } else {
                lg = new LinearGradient(0 + start, 0, width + start, 0,
                        new int[]{0}, null, Shader.TileMode.REPEAT);
            }

            tp.setShader(lg);        //这里注意这里画出来的渐变色会受TextView的字体色的透明度影响
        }

//        if(tp != null) {
//            if(mType == 0) {
//                float start = !TextUtils.isEmpty(mStart) ? tp.measureText(mStart) : 0;
//                float width = !TextUtils.isEmpty(mText) ? tp.measureText(mText) : 0;
//                if (mMaxWidth > 0 && width > mMaxWidth)
//                    width = mMaxWidth;
//                LinearGradient lg = new LinearGradient(0 + start,
//                        0,
//                        width + start,
//                        0,
//                        new int[]{startColor, midColor, endColor},
//                        null,
//                        Shader.TileMode.REPEAT);
//                tp.setShader(lg);        //这里注意这里画出来的渐变色会受TextView的字体色的透明度影响
//            } else {
//                LinearGradient lg = new LinearGradient(0,
//                        0,
//                        0,
//                        tp.descent() - tp.ascent(),
//                        new int[]{startColor, midColor, endColor},
//                        null,
//                        Shader.TileMode.REPEAT);
//                tp.setShader(lg);        //这里注意这里画出来的渐变色会受TextView的字体色的透明度影响
//            }
//        }

    }
}

