package com.example.textviewdemo.shader.lineargradient;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.Keep;
import androidx.annotation.Nullable;

import com.example.textviewdemo.R;

/**
 * name: LinearGradientView
 * desc: LinearGradient 测试
 * author:
 * date: 2018-07-05 20:00
 * remark:
 */
public class LinearGradientView extends TextView {

    private Context mContext;

    private LinearGradient mLinearGradient;
    private Paint mPaint;

    private int[] colors;

    private Matrix mMatrix;
    private float mTranslate;

    public LinearGradientView(Context context) {
        super(context);
    }

    // 3.在构造函数中初始化
    public LinearGradientView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        mMatrix = new Matrix();
        colors = new int[] {getResources().getColor(R.color.color_03DAC5), getResources().getColor(R.color.cffde3d32)};
    }

    public void setGradientColor(@ColorInt int... color) {
        if(color.length > 0) {
            colors = new int[color.length * 2 - 1];
            int i = 0;
            for (int c : color) {
                colors[i] = c;
                colors[2 * color.length - 2 - i] = c;
                i ++;
            }
        }
        initPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        initPaint();
    }

    private void initPaint() {
        mPaint = getPaint();
        mLinearGradient = new LinearGradient(0f, 0f, getMeasuredWidth(), 0f, colors, null, Shader.TileMode.MIRROR);

        mPaint.setShader(mLinearGradient);
    }
    public LinearGradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int w = getWidth();
        int h = getHeight();
        canvas.drawRect(0, 0, w, 60, mPaint);
//        mPaint.setTextSize(100.0f);
//        mPaint.setColor(RED);
//        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
//        canvas.drawText("小狗狗你很好呀，你好可爱",0,h/2, mPaint);
        super.onDraw(canvas);
    }
}
