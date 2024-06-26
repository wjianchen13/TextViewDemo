package com.example.textviewdemo.shader.gradient_final.rainbow_view.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;

import com.example.textviewdemo.BaseApp;
import com.example.textviewdemo.R;
import com.example.textviewdemo.shader.gradient_final.rainbow_view.spans.GradientAnimSpanV2;
import com.example.textviewdemo.shader.gradient_final.rainbow_view.spans.GradientSpanV2;
import com.example.textviewdemo.thumb.Utils;

public class GradientUtils {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        return Utils.dip2px(context, dpValue);
    }

    public static void log(String str) {
        System.out.println("=========================> " + str);
    }

    /**
     * 获得指定内容大小和颜色字符串
     */
    public static SpannableString getGradientText(Context context, String txt, int[] colors, int startIndex, int maxWidth) {
        SpannableString spanString = new SpannableString(txt);
        if (context != null && !TextUtils.isEmpty(spanString)) {
            GradientSpanV2 span = new GradientSpanV2(txt, colors, startIndex, maxWidth);
            spanString.setSpan(span, 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spanString;
    }

    /**
     * 获得指定内容大小和颜色字符串
     */
    public static SpannableString getColorText(Context context, String txt, int colorId) {
        SpannableString spanString = new SpannableString(txt);
        if (context != null && !TextUtils.isEmpty(spanString)) {
            spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, colorId)), 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spanString;
    }

    /**
     * 获得指定内容大小和颜色字符串
     */
    public static SpannableString getSizeText(Context context, String txt, int sizeId) {
        SpannableString spanString = new SpannableString(txt);
        if (context != null && !TextUtils.isEmpty(spanString)) {
            AbsoluteSizeSpan span = new AbsoluteSizeSpan(context.getResources().getDimensionPixelOffset(sizeId));
            spanString.setSpan(span, 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spanString;
    }


    public static @ColorInt int[] getColors() {
        int[] colors = new int[] {
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cffde3d32),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cfffeb702),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cff80ff00),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cff00bfcb)
        };
        return colors;
    }

    /**
     * 获得指定内容大小和颜色字符串
     */
    public static SpannableString getGradientAnimText(Context context, String txt, int[] colors, int startIndex, int maxWidth) {
        SpannableString spanString = new SpannableString(txt);
        if (context != null && !TextUtils.isEmpty(spanString)) {
            GradientAnimSpanV2 span = new GradientAnimSpanV2(txt, colors, startIndex, maxWidth);
            spanString.setSpan(span, 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spanString;
    }

    /**
     *
     * @param src
     * @param dstWidth
     * @param dstHeight
     * @param filter filter是一个布尔值，用于确定是否启用过滤器。如果filter为true，则会应用图像过滤器以平滑缩放图像；如果为false，则不会应用图像过滤器，图像可能会显得锐利但可能带有锯齿。
     * 如果设置filter参数为true，通常会得到更平滑的缩放效果，但可能会消耗更多的系统资源和处理时间。而如果设置为false，则可能会得到更快的缩放结果，但可能会失去一些图像的平滑度。
     * @return
     */
    public static Bitmap createScaledBitmap(Bitmap src, int dstWidth, int dstHeight, boolean filter) {
        return Bitmap.createScaledBitmap(src, dstWidth, dstHeight, filter);
    }

    /**
     * 创建一个BitmapShader
     * @param width
     * @param height
     * @param colors
     * @return
     */
    public static BitmapShader createBitmapShader(int width, int height, int[] colors) {
        try {
            Bitmap bmp = GradientUtils.createGradientBitmap(width, height, colors);
            BitmapShader bitmapShader = new BitmapShader(bmp, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
            return bitmapShader;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据传入的尺寸和图片，生成新的Bitmap
     * @param width
     * @param height
     * @param colors
     * @return
     */
    public static Bitmap createGradientBitmap(int width, int height, int[] colors) {
        if(colors == null) {
            return null;
        }
        if(width == 0)
            width = 100;
        if(height == 0)
            height = 100;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        LinearGradient linearGradient = new LinearGradient(0, 0, width, 0, colors, null, Shader.TileMode.CLAMP);
        paint.setShader(linearGradient);
        canvas.drawRect(0, 0, width, height, paint);
        return bitmap;
    }

}
