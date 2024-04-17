package com.example.textviewdemo.gradient2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

import androidx.annotation.ColorInt;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.textviewdemo.R;
import com.example.textviewdemo.thumb.Utils;

public class MarqueeView2 extends AppCompatTextView {

    /**
     * direction: from left to right
     */
    public static final int leftToRight = 1;

    /**
     * direction: from top to bottom
     */
    public static final int topToBottom = 2;

    //滚动方向
    //不滚动
    public static final int SCROLL_NO = 1;

    //从右往左
    public static final int SCROLL_RL = 3;

    //水平滚动需要的数据
    private float horizontalSpeed = 2f;
    private Rect rect;

    private Paint paint;
    //默认不滚动
    private int scrollType;
    //每次更滚动的距离
    private float scrollStep = 0f;
    //每次更滚动的距离
    private float scrollStep2 = 0f;

    private LinearGradient mLinearGradient = null;
    private Matrix mGradientMatrix = new Matrix();
    private int[] colors = new int[]{};

    /**
     * 字体滚动间距
     */
    private int mSpace;

    @ColorInt
    private int startColor = 0;

    @ColorInt
    private int endColor = 0;

    private int direction = leftToRight;

    private boolean translateAnimate = true;
    private ValueAnimator mAnimator;
    private float translate = 0f;
    private float  mTextWidth;

    private int mOffset1;
    private int mOffset2;

    /**
     * 是否是从右向左方向
     */
    private boolean isRtL = true;

    public MarqueeView2(Context context) {
        this(context, null);
    }

    public MarqueeView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MarqueeView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MarqueeView);
        scrollType = array.getInt(R.styleable.MarqueeView_scrollType, SCROLL_NO);
        int color = array.getColor(R.styleable.MarqueeView_textColor, 0x000000);
        array.recycle();
        setSpeed(1, 5f);
        paint = getPaint();
        paint.setColor(color);
        rect = new Rect();
        startColor = getResources().getColor(R.color.color_03DAC5);
        endColor = getResources().getColor(R.color.color_6200EE);
        colors = new int[]{startColor, endColor, startColor, endColor, startColor};
        mSpace = 200;
//        mLinearGradient = new LinearGradient(0f, 0f, getMeasuredWidth(), 0f, colors, null, Shader.TileMode.CLAMP);
    }

    @Override
    public void setTextColor(int color) {
        super.setTextColor(color);
        paint.setColor(color);
    }

    /**
     * 设置滚动方向
     *
     * @param scrollType 滚动方向
     */
    public void setScrollType(int scrollType) {
        this.scrollType = scrollType;
        invalidate();
    }

    /**
     * 设置滚动速度
     *
     * @param type 滚动方向 0垂直 1水平
     */
    public void setSpeed(int type, float speed) {
        horizontalSpeed = speed;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        initText();
        mLinearGradient = new LinearGradient(0f, 0f, getMeasuredWidth(), 0f, colors, null, Shader.TileMode.CLAMP);
    }

    private void initText() {
        if(paint != null) {
            String text = getText().toString();
            mTextWidth = paint.measureText(text);
            if(mTextWidth < getMeasuredWidth()) { // 不滚动
                scrollType = SCROLL_NO;
            } else { // 滚动
                scrollType = SCROLL_RL;
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        String text = getText().toString();
        if (TextUtils.isEmpty(text)) {
            super.onDraw(canvas);
            return;
        }
        if(mLinearGradient != null)
            getPaint().setShader(mLinearGradient);
        switch (scrollType) {
            case SCROLL_NO:
                super.onDraw(canvas);
                break;
            case SCROLL_RL:
                if(isRtL) {
                    paint.getTextBounds(text, 0, text.length(), rect);
                    int textWidth = rect.width();
                    float textWidth1 = paint.measureText(text);
                    Utils.log("=================> textWidth: " + textWidth + "   textWidth1: " + textWidth1 + "   getWidth(): " + getWidth());
                    int distance = textWidth + mSpace;
                    float currentX = (getWidth() - getPaddingRight()) - textWidth + mOffset1;

                    canvas.drawText(text, currentX, getHeight() / 2 + (paint.getFontMetrics().descent - paint.getFontMetrics().ascent) / 2 - paint.getFontMetrics().descent, paint);
                    if (mOffset1 > distance) {
                        Utils.log("=========================================> getWidth(): " + getWidth() + "   getPaddingRight(): " + getPaddingRight() + "   textWidth: " + textWidth + "   distance: " + distance);
                        mOffset1 = -distance;
                    }
                    float currentX2 = (getWidth() - getPaddingRight()) - textWidth - distance + mOffset2;
                    Utils.log("=================> currentX: " + currentX + "   currentX2: " + currentX2);
                    canvas.drawText(text, currentX2, getHeight() / 2 + (paint.getFontMetrics().descent - paint.getFontMetrics().ascent) / 2 - paint.getFontMetrics().descent, paint);
                    if (mOffset2 > 2 * distance) {
                        mOffset2 = 0;
                    }
                } else {
                    //从右向左滚动，首次不显示文字，后续每次往左偏移speed像素
                    paint.getTextBounds(text, 0, text.length(), rect);
                    int textWidth = rect.width();
                    float textWidth1 = paint.measureText(text);
                    Utils.log("=================> textWidth: " + textWidth + "   textWidth1: " + textWidth1);
                    int viewWidth = getWidth();
                    int distance = textWidth + mSpace;
                    float currentX = getPaddingLeft() - mOffset1;
                    canvas.drawText(text, currentX, getHeight() / 2 + (paint.getFontMetrics().descent - paint.getFontMetrics().ascent) / 2 - paint.getFontMetrics().descent, paint);
                    if (mOffset1 > distance) {
                        mOffset1 = -(distance);
                    }
                    float currentX2 = getPaddingLeft() + distance - mOffset2;
                    canvas.drawText(text, currentX2, getHeight() / 2 + (paint.getFontMetrics().descent - paint.getFontMetrics().ascent) / 2 - paint.getFontMetrics().descent, paint);
                    if (mOffset2 > 2 * distance) {
                        mOffset2 = 0;
                    }
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setupGradient();
        startGradient(getMeasuredWidth());
        System.out.println("=================================> onSizeChanged width: " + getMeasuredWidth() + "   height: " + getMeasuredHeight());
    }

    public void startGradient(int width) {
        if(translateAnimate) {
            if(mAnimator != null) {
                mAnimator.cancel();
                mAnimator = null;
            }
            mAnimator = ValueAnimator.ofInt(0, width);
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (int) animation.getAnimatedValue();
                    if (translateAnimate) {
                        if (direction == leftToRight) {
                            translate = value - getMeasuredWidth();
                            mGradientMatrix.setTranslate(translate, 0f);
                        } else {
                            translate = getMeasuredHeight() * 2  - value;
                            mGradientMatrix.setTranslate(0f, translate);
                        }
                        if(mLinearGradient != null) {
                            mLinearGradient.setLocalMatrix(mGradientMatrix);
                        }
                        if(isRtL) {
                            mOffset1 += horizontalSpeed;
                            mOffset2 += horizontalSpeed;
                        } else {
                            mOffset1 += horizontalSpeed;
                            mOffset2 += horizontalSpeed;
                        }
                        invalidate();
                    }
                }
            });
            ValueAnimator.setFrameDelay(50L);
            mAnimator.setDuration(10000);
            mAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mAnimator.setRepeatMode(ValueAnimator.RESTART);
            mAnimator.setInterpolator(new LinearInterpolator());
            mAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {


                }

                @Override
                public void onAnimationStart(Animator animation) {

                }
            });
            mAnimator.start();
        }
    }

    private void setupGradient() {
        if (getMeasuredWidth() <= 0 || getMeasuredHeight() <= 0) {
            return ;
        }
        switch (direction) {
            case leftToRight:
                if (translateAnimate) {
                    mLinearGradient = new LinearGradient(0f, 0f, getMeasuredWidth() * 1, 0f, colors, null, Shader.TileMode.REPEAT);
                } else {
                    mLinearGradient = new LinearGradient(0f, 0f, getMeasuredWidth() * 1, 0f, startColor, endColor, Shader.TileMode.REPEAT);
                }
                break;
            case topToBottom:
                if (translateAnimate) {
                    mLinearGradient = new LinearGradient(0f, 0f, 0f, getMeasuredHeight(), colors, null, Shader.TileMode.REPEAT);
                } else {
                    mLinearGradient = new LinearGradient(0f, 0f, 0f, getMeasuredHeight(), startColor, endColor, Shader.TileMode.REPEAT);
                }
                break;
        }
    }

}
