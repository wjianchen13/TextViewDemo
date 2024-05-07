package com.example.textviewdemo.shader.gradient_final.test4;

import android.content.Context;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.textviewdemo.BaseApp;
import com.example.textviewdemo.R;
import com.example.textviewdemo.shader.gradient_final.view.GradientAnimSpanV2;
import com.example.textviewdemo.shader.gradient_final.view.GradientAnimTextViewV2;
import com.example.textviewdemo.shader.gradient_final.view.GradientSpanV2;

/**
 * 富文本，渐变 单个使用
 */
public class FinalGradientAnimTestActivity41 extends AppCompatActivity {

    private GradientAnimTextViewV2 tvTest1;
    private GradientAnimTextViewV2 tvTest2;
    private GradientAnimTextViewV2 tvTest3;
    private GradientAnimTextViewV2 tvTest4;
    private GradientAnimTextViewV2 tvTest5;
    private GradientAnimTextViewV2 tvTest6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_gradient_anim_test41);
        tvTest1 = findViewById(R.id.tv_test1);
        tvTest2 = findViewById(R.id.tv_test2);
        tvTest3 = findViewById(R.id.tv_test3);
        tvTest4 = findViewById(R.id.tv_test4);
        tvTest5 = findViewById(R.id.tv_test5);
        tvTest6 = findViewById(R.id.tv_test6);
    }

    /**
     * 超长显示...
     * @param v
     */
    public void onTest1(View v) {
        SpannableStringBuilder sContent = new SpannableStringBuilder();
        sContent.append(getSizeText(this, "hello ni hao ya!!", R.dimen.dp_20));
        sContent.append(" ");
        sContent.append(getGradientAnimText(this, "سجل ABمعركة BBالفريقCC", getColors(), sContent.length(), 1800)); // 18  32

        sContent.append(" ");
        sContent.append(getColorText(this, "可以", R.color.color_6200EE));
        sContent.append(" ");
        sContent.append(getGradientAnimText(this, "wo xiang", getColors(), sContent.length(), 1800)); // 43 51
        sContent.append(" ");
        sContent.append(getColorText(this, "سجل معركة الفريق", R.color.color_6200EE));
        sContent.append("    ");
        sContent.append(getGradientText(this, "AB", getColors(), sContent.length(), 1800)); // 64 75
        tvTest1.setContent(sContent);

//        SpannableStringBuilder sContent = new SpannableStringBuilder();
//        sContent.append(getSizeText(this, "hello ni hao ya!!", R.dimen.dp_20));
//        int[] colors = new int[] {
//                ContextCompat.getColor(BaseApp.getInstance(), R.color.cffde3d32),
//                ContextCompat.getColor(BaseApp.getInstance(), R.color.cfffeb702),
//                ContextCompat.getColor(BaseApp.getInstance(), R.color.cff80ff00),
//                ContextCompat.getColor(BaseApp.getInstance(), R.color.cff00bfcb)
//        };
//        sContent.append(" ");
//        sContent.append(getGradientText(this, "سجل ABمعركة BBالفريقCC", colors, sContent.length(), 1800)); // 18  32
//
//        sContent.append(" ");
//        sContent.append(getColorText(this, "可以", R.color.color_6200EE));
//        sContent.append(" ");
//        sContent.append(getGradientText(this, "wo xiang", colors, sContent.length(), 1800)); // 43 51
//        sContent.append(" ");
//        sContent.append(getColorText(this, "سجل معركة الفريق", R.color.color_6200EE));
//        sContent.append("    ");
//        sContent.append(getGradientText(this, "AB", colors, sContent.length(), 1800)); // 64 75
//        tvTest1.setContent(sContent);
    }

    /**
     * 没超长的情况
     * @param v
     */
    public void onTest2(View v) {
        SpannableStringBuilder sContent = new SpannableStringBuilder();
        sContent.append(getSizeText(this, "hello ni hao ya!!", R.dimen.dp_20));
        sContent.append(" ");
        sContent.append(getGradientAnimText(this, "سجل ABمعركة BBالفريقCC", getColors(), sContent.length(), 1800)); // 18  32

        sContent.append(" ");
        sContent.append(getColorText(this, "可以", R.color.color_6200EE));
        sContent.append(" ");
        sContent.append(getGradientAnimText(this, "wo xiang", getColors(), sContent.length(), 1800)); // 43 51
        sContent.append(" ");
        sContent.append(getColorText(this, "سجل معركة الفريق", R.color.color_6200EE));
        sContent.append("    ");
        sContent.append(getGradientText(this, "AB", getColors(), sContent.length(), 1800)); // 64 75
        tvTest2.setContent(sContent);

//        SpannableStringBuilder sContent = new SpannableStringBuilder();
//        sContent.append(getSizeText(this, "hello ni hao ya!!", R.dimen.dp_20));
//        int[] colors1 = new int[] {
//                ContextCompat.getColor(BaseApp.getInstance(), R.color.cffde3d32),
//                ContextCompat.getColor(BaseApp.getInstance(), R.color.cfffeb702),
//                ContextCompat.getColor(BaseApp.getInstance(), R.color.cff80ff00),
//                ContextCompat.getColor(BaseApp.getInstance(), R.color.cff00bfcb)
//        };
//        int[] colors2 = new int[] {
//                ContextCompat.getColor(BaseApp.getInstance(), R.color.cffde3d32),
//                ContextCompat.getColor(BaseApp.getInstance(), R.color.cfffeb702),
//                ContextCompat.getColor(BaseApp.getInstance(), R.color.cff80ff00),
//                ContextCompat.getColor(BaseApp.getInstance(), R.color.cff00bfcb)
//        };
//        sContent.append(" ");
//        sContent.append(getGradientText(this, "سجل ABمعركة BBالفريقCC", colors, sContent.length(), 1800)); // 18  32
//
//        sContent.append(" ");
//        sContent.append(getColorText(this, "可以", R.color.color_6200EE));
//        sContent.append(" ");
//        sContent.append(getGradientText(this, "wo xiang", colors1, sContent.length(), 1800)); // 43 51
//        sContent.append(" ");
//        sContent.append(getColorText(this, "سجل معركة الفريق", R.color.color_6200EE));
//        sContent.append("    ");
//        sContent.append(getGradientText(this, "AB", colors2, sContent.length(), 1800)); // 64 75
//        tvTest2.setContent(sContent);
    }

    /**
     * 开始滚动
     * @param v`
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
     * 停止滚动
     * @param v
     */
    public void onTest4(View v) {
        tvTest3.stopAnim();
    }

    /**
     * 设置新内容
     * @param v
     */
    public void onTest5(View v) {
//        tvTest3.startAnim();
        tvTest3.setContent("hello");
    }

    /**
     * 设置渐变字体
     * @param v
     */
    public void onTest6(View v) {
        SpannableStringBuilder sContent = new SpannableStringBuilder();
        int[] colors = new int[] {
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cffde3d32),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cfffeb702),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cff80ff00),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cff00bfcb)
        };
        sContent.append(getGradientText(this, "测试滚动和渐变同时存在", colors, sContent.length(), 1800)); // 18  32
        tvTest4.setContent(sContent);
    }

    /**
     * 单纯显示英语渐变
     * @param v
     */
    public void onTest7(View v) {
        SpannableStringBuilder sContent = new SpannableStringBuilder();
        int[] colors = new int[] {
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cffde3d32),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cfffeb702),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cff80ff00),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cff00bfcb)
        };
        sContent.append(getGradientText(this, "hello world", colors, sContent.length(), 1800)); // 18  32
        tvTest5.setContent(sContent);
    }

    /**
     * 单纯显示阿拉伯语渐变
     * @param v
     */
    public void onTest8(View v) {
        SpannableStringBuilder sContent = new SpannableStringBuilder();
        int[] colors = new int[] {
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cffde3d32),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cfffeb702),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cff80ff00),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cff00bfcb)
        };
        sContent.append(getGradientText(this, "سجل معركة الفريق", colors, sContent.length(), 1800)); // 18  32
        tvTest6.setContent(sContent);
    }


    /**
     * 获得指定内容大小和颜色字符串
     */
    public SpannableString getGradientText(Context context, String txt, int[] colors, int startIndex, int maxWidth) {
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


    private @ColorInt int[] getColors() {
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
    public SpannableString getGradientAnimText(Context context, String txt, int[] colors, int startIndex, int maxWidth) {
        SpannableString spanString = new SpannableString(txt);
        if (context != null && !TextUtils.isEmpty(spanString)) {
            GradientAnimSpanV2 span = new GradientAnimSpanV2(txt, colors, startIndex, maxWidth);
            spanString.setSpan(span, 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spanString;
    }

}