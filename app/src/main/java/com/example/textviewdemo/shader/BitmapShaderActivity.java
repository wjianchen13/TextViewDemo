package com.example.textviewdemo.shader;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.textviewdemo.R;

public class BitmapShaderActivity extends AppCompatActivity {

    private BitmapShaderView mBitmapShaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_shader);
        mBitmapShaderView = findViewById(R.id.bsv_test);
    }

    /**
     * TextView Shader基础使用
     * @param v
     */
    public void onTest1(View v) {
        mBitmapShaderView.invalidate();
    }


}