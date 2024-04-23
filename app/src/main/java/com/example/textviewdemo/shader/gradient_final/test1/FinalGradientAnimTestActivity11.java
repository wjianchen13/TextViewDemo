package com.example.textviewdemo.shader.gradient_final.test1;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.textviewdemo.R;
import com.example.textviewdemo.shader.gradient_final.view.GradientAnimTextViewV2;
import com.example.textviewdemo.shader.gradientanimspan.GradientAnimTextView;
import com.example.textviewdemo.thumb.Utils;

/**
 * 1.彩虹，需要支持滚动
 */
public class FinalGradientAnimTestActivity11 extends AppCompatActivity {

    private GradientAnimTextViewV2 tvTest1;
    private GradientAnimTextViewV2 tvTest2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_gradient_anim_test11);
        tvTest1 = findViewById(R.id.tv_test1);
        tvTest2 = findViewById(R.id.tv_test2);
    }

    /**
     *
     * @param v
     */
    public void onTest1(View v) {
        String str = "测试滚动和渐变同时存在的情况，需要设置singleLine=true，设置之后Shader不起作用";
        int color1 = ContextCompat.getColor(this, R.color.cffde3d32);
        int color2 = ContextCompat.getColor(this, R.color.cfffeb702);
        int color3 = ContextCompat.getColor(this, R.color.cff80ff00);
        int color4 = ContextCompat.getColor(this, R.color.cff00bfcb);
        tvTest1.setGradientColor(color1, color2, color3, color4);
        tvTest1.setText(str);
        Utils.log("=====================> onTest1");
    }

    /**
     *
     * @param v
     */
    public void onTest2(View v) {
        String str = "测试滚动和渐变同时存在的情况，需要设置singleLine=true，设置之后Shader不起作用";
        int color1 = ContextCompat.getColor(this, R.color.cffde3d32);
        int color2 = ContextCompat.getColor(this, R.color.cfffeb702);
        int color3 = ContextCompat.getColor(this, R.color.cff80ff00);
        int color4 = ContextCompat.getColor(this, R.color.cff00bfcb);
        tvTest2.setScrollMode();
        tvTest2.setGradientColor(color1, color2, color3, color4);
        tvTest2.setText(str);
    }

    /**
     *
     * @param v
     */
    public void onTest3(View v) {

    }

    /**
     *
     * @param v
     */
    public void onTest4(View v) {

    }

    /**
     *
     * @param v
     */
    public void onTest5(View v) {

    }

    /**
     *
     * @param v
     */
    public void onTest6(View v) {

    }

    /**
     *
     * @param v
     */
    public void onTest7(View v) {

    }


}