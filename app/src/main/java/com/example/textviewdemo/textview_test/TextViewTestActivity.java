package com.example.textviewdemo.textview_test;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.textviewdemo.BaseApp;
import com.example.textviewdemo.R;
import com.example.textviewdemo.shader.utils.ShaderUtils;

/**
 * TextView 测试
 */
public class TextViewTestActivity extends AppCompatActivity {

    private TestTextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textview_test);
        tvTest = findViewById(R.id.tv_test);
    }

    /**
     *
     * @param v
     */
    public void onTest1(View v) {
        SpannableStringBuilder sContent = new SpannableStringBuilder();
        sContent.append(ShaderUtils.getSizeText(this, "hello ni hao ya!!yayaya", R.dimen.dp_20));
        int[] colors = new int[] {
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cffde3d32),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cfffeb702),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cff80ff00),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cff00bfcb)
        };
        sContent.append(" ");
        sContent.append(ShaderUtils.getGradientText(this, "wo xiang qu da", colors, 200));
        sContent.append(" ");
        sContent.append(ShaderUtils.getColorText(this, "可以 le hai 我 xing de hua ", R.color.color_6200EE));
        tvTest.setText(sContent);
//        tvTest.setText("hello ni hao ya!! wo xiang qu da 可以 le hai 我 xing de hua ");
    }

}