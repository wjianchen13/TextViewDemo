package com.example.textviewdemo.shader.gradient_final.test2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.textviewdemo.R;
import com.example.textviewdemo.shader.gradient_final.test1.FinalGradientAnimTestActivity11;
import com.example.textviewdemo.shader.gradient_final.test1.FinalGradientAnimTestActivity12;

/**
 * RainbowScrollTextViewV2 彩虹，需要支持滚动
 */
public class FinalGradientAnimTestActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_gradient_anim_test2);
    }

    /**
     * 单个使用
     * @param v
     */
    public void onTest1(View v) {
        startActivity(new Intent(this, FinalGradientAnimTestActivity21.class));
    }

    /**
     * 列表使用
     * @param v
     */
    public void onTest2(View v) {
        startActivity(new Intent(this, FinalGradientAnimTestActivity22.class));
    }


}