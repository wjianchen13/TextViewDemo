package com.example.textviewdemo.textview_test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

import androidx.annotation.ColorInt;

import com.example.textviewdemo.BaseApp;
import com.example.textviewdemo.R;
import com.example.textviewdemo.thumb.Utils;

public class GradientSpan extends CharacterStyle implements UpdateAppearance, IGradientSpan {

    private String mStart;
    private String mText;

    private int startColor;
    private int midColor;
    private int endColor;
    private int mMaxWidth;
    private int[] mColors;

    private Bitmap bmp;
    private BitmapShader mShader;
    private Paint mPaint;

    private Matrix mMatrix;

    private float mTranslate;

    /**
     * 0 水平方向
     * 1 垂直方向
     */
    private int mType;

    public GradientSpan(String start, String text, @ColorInt int[] colors, int maxWidth) {
        this.mStart = start;
        this.mText = text;
        this.mColors = colors;
        this.mMaxWidth = maxWidth;
        mMatrix = new Matrix();
        bmp = BitmapFactory.decodeResource(BaseApp.getInstance().getResources(), R.drawable.ic_test_shader2);
//        Bitmap.createBitmap()
        mShader = new BitmapShader(bmp, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
    }

    @Override
    public void updateDrawState(TextPaint tp) {
        Utils.log("LinearGradientSpan updateDrawState");
        if(tp != null) {
            mMatrix.setTranslate(mTranslate, 0);
            mTranslate += 10;
            mShader.setLocalMatrix(mMatrix);

            tp.setShader(mShader);        //这里注意这里画出来的渐变色会受TextView的字体色的透明度影响
        }
    }

    @Override
    public String getSpanText() {
        return mText;
    }
}

