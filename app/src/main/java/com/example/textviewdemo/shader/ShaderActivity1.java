package com.example.textviewdemo.shader;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.textviewdemo.R;
import com.example.textviewdemo.gradient.GradientActivity;
import com.example.textviewdemo.gradient1.GradientActivity1;
import com.example.textviewdemo.gradient_vertical.GradientVerticalActivity;
import com.example.textviewdemo.thumb.ThumbActivity;
import com.example.textviewdemo.vertical.CenterActivity;

public class ShaderActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shader1);
    }

    /**
     * TextView Shader基础使用
     * @param v
     */
    public void onTest1(View v) {

    }


}