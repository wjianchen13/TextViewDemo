package com.example.textviewdemo.gradient;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.textviewdemo.R;

public class GradientActivity extends AppCompatActivity {

    private TestTextView tvTest2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gradient);
        tvTest2 = findViewById(R.id.tv_test2);
    }

    /**
     * 刷新TextView
     * @param v
     */
    public void onTest1(View v) {
        tvTest2.invalidate();
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