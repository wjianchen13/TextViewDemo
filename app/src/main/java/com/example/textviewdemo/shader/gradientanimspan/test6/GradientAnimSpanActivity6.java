package com.example.textviewdemo.shader.gradientanimspan.test6;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.textviewdemo.R;

/**
 * TextView默认值测试
 */
public class GradientAnimSpanActivity6 extends AppCompatActivity {

    private TextView tvTest1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gradient_anim_span6);
        tvTest1 = findViewById(R.id.tv_test1);
    }

    /**
     * 测试
     * @param v
     */
    public void onTest1(View v) {
        String str = "测试滚动和渐变同时存在的情况，需要设置singleLine=true，设置之后Shader不起作用";
        tvTest1.setText(str);
    }


}