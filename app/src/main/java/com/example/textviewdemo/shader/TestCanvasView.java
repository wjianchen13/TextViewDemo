package com.example.textviewdemo.shader;


import android.content.Context;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.textviewdemo.thumb.Utils;

/**
 * name: TestCanvasView
 * desc: 测试画布操作
 * author:
 * date: 2018-07-05 20:00
 * remark:
 */
public class TestCanvasView extends TextView {

    private int[] colors = {0xFFFF2B22, 0xFFFF7F22, 0xFFEDFF22, 0xFF22FF22, 0xFF22F4FF, 0xFF2239FF, 0xFF5400F7};
    private LinearGradient mLinearGradient;
    private Matrix mMatrix;

//    // 1.创建一个画笔
//    private Paint mPaint = new Paint();
    private Context mContext;

    private int mTranslate;

    public TestCanvasView(Context context) {
        super(context);
    }

    // 3.在构造函数中初始化
    public TestCanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initPaint();
    }

    private void initPaint() {
        mLinearGradient = new LinearGradient(0, 0, Utils.dip2px(mContext, 150f), 0, colors, null, Shader.TileMode.MIRROR);
        getPaint().setShader(mLinearGradient);
        BitmapShader shader;
    }
    public TestCanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawRect(0,0,660, 30, getPaint());

        if (mMatrix == null) {
            mMatrix = new Matrix();
        }

        mMatrix.setTranslate(Utils.dip2px(mContext, 10f), 0);
        mLinearGradient.setLocalMatrix(mMatrix);
        canvas.drawRect(0,40,660, 70, getPaint());

        mMatrix.setTranslate(Utils.dip2px(mContext, 20f), 0);
        mLinearGradient.setLocalMatrix(mMatrix);
        canvas.drawRect(0,80,660, 110, getPaint());

        mMatrix.setTranslate(Utils.dip2px(mContext, 30f), 0);
        mLinearGradient.setLocalMatrix(mMatrix);
        canvas.drawRect(0,120,660, 140, getPaint());

        mMatrix.setTranslate(Utils.dip2px(mContext, 40f), 0);
        mLinearGradient.setLocalMatrix(mMatrix);
        canvas.drawRect(0,150,660, 180, getPaint());

        mMatrix.setTranslate(Utils.dip2px(mContext, 50f), 0);
        mLinearGradient.setLocalMatrix(mMatrix);
        canvas.drawRect(0,190,660, 220, getPaint());

        super.onDraw(canvas);
//        getPaint().setColor(Color.BLUE);
//        canvas.drawCircle(0,0,100, getPaint());

    }
}
