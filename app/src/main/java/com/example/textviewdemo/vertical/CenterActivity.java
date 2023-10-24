package com.example.textviewdemo.vertical;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.textviewdemo.R;


/**
 * 垂直居中裁剪TextView显示
 */
public class CenterActivity extends AppCompatActivity {

    private VerticalCenterTextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center);
        tvTest = findViewById(R.id.tv_test);
        tvTest.setText("你 he 12asdkfjkasdfjkasdk");
    }
}
