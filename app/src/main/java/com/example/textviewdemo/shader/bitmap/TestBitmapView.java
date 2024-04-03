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
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.textviewdemo.BaseApp;
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
    private Bitmap bmpNew;

    private int[] svip6Color;
    private int[] svip7Color;

    private Bitmap scaledBitmap;

    public TestBitmapView(Context context) {
        super(context);
    }

    // 3.在构造函数中初始化
    public TestBitmapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        svip6Color = new int[]{ContextCompat.getColor(BaseApp.getInstance(), R.color.cffff2f2f),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cffd2a96b),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cffbf37ff)};
        svip7Color = new int[]{ContextCompat.getColor(BaseApp.getInstance(), R.color.cffde3d32),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cfffeb702),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cff80ff00),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cff00bfcb)};
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
//        bmpNew = Bitmap.createBitmap(svip6Color, 100, 300, Bitmap.Config.ARGB_8888);
        bmpNew = createGradientBitmap(132, 100, svip7Color);
    }
    public TestBitmapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bmp, mMatrix, mPaint);
        canvas.translate(0, 200);
        canvas.drawBitmap(scaledBitmap, mMatrix, mPaint);
        canvas.translate(0, 100);
        canvas.drawBitmap(bmpNew, mMatrix, mPaint);

//        getPaint().setColor(Color.BLUE);
//        canvas.drawCircle(0,0,100, getPaint());

    }

    public Bitmap createGradientBitmap(int width, int height, int[] colors) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        LinearGradient linearGradient = new LinearGradient(0, 0, width, 0, colors, null, Shader.TileMode.CLAMP);
        paint.setShader(linearGradient);
        canvas.drawRect(0, 0, width, height, paint);
        return bitmap;
    }
}
