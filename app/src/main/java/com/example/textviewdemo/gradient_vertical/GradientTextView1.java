package com.example.textviewdemo.gradient_vertical;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.Choreographer;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.textviewdemo.R;
import com.example.textviewdemo.gradient.AnimateDirection;
import com.example.textviewdemo.gradient.TranslateSpeed;

/**
 * 测试字体渐变
 */
class GradientTextView1 extends AppCompatTextView {

    /**
     * direction: from left to right
     */
    public static final int leftToRight = 1;

    /**
     * direction: from top to bottom
     */
    public static final int topToBottom = 2;

    /**
     * translate speed slow
     */
    public static final int slow = 20;

    /**
     * translate speed normal
     */
    public static final int normal = 10;

    /**
     * translate speed fast
     */
    public static final int fast = 5;

    private LinearGradient mLinearGradient= null;
    private Matrix mGradientMatrix = new Matrix();

    @ColorInt
    private int startColor = 0;

    @ColorInt
    private int endColor = 0;

    @AnimateDirection
    private int direction = 0;

    @TranslateSpeed
    private int translateSpeed = 0;

    private boolean translateAnimate = false;

    private  Choreographer mChoreographer;

    private float translate = 0f;

    private int[] colors = new int[]{};

    public GradientTextView1(@NonNull Context context) {
        super(context);
    }

    public GradientTextView1(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientTextView1(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GradientTextView);
            startColor = a.getColor(R.styleable.GradientTextView_gradient_startColor, getResources().getColor(R.color.color_03DAC5));
            endColor = a.getColor(R.styleable.GradientTextView_gradient_endColor, getResources().getColor(R.color.color_6200EE));
            translateAnimate = a.getBoolean(R.styleable.GradientTextView_gradient_animate, false);
            translateSpeed = a.getInt(R.styleable.GradientTextView_gradient_speed, normal);

            direction = a.getInt(R.styleable.GradientTextView_gradient_direction, leftToRight);
            initColors();
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int expandSpec = MeasureSpec.makeMeasureSpec(500, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        getPaint().setShader(mLinearGradient);
        super.onDraw(canvas);
        if (translateAnimate) {
            if (direction == leftToRight) {
                translate += getMeasuredWidth() * 2 / translateSpeed;
                if (translate > 0) {
                    translate = -getMeasuredWidth();
                }
                mGradientMatrix.setTranslate(translate, 0f);
            } else {
                translate += getMeasuredHeight() / translateSpeed;
                if (translate > getMeasuredHeight()) {
                    translate = 0f;
                }
                mGradientMatrix.setTranslate(0f, translate);
            }
            if(mLinearGradient != null) {
                mLinearGradient.setLocalMatrix(mGradientMatrix);
            }
            postInvalidateDelayed(100);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setupGradient();
    }

    private void setupGradient() {
        if (getMeasuredWidth() <= 0 || getMeasuredHeight() <= 0) {
            return ;
        }
        switch (direction) {
            case leftToRight:
                if (translateAnimate) {
                    mLinearGradient = new LinearGradient(0f, 0f, getMeasuredWidth() * 2, 0f, colors, null, Shader.TileMode.CLAMP);
                } else {
                    mLinearGradient = new LinearGradient(0f, 0f, getMeasuredWidth() * 2, 0f, startColor, endColor, Shader.TileMode.CLAMP);
                }
                break;
            case topToBottom:
                if (translateAnimate) {
                    mLinearGradient = new LinearGradient(0f, 0f, 0f, getMeasuredHeight(), colors, null, Shader.TileMode.CLAMP);
                } else {
                    mLinearGradient = new LinearGradient(0f, 0f, 0f, getMeasuredHeight(), startColor, endColor, Shader.TileMode.CLAMP);
                }
                break;
        }
    }

    private void  setColor(@ColorInt int startColor, @ColorInt int endColor) {
        this.startColor = startColor;
        this.endColor = endColor;
        initColors();
    }

    private void  initColors() {
        if (translateAnimate) {
            colors = new int[]{startColor, endColor, startColor, endColor, startColor, endColor, startColor, endColor, startColor};
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        translateAnimate = false;
    }

}