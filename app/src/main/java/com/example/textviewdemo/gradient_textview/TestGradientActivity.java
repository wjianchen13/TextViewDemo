package com.example.textviewdemo.gradient_textview;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.textviewdemo.R;

public class TestGradientActivity extends AppCompatActivity {

    private MultiGradientTextView tvTest1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_gradient);
        tvTest1 = findViewById(R.id.tv_test1);
    }

    /**
     * TextView 超过一定范围显示...
     * @param v
     */
    public void onTest1(View v) {
        tvTest1.setText("渐变，再渐变,还有哦\n换行试试");
        tvTest1.addGradientSpan("渐变", new int[]{Color.RED,Color.YELLOW,Color.BLUE});
        tvTest1.addGradientSpan( "再渐变", new int[]{Color.BLUE,Color.YELLOW,Color.RED});
        tvTest1.addGradientSpan( "还有哦\n换行试试",new int[]{Color.YELLOW,Color.RED});
        tvTest1.updateSpannable();

    }


}