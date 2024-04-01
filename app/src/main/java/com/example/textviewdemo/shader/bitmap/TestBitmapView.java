package com.example.textviewdemo.shader.bitmap;


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

import androidx.annotation.Nullable;

import com.example.textviewdemo.R;
import com.example.textviewdemo.thumb.Utils;

/**
 * name: TestBitmapView
 * desc: 测试Bitmap
 * author:
 * date: 2018-07-05 20:00
 * remark:
 */
public class TestBitmapView extends TextView {

    private int[] colors = {0xFFFF2B22, 0xFFFF7F22, 0xFFEDFF22, 0xFF22FF22, 0xFF22F4FF, 0xFF2239FF, 0xFF5400F7};
    private LinearGradient mLinearGradient;
    private Matrix mMatrix;

//    // 1.创建一个画笔
//    private Paint mPaint = new Paint();
    private Context mContext;
    private Bitmap bmp;
    private Paint mPaint;

    private int mTranslate;

    private Bitmap scaledBitmap;

    public TestBitmapView(Context context) {
        super(context);
    }

    // 3.在构造函数中初始化
    public TestBitmapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initPaint();
    }

    private void initPaint() {
        mPaint = getPaint();
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_test_shader2);
//        mShader = new BitmapShader(bmp, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        mMatrix = new Matrix();


        //        Bitmap srcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.your_image);
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        scaledBitmap = Bitmap.createScaledBitmap(bmp, width / 2, height / 2, true);
        int scaledWidth = scaledBitmap.getWidth();
    }
    public TestBitmapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawBitmap(bmp, mMatrix, mPaint);
//        canvas.translate(0, 300);
//        canvas.drawBitmap(scaledBitmap, mMatrix, mPaint);
//        getPaint().setColor(Color.BLUE);
//        canvas.drawCircle(0,0,100, getPaint());

    }
}
