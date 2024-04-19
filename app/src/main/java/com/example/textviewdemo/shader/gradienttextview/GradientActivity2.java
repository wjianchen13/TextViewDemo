package com.example.textviewdemo.shader.gradienttextview;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.textviewdemo.R;

/**
 * 渐变TextView 首尾相接
 */
public class GradientActivity2 extends AppCompatActivity {

    private MarqueeView2 marquee_horizontal;
    private MarqueeView2 marquee_horizontal_muti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gradient2);
        marquee_horizontal = findViewById(R.id.marquee_horizontal);
        marquee_horizontal_muti = findViewById(R.id.marquee_horizontal_muti);

        //水平滚动
        marquee_horizontal.setFocusable(true);
        marquee_horizontal.setFocusableInTouchMode(true);
        marquee_horizontal.setSpeed(1, (float) 5);
//        marquee_horizontal.setScrollType(SCROLL_RL);
        marquee_horizontal.setText("水平跑马灯单行1水平跑马灯单行2水平跑马灯单行3");

/*        marquee_horizontal_muti.setFocusable(true);
        marquee_horizontal_muti.setFocusableInTouchMode(true);
        marquee_horizontal_muti.setSpeed(1, (float) 5);
        marquee_horizontal_muti.setScrollType(SCROLL_RL);
        marquee_horizontal_muti.setText("水平跑马灯多行。。 水平跑马灯多行。。水平跑马灯多行。。水平跑马灯多行。。水平跑马灯多行。。水平跑马灯多行。。");*/

    }

}