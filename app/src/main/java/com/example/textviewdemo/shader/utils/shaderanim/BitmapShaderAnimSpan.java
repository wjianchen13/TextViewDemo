package com.example.textviewdemo.shader.utils.shaderanim;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

import androidx.annotation.ColorInt;

import com.example.textviewdemo.BaseApp;
import com.example.textviewdemo.R;
import com.example.textviewdemo.shader.utils.shaderanim.interfaces.IAnimSpan;
import com.example.textviewdemo.shader.utils.ShaderUtils;
import com.example.textviewdemo.thumb.Utils;

public class BitmapShaderAnimSpan extends CharacterStyle
        implements UpdateAppearance, IAnimSpan {

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

    /**
     * 真实背景Bitmap
     */
    private Bitmap bmpBg;

    /**
     * 字体宽度，动态计算
     */
    private int mWidth;

    private Matrix mMatrix;

    private float mTranslate;

    /**
     *
     * @param start
     * @param text
     * @param textSize sp
     * @param colors
     * @param maxWidth
     */
    public BitmapShaderAnimSpan(String start, String text, int textSize, @ColorInt int[] colors, int maxWidth) {
        this.mStart = start;
        this.mText = text;
        this.mColors = colors;
        this.mMaxWidth = maxWidth;
        mMatrix = new Matrix();
        bmp = BitmapFactory.decodeResource(BaseApp.getInstance().getResources(), R.drawable.ic_test_shader2);
//        Bitmap.createBitmap()
//        bmp1 = ShaderUtils.createScaledBitmap(bmp, bmp.getWidth(), 10, true);
//        mShader = new BitmapShader(bmp, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);

        initShader(textSize);
    }

    /**
     * 初始化Shader
     */
    private void initShader(int textSize) {
        if(mShader == null) {
            Paint p = new Paint();
            p.setTextSize(Utils.dip2px(BaseApp.getInstance(), textSize));
            mWidth = !TextUtils.isEmpty(mText) ? (int)p.measureText(mText) : 0;
            Utils.log("width: " + mWidth);
            if (mWidth > 0) {
                bmpBg = ShaderUtils.createScaledBitmap(bmp, mWidth, 10, true);
                mShader = new BitmapShader(bmpBg, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
            }
        }
    }

    @Override
    public void onAnim(int progress) {
        if(mMatrix != null) {
            int translate = (int)((float)mWidth / (float)100 * progress);
            mMatrix.setTranslate(translate, 0);
            Utils.log("BitmapShaderAnimSpan onAnim progress: " + progress + "   translate: " + translate);
        }
    }

    @Override
    public void updateDrawState(TextPaint tp) {
//        Utils.log("LinearGradientSpan updateDrawState");
        if(tp != null) {
//            float start = !TextUtils.isEmpty(mStart) ? tp.measureText(mStart) - 14 : 0;
//            float width = !TextUtils.isEmpty(mText) ? tp.measureText(mText) + 4 : 0;
//            if(mMaxWidth > 0 && width > mMaxWidth)
//                width = mMaxWidth;
//            LinearGradient lg;
//            if(startColor != 0 && midColor != 0 && endColor != 0) {
//                lg = new LinearGradient(0 + start, 0, width + start, 0,
//                        new int[]{startColor, midColor, endColor},
//                        null, Shader.TileMode.REPEAT);
//            } else if(mColors != null){
//                lg = new LinearGradient(0 + start, 0, width + start, 0,
//                        mColors, null, Shader.TileMode.REPEAT);
//            } else {
//                lg = new LinearGradient(0 + start, 0, width + start, 0,
//                        new int[]{0}, null, Shader.TileMode.REPEAT);
//            }



//            if(mShader == null) {
//                float width = !TextUtils.isEmpty(mText) ? tp.measureText(mText) : 0;
//                Utils.log("update width: " + width);
//                if (width > 0) {
//                    bmpBg = ShaderUtils.createScaledBitmap(bmp, (int) width, 10, true);
//                    //        bmp1 = ShaderUtils.createScaledBitmap(bmp, bmp.getWidth(), 10, true);
//                    mShader = new BitmapShader(bmpBg, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
//                }
//            }


            if(mShader != null && mMatrix != null) {
//                mMatrix.setTranslate(mTranslate, 0);
//                mTranslate += 10;
                mShader.setLocalMatrix(mMatrix);
                tp.setShader(mShader);        //这里注意这里画出来的渐变色会受TextView的字体色的透明度影响
            }
        }
    }
}

