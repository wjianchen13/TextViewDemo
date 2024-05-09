package com.example.textviewdemo.shader.gradient_final.test10;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.textviewdemo.R;
import com.example.textviewdemo.shader.gradient_final.rainbow_view.GradientAnimTextViewV2;
import com.example.textviewdemo.shader.gradient_final.rainbow_view.utils.GradientUtils;

/**
 * 测试GradientAnimTextViewV2 显示隐藏相关状态，回调
 */
public class FinalGradientAnimTestActivity10 extends AppCompatActivity {

    private FrameLayout flytTest1;
    private GradientAnimTextViewV2 tvTest1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_gradient_anim_test10);
        flytTest1 = findViewById(R.id.flyt_test1);
        tvTest1 = findViewById(R.id.tv_test1);
    }

    /**
     * 设置 父布局 invisible
     * @param v
     */
    public void onTest1(View v) {
        flytTest1.setVisibility(View.INVISIBLE);
    }

    /**
     * 设置 父布局 gone
     * @param v
     */
    public void onTest2(View v) {
        flytTest1.setVisibility(View.GONE);
    }

    /**
     * 设置 父布局 visible
     * @param v
     */
    public void onTest3(View v) {
        flytTest1.setVisibility(View.VISIBLE);
    }

    /**
     * 设置 invisible
     * @param v
     */
    public void onTest4(View v) {
        tvTest1.setVisibility(View.INVISIBLE);
    }

    /**
     * 设置 gone
     * @param v
     */
    public void onTest5(View v) {
        tvTest1.setVisibility(View.GONE);
    }

    /**
     * 设置 visible
     * @param v
     */
    public void onTest6(View v) {
        tvTest1.setVisibility(View.VISIBLE);
    }

    /**
     * 查看测试View的显示状态
     * @param v
     */
    public void onTest7(View v) {
        GradientUtils.log("isShow: " + tvTest1.isShown());
        Toast.makeText(this, "isShow: " + tvTest1.isShown(), Toast.LENGTH_SHORT).show();
    }

}