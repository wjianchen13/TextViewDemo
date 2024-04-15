package com.example.textviewdemo.gradient1;

import static com.example.textviewdemo.gradient1.MarqueeView.SCROLL_BT;
import static com.example.textviewdemo.gradient1.MarqueeView.SCROLL_RL;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.textviewdemo.R;

public class GradientActivity1 extends AppCompatActivity {

    private MarqueeView marquee_horizontal;
    private MarqueeView marquee_horizontal_muti;
    private MarqueeView marquee_vertical;
    private MarqueeView marquee_vertical_muti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gradient1);
        marquee_horizontal = findViewById(R.id.marquee_horizontal);
        marquee_horizontal_muti = findViewById(R.id.marquee_horizontal_muti);
        marquee_vertical = findViewById(R.id.marquee_vertical);
        marquee_vertical_muti = findViewById(R.id.marquee_vertical_muti);

        //水平滚动
        marquee_horizontal.setFocusable(true);
        marquee_horizontal.setFocusableInTouchMode(true);
        marquee_horizontal.setSpeed(1, (float) 5);
        marquee_horizontal.setScrollType(SCROLL_RL);
        marquee_horizontal.setText("水平跑马灯单行");

        marquee_horizontal_muti.setFocusable(true);
        marquee_horizontal_muti.setFocusableInTouchMode(true);
        marquee_horizontal_muti.setSpeed(1, (float) 5);
        marquee_horizontal_muti.setScrollType(SCROLL_RL);
        marquee_horizontal_muti.setText("水平跑马灯多行。。 水平跑马灯多行。。水平跑马灯多行。。水平跑马灯多行。。水平跑马灯多行。。水平跑马灯多行。。");

        marquee_vertical.setFocusable(true);
        marquee_vertical.setFocusableInTouchMode(true);
        marquee_vertical.setSpeed(0, (float) 3);
        marquee_vertical.setScrollType(SCROLL_BT);
        marquee_vertical.setText("垂直跑马灯单行");

        marquee_vertical_muti.setFocusable(true);
        marquee_vertical_muti.setFocusableInTouchMode(true);
        marquee_vertical_muti.setSpeed(0, (float) 3);
        marquee_vertical_muti.setScrollType(SCROLL_BT);
        marquee_vertical_muti.setText("垂直跑马灯多行。。。垂直跑马灯多行。。。垂直跑马灯多行。。。垂直跑马灯多行。。。垂直跑马灯多行。。。垂直跑马灯多行。。。垂直跑马灯多行。。。垂直跑马灯多行。。。");
    }

}