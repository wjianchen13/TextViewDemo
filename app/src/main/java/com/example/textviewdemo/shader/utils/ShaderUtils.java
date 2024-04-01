package com.example.textviewdemo.shader.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import androidx.core.content.ContextCompat;

public class ShaderUtils {

    public static void log(String str) {
        System.out.println("==========================> " + str);
    }

    /**
     * 获得指定内容大小和颜色字符串
     */
    public static SpannableString getGradientText(Context context, String txt, int colorId) {
        SpannableString spanString = new SpannableString(txt);
        if (context != null && !TextUtils.isEmpty(spanString)) {
            spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, colorId)), 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
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
    public static SpannableString getSizeColorText(Context context, String txt, int sizeId, int colorId, boolean isBold) {
        SpannableString spanString = new SpannableString(txt);
        if (context != null && !TextUtils.isEmpty(spanString)) {
            AbsoluteSizeSpan span = new AbsoluteSizeSpan(context.getResources().getDimensionPixelOffset(sizeId));
            spanString.setSpan(span, 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, colorId)), 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if(isBold) {
                spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
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


}
