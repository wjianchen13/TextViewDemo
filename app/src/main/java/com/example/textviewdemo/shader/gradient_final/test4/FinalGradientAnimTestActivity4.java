package com.example.textviewdemo.shader.gradient_final.test4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.textviewdemo.R;
import com.example.textviewdemo.shader.gradient_final.test3.FinalGradientAnimTestActivity31;
import com.example.textviewdemo.shader.gradient_final.test3.FinalGradientAnimTestActivity32;

/**
 * GradientAnimTextViewV2 渐变 富文本
 */
public class FinalGradientAnimTestActivity4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_gradient_anim_test4);
    }

    /**
     * 单个使用
     * @param v
     */
    public void onTest1(View v) {
        startActivity(new Intent(this, FinalGradientAnimTestActivity41.class));
    }

    /**
     * 列表使用
     * @param v
     */
    public void onTest2(View v) {
        startActivity(new Intent(this, FinalGradientAnimTestActivity42.class));
    }


}