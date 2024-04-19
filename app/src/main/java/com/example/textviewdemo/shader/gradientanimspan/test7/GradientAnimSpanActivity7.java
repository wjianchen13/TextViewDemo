package com.example.textviewdemo.shader.gradientanimspan.test7;

import android.content.Context;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.textviewdemo.R;
import com.example.textviewdemo.shader.gradientanimspan.GradientAnimSpan;
import com.example.textviewdemo.shader.gradientanimspan.GradientAnimTextView;
import com.example.textviewdemo.shader.gradientanimspan.GradientSpan1;
import com.example.textviewdemo.shader.gradientanimspan.test5.RainbowScrollTextView;

/**
 * 显示省略号
 */
public class GradientAnimSpanActivity7 extends AppCompatActivity {

    private GradientAnimTextView tvTest1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gradient_anim_span7);
        tvTest1 = findViewById(R.id.tv_test1);
    }

    /**
     * 彩虹滚动
     * @param v
     */
    public void onTest1(View v) {
        String str = "测试滚动和渐变同时存在的情况，需要设置singleLine=true，设置之后Shader不起作用";
        int color1 = ContextCompat.getColor(this, R.color.cffde3d32);
        int color2 = ContextCompat.getColor(this, R.color.cfffeb702);
        int color3 = ContextCompat.getColor(this, R.color.cff80ff00);
        int color4 = ContextCompat.getColor(this, R.color.cff00bfcb);
//        tvTest1.setText(str, true, color1, color2, color3, color4);
    }

    /**
     * 非彩虹滚动
     * @param v
     */
    public void onTest2(View v) {
        String str = "测试滚动和渐变同时存在的情况，需要设置singleLine=true，设置之后Shader不起作用";
//        tvTest1.setText(str, false);
    }


    /**
     * 彩虹不滚动
     * @param v
     */
    public void onTest3(View v) {
        String str = "测试滚动和渐变同时存在";
        int color1 = ContextCompat.getColor(this, R.color.cffde3d32);
        int color2 = ContextCompat.getColor(this, R.color.cfffeb702);
        int color3 = ContextCompat.getColor(this, R.color.cff80ff00);
        int color4 = ContextCompat.getColor(this, R.color.cff00bfcb);
//        tvTest1.setText(str, true, color1, color2, color3, color4);
    }

    /**
     * 非彩虹不滚动
     * @param v
     */
    public void onTest4(View v) {
        String str = "测试滚动和渐变同时存在的";
//        tvTest1.setText(str, false);
    }

    /**
     * 获得指定内容大小和颜色字符串
     */
    public SpannableString getGradientText(Context context, String txt, int[] colors, int startIndex, int maxWidth) {
        SpannableString spanString = new SpannableString(txt);
        if (context != null && !TextUtils.isEmpty(spanString)) {
            GradientSpan1 span = new GradientSpan1(txt, colors, startIndex, maxWidth);
            spanString.setSpan(span, 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spanString;
    }

    /**
     * 获得指定内容大小和颜色字符串
     */
    public SpannableString getColorText(Context context, String txt, int colorId) {
        SpannableString spanString = new SpannableString(txt);
        if (context != null && !TextUtils.isEmpty(spanString)) {
            spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, colorId)), 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spanString;
    }

    /**
     * 获得指定内容大小和颜色字符串
     */
    public SpannableString getSizeText(Context context, String txt, int sizeId) {
        SpannableString spanString = new SpannableString(txt);
        if (context != null && !TextUtils.isEmpty(spanString)) {
            AbsoluteSizeSpan span = new AbsoluteSizeSpan(context.getResources().getDimensionPixelOffset(sizeId));
            spanString.setSpan(span, 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spanString;
    }

    /**
     * 获得指定内容大小和颜色字符串
     */
    public SpannableString getGradientAnimText(Context context, String txt, int[] colors, int startIndex, int maxWidth) {
        SpannableString spanString = new SpannableString(txt);
        if (context != null && !TextUtils.isEmpty(spanString)) {
            GradientAnimSpan span = new GradientAnimSpan(txt, colors, startIndex, maxWidth);
            spanString.setSpan(span, 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spanString;
    }

}