package com.example.textviewdemo.shader.mix;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.textviewdemo.R;
import com.example.textviewdemo.shader.bitmap.TestBitmapView;
import com.example.textviewdemo.shader.bitmapshaderspan.BitmapShaderSpan;
import com.example.textviewdemo.shader.lineargradientspan.LinearGradientSpan;

/**
 * 字体Span混合测试 GradientSpan BitmapShaderSpan
 */
public class MixSpanActivity extends AppCompatActivity {

    private TextView tvTest1;
    private TextView tvTest2;
    public static MixSpanActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(R.layout.activity_mix_span);
        tvTest1 = findViewById(R.id.tv_test1);
        tvTest2 = findViewById(R.id.tv_test2);
    }

    /**
     * GradientSpan 测试
     * @param v
     */
    public void onTest1(View v) {
        String str = getString(R.string.span_test);
        SpannableStringBuilder giftComeSb = new SpannableStringBuilder(str);
        @ColorInt int[] colors = new int[]{ContextCompat.getColor(this, R.color.cfffad962),
                ContextCompat.getColor(this, R.color.cffe5ae65),
                ContextCompat.getColor(this, R.color.cffff9d69)};
        String select = "you";
        int entrantSt = str.indexOf(select);
        int entrantEd = entrantSt + select.length();
        giftComeSb.setSpan(new LinearGradientSpan("img ", select,
                        colors, 0),
                entrantSt, entrantEd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvTest1.setText(giftComeSb);
    }

    /**
     * BitmapShaderSpan 测试
     * @param v
     */
    public void onTest2(View v) {
        String str = getString(R.string.span_test);
        SpannableStringBuilder giftComeSb = new SpannableStringBuilder(str);
        @ColorInt int[] colors = new int[]{ContextCompat.getColor(this, R.color.cfffad962),
                ContextCompat.getColor(this, R.color.cffe5ae65),
                ContextCompat.getColor(this, R.color.cffff9d69)};
        String select = "you";
        int entrantSt = str.indexOf(select);
        int entrantEd = entrantSt + select.length();
        giftComeSb.setSpan(new BitmapShaderSpan("img ", select,
                        colors, 0),
                entrantSt, entrantEd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvTest2.setText(giftComeSb);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivity = null;
    }
}