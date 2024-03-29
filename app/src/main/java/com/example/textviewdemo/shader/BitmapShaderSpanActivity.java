package com.example.textviewdemo.shader;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.textviewdemo.R;

/**
 * BitmapShader Span测试
 */
public class BitmapShaderSpanActivity extends AppCompatActivity {

    private TestBitmapView tvTest;
    public static BitmapShaderSpanActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(R.layout.activity_shader4);
        tvTest = findViewById(R.id.bsv_test);
    }

    /**
     * TextView Shader基础使用
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
        giftComeSb.setSpan(new BitmapShaderSpan("img ", select,
                        colors, 0),
                entrantSt, entrantEd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvTest.setText(giftComeSb);

//        Bitmap srcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.your_image);
//        int width = srcBitmap.getWidth();
//        int height = srcBitmap.getHeight();
//        Bitmap scaledBitmap = Bitmap.createScaledBitmap(srcBitmap, width / 2, height / 2, true);
//        imageView.setImageBitmap(scaledBitmap);
    }

    /**
     * 刷新测试
     * @param v
     */
    public void onTest2(View v) {
        tvTest.invalidate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivity = null;
    }
}