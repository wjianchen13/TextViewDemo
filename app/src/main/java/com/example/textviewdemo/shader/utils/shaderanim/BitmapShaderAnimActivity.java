package com.example.textviewdemo.shader.utils.shaderanim;

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
public class BitmapShaderAnimActivity extends AppCompatActivity {

    private ShaderAnimTextView tvTest;
    public static BitmapShaderAnimActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(R.layout.activity_bitmap_shader_anim);
        tvTest = findViewById(R.id.tv_test);
    }



    /**
     * TextView Shader基础使用
     * @param v
     */
    public void onTest1(View v) {

        String str = getString(R.string.span_test);
        SpannableStringBuilder giftComeSb = new SpannableStringBuilder(str);
//        giftComeSb.getSpans()
        @ColorInt int[] colors = new int[]{ContextCompat.getColor(this, R.color.cffff2f2f),
                ContextCompat.getColor(this, R.color.cffe5ae65),
                ContextCompat.getColor(this, R.color.cff80ff00)};
        String select = "you";
        int entrantSt = str.indexOf(select);
        int entrantEd = entrantSt + select.length();
        BitmapShaderAnimSpan animSpan = new BitmapShaderAnimSpan("img ", select, 30,
                colors, 0);
        giftComeSb.setSpan(animSpan, entrantSt, entrantEd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        String select1 = "me";
        int entrantSt1 = str.indexOf(select1);
        int entrantEd1 = entrantSt1 + select1.length();
        BitmapShaderAnimSpan animSpan1 = new BitmapShaderAnimSpan("img ", select1, 30,
                colors, 0);
        giftComeSb.setSpan(animSpan1, entrantSt1, entrantEd1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvTest.setText(giftComeSb);
        tvTest.startAnim();
//        tvTest.getText()
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

    /**
     * 测试其他功能
     * @param v
     */
    public void onTest3(View v) {
        tvTest.initSpan();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivity = null;
    }
}