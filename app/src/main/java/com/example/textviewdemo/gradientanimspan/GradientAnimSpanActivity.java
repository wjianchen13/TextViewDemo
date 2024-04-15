package com.example.textviewdemo.gradientanimspan;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.textviewdemo.BaseApp;
import com.example.textviewdemo.R;
import com.example.textviewdemo.shader.utils.ShaderUtils;
import com.example.textviewdemo.textview_test.TestTextView;

/**
 * TextView 测试
 */
public class GradientAnimSpanActivity extends AppCompatActivity {

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
        sContent.append(ShaderUtils.getSizeText(this, "hello ni hao ya!!", R.dimen.dp_20));
        int[] colors = new int[] {
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cffde3d32),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cfffeb702),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cff80ff00),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cff00bfcb)
        };
        sContent.append(" ");
        sContent.append(ShaderUtils.getGradientText(this, "سجل ABمعركة BBالفريقCC", colors, sContent.length(), 800)); // 18  32
//        sContent.append(ShaderUtils.getColorText(this, "wo xiang qu daAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", R.color.color_6200EE)); // 18  32

//        sContent.append(ShaderUtils.getGradientText(this, "wo xiang da", colors, sContent.length(), 800)); // 18  32
        sContent.append(" ");
        sContent.append(ShaderUtils.getColorText(this, "可以", R.color.color_6200EE));
        sContent.append(" ");
        sContent.append(ShaderUtils.getGradientText(this, "wo xiang", colors, sContent.length(), 800)); // 43 51
        sContent.append(" ");
        sContent.append(ShaderUtils.getColorText(this, "سجل معركة الفريق", R.color.color_6200EE));
        sContent.append("    ");
        sContent.append(ShaderUtils.getGradientText(this, "AB", colors, sContent.length(), 800)); // 64 75

        tvTest.setText(sContent);
//        tvTest.setText("hello ni hao ya!! wo xiang qu da 可以 le hai 我 xing de hua ");
    }

    /**
     * 单独测试阿语
     * @param v
     */
    public void onTest2(View v) {
        SpannableStringBuilder sContent = new SpannableStringBuilder();
        int[] colors = new int[] {
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cffde3d32),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cfffeb702),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cff80ff00),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cff00bfcb)
        };
//        sContent.append(ShaderUtils.getGradientText(this, "سجل AAمعركة BBالفريقCC", colors, sContent.length(), 800)); // 18  32
//        sContent.append(ShaderUtils.getColorText(this, "wo xiang qu daAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", R.color.color_6200EE)); // 18  32
//        sContent.append(ShaderUtils.getColorText(this, "bc", R.color.color_6200EE));
//        sContent.append(" ");
//        sContent.append(ShaderUtils.getGradientText(this, "wo xiang qu", colors, sContent.length(), 800)); // 18  32
//        sContent.append(ShaderUtils.getGradientText(this, "w", colors, sContent.length(), 800)); // 18  32
//        sContent.append(ShaderUtils.getGradientText(this, "سجل معركة الفريق", colors, sContent.length(), 800)); // 18  32
//        sContent.append(ShaderUtils.getGradientText(this, "سجل ABمعركة BBالفريقCC", colors, sContent.length(), 800)); // 18  32
//        sContent.append(" \n");
//        sContent.append(ShaderUtils.getColorText(this, "سجل معركة الفريق", R.color.color_6200EE));
//        sContent.append(" ");
//        sContent.append(ShaderUtils.getGradientText(this, "wo xiang", colors, sContent.length(), 800)); // 43 51
//        sContent.append(" ");
//        sContent.append(ShaderUtils.getColorText(this, "سجل معركة الفريق", R.color.color_6200EE));
        sContent.append(ShaderUtils.getColorText(this, "AAA", R.color.color_6200EE));
        sContent.append(" ");
        sContent.append(ShaderUtils.getGradientText(this, "سجل معركة الفريق", colors, sContent.length(), 800)); // 64 75
        sContent.append(" ");
        sContent.append(ShaderUtils.getColorText(this, "BBB", R.color.color_6200EE));
        tvTest.setText(sContent);
    }

}