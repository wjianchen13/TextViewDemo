package com.example.textviewdemo.shader.gradient_final.test9;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.textviewdemo.BaseApp;
import com.example.textviewdemo.R;
import com.example.textviewdemo.shader.gradient_final.utils.GradientUtils;
import com.example.textviewdemo.shader.gradient_final.rainbow_view.GradientAnimTextViewV2;

/**
 * 测试GradientAnimTextViewV2获取Span的实际位置
 */
public class FinalGradientAnimTestActivity9 extends AppCompatActivity {

    private GradientAnimTextViewV2 tvTest1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_gradient_anim_test9);
        tvTest1 = findViewById(R.id.tv_test1);
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
     * 测试1
     * @param v
     */
    public void onTest1(View v) {
        SpannableStringBuilder sContent = new SpannableStringBuilder();
        sContent.append(GradientUtils.getSizeText(this, "hello ni hao ya!!", R.dimen.dp_20));
        sContent.append(" ");
        sContent.append(GradientUtils.getGradientAnimText(this, "سجل ABمعركة BBالفريقCC", getColors(), sContent.length(), 1800)); // 18  32

        sContent.append(" ");
        sContent.append(GradientUtils.getColorText(this, "可以", R.color.color_6200EE));
        sContent.append(" ");
        sContent.append(GradientUtils.getGradientAnimText(this, "wo xiang", getColors(), sContent.length(), 1800)); // 43 51
        sContent.append(" ");
        sContent.append(GradientUtils.getColorText(this, "سجل معركة الفريق", R.color.color_6200EE));
        sContent.append("    ");
        sContent.append(GradientUtils.getGradientText(this, "AB", getColors(), sContent.length(), 1800)); // 64 75
        tvTest1.setContent(sContent);
    }


    /**
     * 测试2
     * @param v
     */
    public void onTest2(View v) {

    }

    /**
     * 测试3
     * @param v
     */
    public void onTest3(View v) {

    }

    /**
     * 测试4
     * @param v
     */
    public void onTest4(View v) {

    }

    /**
     * 测试5
     * @param v
     */
    public void onTest5(View v) {

    }
}