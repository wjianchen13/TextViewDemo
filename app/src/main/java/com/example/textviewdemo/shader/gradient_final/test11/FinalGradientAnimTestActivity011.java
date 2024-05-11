package com.example.textviewdemo.shader.gradient_final.test11;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.textviewdemo.R;
import com.example.textviewdemo.shader.gradient_final.rainbow_view.GradientAnimTextViewV2;
import com.example.textviewdemo.shader.gradient_final.rainbow_view.utils.GradientUtils;

/**
 * 多个TextView使用同一个span实例
 */
public class FinalGradientAnimTestActivity011 extends AppCompatActivity {

    private GradientAnimTextViewV2 tvTest1;
    private GradientAnimTextViewV2 tvTest2;
    private SpannableStringBuilder sContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_gradient_anim_test011);
        tvTest1 = findViewById(R.id.tv_test1);
        tvTest2 = findViewById(R.id.tv_test2);
    }

    /**
     * 生成span
     * @param v
     */
    public void onTest1(View v) {
        sContent = new SpannableStringBuilder();
        sContent.append(GradientUtils.getSizeText(this, "hello ni hao ya!!", R.dimen.dp_20));
        sContent.append(" ");
        sContent.append(GradientUtils.getGradientAnimText(this, "سجل ABمعركة BBالفريقCC", GradientUtils.getColors(), sContent.length(), 1800)); // 18  32

        sContent.append(" ");
        sContent.append(GradientUtils.getColorText(this, "可以", R.color.color_6200EE));
        sContent.append(" ");
        sContent.append(GradientUtils.getGradientAnimText(this, "wo xiang", GradientUtils.getColors(), sContent.length(), 1800)); // 43 51
        sContent.append(" ");
        sContent.append(GradientUtils.getColorText(this, "سجل معركة الفريق", R.color.color_6200EE));
        sContent.append("    ");
        sContent.append(GradientUtils.getGradientText(this, "AB", GradientUtils.getColors(), sContent.length(), 1800)); // 64 75

    }

    /**
     * 设置span1
     * @param v
     */
    public void onTest2(View v) {
        tvTest1.setContent(sContent);
    }

    /**
     * 设置span2
     * @param v
     */
    public void onTest3(View v) {
        tvTest2.setContent(sContent);
    }

    /**
     * 取消设置
     * @param v
     */
    public void onTest4(View v) {
        tvTest2.setContent("");
    }

}