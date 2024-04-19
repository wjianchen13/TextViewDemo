package com.example.textviewdemo.shader.lineargradient;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.textviewdemo.R;

public class LinearGradientActivity extends AppCompatActivity {

    private LinearGradientView mLinearGradientView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_gradient);
        mLinearGradientView = findViewById(R.id.lgv_test);
    }

    /**
     * TextView Shader基础使用
     * @param v
     */
    public void onTest1(View v) {
        mLinearGradientView.setGradientColor(ContextCompat.getColor(this, R.color.color_03DAC5), ContextCompat.getColor(this, R.color.color_6200EE));
        mLinearGradientView.invalidate();
    }


}