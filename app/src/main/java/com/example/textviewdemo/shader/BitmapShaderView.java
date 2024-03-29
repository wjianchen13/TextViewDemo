package com.example.textviewdemo.shader;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.textviewdemo.R;

/**
 * name: TranslateView
 * desc: 画布位移操作
 * author:
 * date: 2018-07-05 20:00
 * remark:
 */
public class BitmapShaderView extends TextView {

    private Context mContext;

    private Bitmap bmp;
    private BitmapShader mShader;
    private Paint mPaint;

    private Matrix mMatrix;
    private float mTranslate;

    public BitmapShaderView(Context context) {
        super(context);
    }

    // 3.在构造函数中初始化
    public BitmapShaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        mMatrix = new Matrix();
        initPaint();
    }

    private void initPaint() {
        mPaint = getPaint();
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_test_shader3);
        mShader = new BitmapShader(bmp, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        mPaint.setShader(mShader);
    }
    public BitmapShaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int w = getWidth();
        int h = getHeight();

//        mPaint.setTextSize(100.0f);
//        mPaint.setColor(RED);
//        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
//        canvas.drawText("小狗狗你很好呀，你好可爱",0,h/2, mPaint);
        mMatrix.setTranslate(mTranslate, 0);
        mTranslate += 10;
        mShader.setLocalMatrix(mMatrix);
        super.onDraw(canvas);
    }
}
