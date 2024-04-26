package com.example.textviewdemo.shader.gradient_final;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.textviewdemo.R;
import com.example.textviewdemo.shader.gradient_final.manager.AnimManager;
import com.example.textviewdemo.shader.gradient_final.test1.FinalGradientAnimTestActivity1;
import com.example.textviewdemo.shader.gradient_final.test2.FinalGradientAnimTestActivity2;
import com.example.textviewdemo.shader.gradient_final.test3.FinalGradientAnimTestActivity3;
import com.example.textviewdemo.shader.gradient_final.test4.FinalGradientAnimTestActivity4;
import com.example.textviewdemo.shader.gradient_final.test5.FinalGradientAnimTestActivity5;

/**
 * 彩虹字体最终版本
 * 使用例子
 * 1.彩虹，需要支持滚动
 *   单个控件
 *   RecyclerView中使用
 * 2.彩虹，超出显示...
 * 3.彩虹，换行
 */
public class FinalGradientAnimActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_gradient_anim);
    }

    /**
     * GradientAnimTextViewV2 彩虹，需要支持滚动
     * @param v
     */
    public void onTest1(View v) {
        startActivity(new Intent(this, FinalGradientAnimTestActivity1.class));
    }

    /**
     * RainbowScrollTextViewV2 彩虹，需要支持滚动
     * @param v
     */
    public void onTest2(View v) {
        startActivity(new Intent(this, FinalGradientAnimTestActivity2.class));
    }

    /**
     * GradientAnimTextViewV2 渐变，超长显示...
     * @param v
     */
    public void onTest3(View v) {
        startActivity(new Intent(this, FinalGradientAnimTestActivity3.class));
    }

    /**
     * GradientAnimTextViewV2 渐变 富文本
     * @param v
     */
    public void onTest4(View v) {
        startActivity(new Intent(this, FinalGradientAnimTestActivity4.class));
    }

    /**
     * RainbowScrollTextViewV2 使用，检查生命周期，是否回收
     * @param v
     */
    public void onTest5(View v) {
        startActivity(new Intent(this, FinalGradientAnimTestActivity5.class));
//        AnimManager.getInstance().logAllView();
    }

    /**
     * 测试控件
     * @param v
     */
    public void onTest6(View v) {
        startActivity(new Intent(this, FinalTestViewActivity.class));
    }

    /**
     * 基础测试
     * @param v
     */
    public void onTest7(View v) {
        startActivity(new Intent(this, FinalTestActivity.class));
    }


}