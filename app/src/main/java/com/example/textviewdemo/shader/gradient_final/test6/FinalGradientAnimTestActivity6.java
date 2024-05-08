package com.example.textviewdemo.shader.gradient_final.test6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.textviewdemo.R;
import com.example.textviewdemo.shader.gradient_final.rainbow_view.manager.AnimManager;

/**
 * GradientAnimTextViewV2 渐变，超长显示...
 */
public class FinalGradientAnimTestActivity6 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_gradient_anim_test6);
    }

    /**
     * 单个使用
     * @param v
     */
    public void onTest1(View v) {
        startActivity(new Intent(this, FinalGradientAnimTestActivity61.class));
    }

    /**
     * 列表使用
     * @param v
     */
    public void onTest2(View v) {
        startActivity(new Intent(this, FinalGradientAnimTestActivity62.class));
    }

    /**
     * 回收测试
     * @param v
     */
    public void onTest3(View v) {
        AnimManager.getInstance().logAllView();
    }


}