package com.example.textviewdemo.shader.gradient_final.view;

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
import com.example.textviewdemo.shader.textview_test.GradientInfo;
import com.example.textviewdemo.shader.utils.ShaderUtils;
import com.example.textviewdemo.thumb.Utils;

public class GradientSpanV2 extends CharacterStyle implements UpdateAppearance, IGradientSpanV2 {

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
    protected int[] mColors;

    private Bitmap bmp;
    private BitmapShader mShader;

    private Matrix mMatrix;

    protected float mTranslate;

    private boolean isAr = true;

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
    public GradientSpanV2(String text, @ColorInt int[] colors, int startIndex, int maxWidth) {
        this.mText = text;
        this.mStartIndex = startIndex;
        this.mMaxWidth = maxWidth;
        mMatrix = new Matrix();
        setGradientColor(colors);
    }

    /**
     * 这里面传入的color不能是统一对象，如果是同一个对象，当有多个GradientSpanV2时，会导致方向混乱
     * @param color
     */
    public void setGradientColor(@ColorInt int[] color) {
        mColors = color;
        if(isAr) { // 阿拉伯语颜色反向
            reverseArray(mColors);
        }
    }

    private void reverseArray(int[] arr) {
        if(arr == null || arr.length == 0)
            return ;
        for(int start = 0, end = arr.length - 1; start < end; start ++, end --) {
            swap(arr, start, end);
        }

    }

    private void swap(int arr[], int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    @Override
    public void updateDrawState(TextPaint tp) {
//        Utils.log("LinearGradientSpan updateDrawState");
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
            bmp = createShaderBitmap();
            mShader = new BitmapShader(bmp, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        }
    }

    protected Bitmap createShaderBitmap() {
        return ShaderUtils.createGradientBitmap(mWidth, mCreateBitmapHeight, mColors);
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

