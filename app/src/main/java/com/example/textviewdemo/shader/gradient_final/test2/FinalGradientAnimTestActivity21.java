package com.example.textviewdemo.shader.gradient_final.test2;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.textviewdemo.BaseApp;
import com.example.textviewdemo.R;
import com.example.textviewdemo.shader.gradient_final.rainbow_view.RainbowScrollTextViewV2;
import com.example.textviewdemo.shader.gradient_final.rainbow_view.utils.GradientUtils;
import com.example.textviewdemo.thumb.Utils;

/**
 * 1.彩虹，需要支持滚动
 */
public class FinalGradientAnimTestActivity21 extends AppCompatActivity {

    private RainbowScrollTextViewV2 tvTest1;
    private RainbowScrollTextViewV2 tvTest2;
    private RainbowScrollTextViewV2 tvTest3;
    private RainbowScrollTextViewV2 tvTest4;
    private RainbowScrollTextViewV2 tvTest5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_gradient_anim_test21);
        tvTest1 = findViewById(R.id.tv_test1);
        tvTest2 = findViewById(R.id.tv_test2);
        tvTest3 = findViewById(R.id.tv_test3);
        tvTest4 = findViewById(R.id.tv_test4);
        tvTest5 = findViewById(R.id.tv_test5);
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

    /**
     * 滚动模式 滚动+渐变 代码设置
     * @param v
     */
    public void onTest2(View v) {
        String str = "测试滚动和渐变同时存在的情况，需要设置singleLine=true，设置之后Shader不起作用";
        int color1 = ContextCompat.getColor(this, R.color.cffde3d32);
        int color2 = ContextCompat.getColor(this, R.color.cfffeb702);
        int color3 = ContextCompat.getColor(this, R.color.cff80ff00);
        int color4 = ContextCompat.getColor(this, R.color.cff00bfcb);
        tvTest2.setContent(str);
    }

    /**
     * 开始滚动
     * @param v`
     */
    public void onTest3(View v) {
//        String str = "测试滚动和渐变同时存在的情况，需要设置singleLine=true，设置之后Shader不起作用";
        String str = "测试滚动和渐变";
        int color1 = ContextCompat.getColor(this, R.color.cffde3d32);
        int color2 = ContextCompat.getColor(this, R.color.cfffeb702);
        int color3 = ContextCompat.getColor(this, R.color.cff80ff00);
        int color4 = ContextCompat.getColor(this, R.color.cff00bfcb);
        tvTest3.setContent(str, new int[]{color1, color2, color3, color4});
    }

    /**
     * 停止滚动
     * @param v
     */
    public void onTest4(View v) {
        tvTest3.stopAnim();
    }

    /**
     * 设置新内容
     * @param v
     */
    public void onTest5(View v) {
        tvTest3.setContent("测试滚动和渐变同时存在的情况，需要设置singleLine=true，设置之后Shader不起作用");
    }

    /**
     * 设置渐变内容
     * @param v
     */
    public void onTest6(View v) {
        SpannableStringBuilder sContent = new SpannableStringBuilder();
        int[] colors = new int[] {
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cffde3d32),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cfffeb702),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cff80ff00),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cff00bfcb)
        };
        sContent.append(GradientUtils.getGradientText(this, "测试滚动和渐变同时存在的情况，需要设置singleLine=true，设置之后Shader不起作用", colors, sContent.length(), 0));
        tvTest3.setContent(sContent);
    }

    /**
     * 设置新内容 短
     * @param v
     */
    public void onTest7(View v) {
        tvTest3.setContent("测试滚动和渐变");
    }

    /**
     * 设置渐变内容 短
     * @param v
     */
    public void onTest8(View v) {
        SpannableStringBuilder sContent = new SpannableStringBuilder();
        int[] colors = new int[] {
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cffde3d32),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cfffeb702),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cff80ff00),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cff00bfcb)
        };
        sContent.append(GradientUtils.getGradientText(this, "测试滚动和渐", colors, sContent.length(), 0));
        tvTest3.setContent(sContent);
    }

    /**
     * 常规字体式样
     * @param v
     */
    public void onTest9(View v) {
        String str = "测试滚动和渐变同时存在的情况，需要设置singleLine=true，设置之后Shader不起作用";
        int color1 = ContextCompat.getColor(this, R.color.cffde3d32);
        int color2 = ContextCompat.getColor(this, R.color.cfffeb702);
        int color3 = ContextCompat.getColor(this, R.color.cff80ff00);
        int color4 = ContextCompat.getColor(this, R.color.cff00bfcb);
        tvTest4.setContent(str, new int[]{color1, color2, color3, color4});
    }


    /**
     * 加粗字体式样
     * @param v
     */
    public void onTest10(View v) {
        String str = "测试滚动和渐变同时";
        int color1 = ContextCompat.getColor(this, R.color.cffde3d32);
        int color2 = ContextCompat.getColor(this, R.color.cfffeb702);
        int color3 = ContextCompat.getColor(this, R.color.cff80ff00);
        int color4 = ContextCompat.getColor(this, R.color.cff00bfcb);
        tvTest5.setContent(str, new int[]{color1, color2, color3, color4});
    }

}