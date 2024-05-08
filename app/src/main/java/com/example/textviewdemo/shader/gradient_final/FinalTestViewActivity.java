package com.example.textviewdemo.shader.gradient_final;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.textviewdemo.R;
import com.example.textviewdemo.shader.gradient_final.view.GradientAnimTextViewV2;
import com.example.textviewdemo.thumb.Utils;

/**
 * 基础测试
 */
public class FinalTestViewActivity extends AppCompatActivity {

    private GradientAnimTextViewV2 tvTest1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_test_view);
        tvTest1 = findViewById(R.id.tv_test1);
    }

    /**
     * 滚动模式 滚动+渐变 xml设置
     * @param v
     */
    public void onTest1(View v) {
        String str = "测试滚动和渐变同时存在的情况，需要设置singleLine=true，设置之后Shader不起作用";
        int color1 = ContextCompat.getColor(this, R.color.cffde3d32);
        int color2 = ContextCompat.getColor(this, R.color.cfffeb702);
        int color3 = ContextCompat.getColor(this, R.color.cff80ff00);
        int color4 = ContextCompat.getColor(this, R.color.cff00bfcb);
        tvTest1.setContent(str, new int[]{color1, color2, color3, color4});
        Utils.log("onTest1");
    }
}