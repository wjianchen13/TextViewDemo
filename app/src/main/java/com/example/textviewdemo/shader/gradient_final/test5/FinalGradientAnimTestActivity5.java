package com.example.textviewdemo.shader.gradient_final.test5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.textviewdemo.R;
import com.example.textviewdemo.shader.gradient_final.rainbow_view.manager.AnimManager;

/**
 * RainbowScrollTextViewV2 使用，检查生命周期，是否回收
 */
public class FinalGradientAnimTestActivity5 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_gradient_anim_test5);
    }

    /**
     * 单个使用
     * @param v
     */
    public void onTest1(View v) {
        startActivity(new Intent(this, FinalGradientAnimTestActivity51.class));
    }

    /**
     * 列表使用
     * @param v
     */
    public void onTest2(View v) {
        startActivity(new Intent(this, FinalGradientAnimTestActivity52.class));
    }

    /**
     * 回收测试
     * @param v
     */
    public void onTest3(View v) {
        AnimManager.getInstance().logAllView();
    }

}