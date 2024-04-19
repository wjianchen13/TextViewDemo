package com.example.textviewdemo.shader.gradientanimspan;

import android.content.Context;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.textviewdemo.BaseApp;
import com.example.textviewdemo.R;

/**
 * 渐变，渐变动画基础测试
 */
public class GradientAnimSpanActivity2 extends AppCompatActivity {

    private GradientAnimTextView tvTest1;
    private GradientAnimTextView tvTest2;
    private GradientAnimTextView tvTest3;
    private GradientAnimTextView tvTest4;
    private GradientAnimTextView tvTest30;
    private GradientAnimTextView tvTest31;
    private GradientAnimTextView tvTest32;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gradient_anim_span2);
        tvTest1 = findViewById(R.id.tv_test1);
        tvTest2 = findViewById(R.id.tv_test2);
        tvTest3 = findViewById(R.id.tv_test3);
        tvTest4 = findViewById(R.id.tv_test4);
        tvTest30 = findViewById(R.id.tv_test30);
        tvTest31 = findViewById(R.id.tv_test31);
        tvTest32 = findViewById(R.id.tv_test32);

    }

    /**
     * 滚动+渐变
     * 测试滚动和渐变同时存在的情况，需要设置singleLine=true，设置之后Shader不起作用
     * @param v
     */
    public void onTest1(View v) {
        String str = "测试滚动和渐变同时存在的情况，需要设置singleLine=true，设置之后Shader不起作用";
        int color1 = ContextCompat.getColor(this, R.color.cffde3d32);
        int color2 = ContextCompat.getColor(this, R.color.cfffeb702);
        int color3 = ContextCompat.getColor(this, R.color.cff80ff00);
        int color4 = ContextCompat.getColor(this, R.color.cff00bfcb);
        tvTest1.setGradientColor(color1, color2, color3, color4);
        tvTest1.setText(str);

    }

    /**
     * 滚动+渐变 代码设置
     * 测试滚动和渐变同时存在的情况，需要设置singleLine=true，设置之后Shader不起作用
     * @param v
     */
    public void onTest2(View v) {
        String str = "测试滚动和渐变同时存在的情况，需要设置singleLine=true，设置之后Shader不起作用";
        int color1 = ContextCompat.getColor(this, R.color.cffde3d32);
        int color2 = ContextCompat.getColor(this, R.color.cfffeb702);
        int color3 = ContextCompat.getColor(this, R.color.cff80ff00);
        int color4 = ContextCompat.getColor(this, R.color.cff00bfcb);
        tvTest2.setScrollMode();
        tvTest2.setGradientColor(color1, color2, color3, color4);
        tvTest2.setText(str);
    }

    /**
     * 常规模式 超长显示... xml 设置
     * @param v
     */
    public void onTest3(View v) {
        SpannableStringBuilder sContent = new SpannableStringBuilder();
        int[] colors = new int[] {
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cffde3d32),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cfffeb702),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cff80ff00),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cff00bfcb)
        };
        sContent.append(getGradientAnimText(this, "测试滚动和渐变同时存在的情况，需要设置singleLine=true，设置之后Shader不起作用", colors, sContent.length(), 1800)); // 18  32
        tvTest3.setContent(sContent);
    }

    /**
     * 常规模式 超长显示 ... 代码设置
     * @param v
     */
    public void onTest4(View v) {
        tvTest4.setSpanModeEllipsize();
        SpannableStringBuilder sContent = new SpannableStringBuilder();
        int[] colors = new int[] {
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cffde3d32),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cfffeb702),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cff80ff00),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cff00bfcb)
        };
        sContent.append(getGradientAnimText(this, "测试滚动和渐变同时存在的情况，需要设置singleLine=true，设置之后Shader不起作用", colors, sContent.length(), 1800)); // 18  32
        tvTest4.setContent(sContent);
    }


    /**
     * 不滚动+渐变 normal方式
     * @param v
     */
    public void onTest31(View v) {
        SpannableStringBuilder sContent = new SpannableStringBuilder();
        int[] colors = new int[] {
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cffde3d32),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cfffeb702),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cff80ff00),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cff00bfcb)
        };
        sContent.append(getGradientAnimText(this, "سجل ABمعركة BBالفريقCC", colors, sContent.length(), 1800)); // 18  32
        tvTest31.setContent(sContent);
    }

    /**
     * 超长显示 ...
     * @param v
     */
    public void onTest32(View v) {
        SpannableStringBuilder sContent = new SpannableStringBuilder();
        int[] colors = new int[] {
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cffde3d32),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cfffeb702),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cff80ff00),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cff00bfcb)
        };
        sContent.append(getGradientAnimText(this, "测试滚动和渐变同时存在的情况，需要设置singleLine=true，设置之后Shader不起作用", colors, sContent.length(), 1800)); // 18  32
        tvTest32.setContent(sContent);
    }




    /**
     * 不滚动+渐变 scroll方式
     * @param v
     */
    public void onTest30(View v) {
        String str = "不滚动+渐变";
        tvTest30.setText(str);
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