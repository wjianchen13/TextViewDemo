package com.example.textviewdemo.gradient_vertical;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.textviewdemo.R;

/**
 * 既要滚动，又要渐变是不行的，当设置了android:singleLine="true"的时候，渐变就会失效
 */
public class GradientVerticalActivity extends AppCompatActivity {

    private GradientVerticalTextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gradient_vertical);
        tvTest = findViewById(R.id.tv_test2);
    }

    /**
     * 刷新TextView
     * @param v
     */
    public void onTest1(View v) {
        tvTest = findViewById(R.id.tv_test2);
        tvTest.setText("你 he 12asdkfjkasdfjaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaakasdk");
    }

    /**
     *
     * @param v
     */
    public void onTest2(View v) {

    }

    /**
     *
     * @param v
     */
    public void onTest3(View v) {

    }

}