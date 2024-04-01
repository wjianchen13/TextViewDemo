package com.example.textviewdemo.shader.lineargradientspan;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.textviewdemo.R;

/**
 * LinearGradient Span基础使用
 */
public class LinearGradientSpanActivity extends AppCompatActivity {

    private TextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_gradient_span);
        tvTest = findViewById(R.id.bsv_test);
    }

    /**
     * LinearGradient Span基础使用
     * @param v
     */
    public void onTest1(View v) {
//        mBitmapShaderView.invalidate();
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
        tvTest.setText(giftComeSb);

    }

    /**
     * 刷新测试
     * @param v
     */
    public void onTest2(View v) {
        tvTest.invalidate();
    }

}