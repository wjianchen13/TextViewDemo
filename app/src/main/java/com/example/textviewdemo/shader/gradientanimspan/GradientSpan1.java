package com.example.textviewdemo.shader.gradientanimspan;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;

import com.example.textviewdemo.BaseApp;
import com.example.textviewdemo.R;
import com.example.textviewdemo.shader.utils.ShaderUtils;
import com.example.textviewdemo.shader.textview_test.GradientInfo;
import com.example.textviewdemo.thumb.Utils;

public class GradientSpan1 extends CharacterStyle implements UpdateAppearance, IGradientSpan1 {

    /**
     * 动态创建Bitmap的高度
     */
    public static final int mCreateBitmapHeight = 150;

    private String mText;

    /**
     * span开始位置，因为可能有相同的内容，所以以这个开始位置进行区分
     */
    private int mStartIndex;

    private int mMaxWidth;
    protected int mWidth;
    private int[] mColors;

    private Bitmap bmp;
    private BitmapShader mShader;

    private Matrix mMatrix;

    protected float mTranslate;

    /**
     * 当前span 的渐变信息
     */
    protected GradientInfo mGradientInfo;

    /**
     * 渐变span
     * @param text 内容
     * @param colors 渐变颜色
     * @param startIndex 开始位置
     * @param maxWidth 最大宽度
     */
    public GradientSpan1(String text, @ColorInt int[] colors, int startIndex, int maxWidth) {
        this.mText = text;
        this.mColors = colors;
        this.mStartIndex = startIndex;
        this.mMaxWidth = maxWidth;
        mMatrix = new Matrix();
    }

    @Override
    public void updateDrawState(TextPaint tp) {
        Utils.log("LinearGradientSpan updateDrawState");
        if(tp != null && mShader != null) {
            mMatrix.setTranslate(mTranslate, 0);
            mShader.setLocalMatrix(mMatrix);
            tp.setShader(mShader);        // 这里注意这里画出来的渐变色会受TextView的字体色的透明度影响
        }
    }

    @Override
    public String getSpanText() {
        return mText;
    }

    /**
     * 准备绘制
     * @param info
     */
    @Override
    public void onDrawBefore(GradientInfo info) {
        this.mGradientInfo = info;
        float start = 0;
        float end = 10;
        if(info != null) {
            start = info.left;
            end = info.right;
        }
        this.mTranslate = start;
        if(mColors == null) {
            mColors = new int[] {
                    ContextCompat.getColor(BaseApp.getInstance(), R.color.black),
                    ContextCompat.getColor(BaseApp.getInstance(), R.color.black)
            };
        }
        if(mColors != null) {
            mWidth = (int)Math.abs(end - start);
            if(mMaxWidth != 0 && mWidth > mMaxWidth)
                mWidth = mMaxWidth;
            bmp = ShaderUtils.createGradientBitmap(mWidth, mCreateBitmapHeight, mColors);
            mShader = new BitmapShader(bmp, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        }
    }

    @Override
    public Bitmap getGradientBitmap() {
        return bmp;
    }

    @Override
    public int getStartIndex() {
        return mStartIndex;
    }

    @Override
    public GradientInfo getGradientInfo() {
        return mGradientInfo;
    }
}

