package com.example.textviewdemo.gradientanimspan.test5;

import android.content.Context;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.textviewdemo.R;
import com.example.textviewdemo.gradientanimspan.GradientSpan1;

/**
 * 渐变滚动和非渐变滚动兼容
 * 渐变滚动使用的是自定义方式
 * 常规滚动使用的是TextView自带方式
 */
public class GradientAnimSpanActivity5 extends AppCompatActivity {

    private RainbowTextView tvTest1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gradient_anim_span5);
        tvTest1 = findViewById(R.id.tv_test1);
    }

    /**
     * 滚动+渐变
     * 测试滚动和渐变同时存在的情况，需要设置singleLine=true，设置之后Shader不起作用
     * @param v
     */
    public void onTest1(View v) {
        String str = "测试滚动和渐变同时存在的情况，需要设置singleLine=true，设置之后Shader不起作用";
        tvTest1.setContent(str);
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

}