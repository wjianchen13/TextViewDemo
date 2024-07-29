package com.example.textviewdemo.shader.gradient_final.test1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.textviewdemo.R;

/**
 * 1.彩虹，需要支持滚动
 * 因为考虑到需要支持非渐变，普通字体也需要滚动，所以要使用普通字体渐变的方式，外层嵌套一层ViewGroup，使用RainbowScrollTextViewV2代替
 * 查看FinalGradientAnimTestActivity2的实现
 */
public class FinalGradientAnimTestActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_gradient_anim_test1);
    }

    /**
     * 单个使用
     * @param v
     */
    public void onTest1(View v) {
        startActivity(new Intent(this, FinalGradientAnimTestActivity11.class));
    }

    /**
     * 列表使用
     * @param v
     */
    public void onTest2(View v) {
        startActivity(new Intent(this, FinalGradientAnimTestActivity12.class));
    }


}