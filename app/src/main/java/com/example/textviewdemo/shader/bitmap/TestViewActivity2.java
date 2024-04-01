package com.example.textviewdemo.shader.bitmap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.textviewdemo.R;
import com.example.textviewdemo.thumb.ThumbActivity;

/**
 * Bitmap生成测试
 */
public class TestViewActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view2);
    }

    /**
     * TextView Shader基础使用
     * @param v
     */
    public void onTest1(View v) {
        startActivity(new Intent(this, ThumbActivity.class));
    }

}