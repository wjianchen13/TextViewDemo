package com.example.textviewdemo.shader.gradient_final.test3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.textviewdemo.R;
import com.example.textviewdemo.shader.gradient_final.test2.FinalGradientAnimTestActivity21;
import com.example.textviewdemo.shader.gradient_final.test2.FinalGradientAnimTestActivity22;

/**
 * GradientAnimTextViewV2 渐变，超长显示...
 */
public class FinalGradientAnimTestActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_gradient_anim_test3);
    }

    /**
     * 单个使用
     * @param v
     */
    public void onTest1(View v) {
        startActivity(new Intent(this, FinalGradientAnimTestActivity31.class));
    }

    /**
     * 列表使用
     * @param v
     */
    public void onTest2(View v) {
        startActivity(new Intent(this, FinalGradientAnimTestActivity32.class));
    }


}