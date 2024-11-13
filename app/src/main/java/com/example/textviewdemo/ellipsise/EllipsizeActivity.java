package com.example.textviewdemo.ellipsise;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.textviewdemo.R;

public class EllipsizeActivity extends AppCompatActivity {

    private TextView tvTest1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ellipsize);
        tvTest1 = findViewById(R.id.tv_test1);
    }

    /**
     * TextView 超过一定范围显示...
     * @param v
     */
    public void onTest1(View v) {
        tvTest1.setText("hellohellohellohellohellohellohellohellohellohellohello");
    }


}