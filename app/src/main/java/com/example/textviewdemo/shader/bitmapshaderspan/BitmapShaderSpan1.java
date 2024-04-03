package com.example.textviewdemo.shader.bitmapshaderspan;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;

import com.example.textviewdemo.BaseApp;
import com.example.textviewdemo.R;
import com.example.textviewdemo.shader.utils.ShaderUtils;
import com.example.textviewdemo.thumb.Utils;

/**
 * 无论是LinearGradient还是BitmapShader，在富文本设置时都需要设置偏移，他们的绘制区域是以整个TextView区域为准的
 * 如果没有设置偏移，而span的区域又不在起始位置时，如果是静态的渐变span，看起来就好像有断层。如果是TileMode设置成了
 * CLAMP,那后面的span看起来就是最后一个色值的颜色，是纯色。
 */
public class BitmapShaderSpan1 extends CharacterStyle
        implements UpdateAppearance {

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

    private int[] svip7Color;

    /**
     * 字体宽度，动态计算
     */
    private int mWidth;

    /**
     * 真实背景Bitmap
     */
    private Bitmap bmpBg;

    public BitmapShaderSpan1(String start, String text, @ColorInt int[] colors, int textSize, int maxWidth) {
        this.svip7Color = new int[]{ContextCompat.getColor(BaseApp.getInstance(), R.color.cffde3d32),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cfffeb702),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cff80ff00),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cff00bfcb)};
        this.mStart = start;
        this.mText = text;
        this.mColors = colors;
        this.mMaxWidth = maxWidth;
        mMatrix = new Matrix();
        initShader(textSize);
    }

    /**
     * 初始化Shader
     */
    private void initShader(int textSize) {
        if(mShader == null) {
            Paint p = new Paint();
            p.setTextSize(Utils.dip2px(BaseApp.getInstance(), textSize));
            mWidth = !TextUtils.isEmpty(mText) ? (int)p.measureText(mText) : 10;
            Utils.log("width: " + mWidth);
            if (mWidth > 0) {
                bmpBg = createGradientBitmap(mWidth, 10, svip7Color);
                mShader = new BitmapShader(bmpBg, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
            }
        }
    }

    @Override
    public void updateDrawState(TextPaint tp) {
        Utils.log("LinearGradientSpan updateDrawState");
        if(tp != null && mShader != null) {
            tp.setShader(mShader);        //这里注意这里画出来的渐变色会受TextView的字体色的透明度影响
        }
    }

    public static Bitmap createGradientBitmap(int width, int height, int[] colors) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        LinearGradient linearGradient = new LinearGradient(0, 0, width, 0, colors, null, Shader.TileMode.CLAMP);
        paint.setShader(linearGradient);
        canvas.drawRect(0, 0, width, height, paint);
        return bitmap;
    }
}

