package com.example.textviewdemo.gradient1;

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
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import androidx.annotation.ColorInt;

import com.example.textviewdemo.R;

import java.util.ArrayList;
import java.util.List;

public class MarqueeView extends TextView {

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
    //从下往上
    public static final int SCROLL_BT = 2;
    //从右往左
    public static final int SCROLL_RL = 3;

    //垂直滚动需要的数据
    private float lineSpace;
    private float verticalSpeed = 0.1f;
    private List<String> textList = new ArrayList<>();
    private StringBuilder textBuilder = new StringBuilder();

    //水平滚动需要的数据
    private float horizontalSpeed = 2f;
    private Rect rect;

    private Paint paint;
    //默认不滚动
    private int scrollType;
    //每次更滚动的距离
    private float scrollStep = 0f;

    private LinearGradient mLinearGradient = null;
    private Matrix mGradientMatrix = new Matrix();
    private int[] colors = new int[]{};

    @ColorInt
    private int startColor = 0;

    @ColorInt
    private int endColor = 0;

    private int direction = leftToRight;

    private boolean translateAnimate = true;
    private ValueAnimator mAnimator;
    private float translate = 0f;


    public MarqueeView(Context context) {
        this(context, null);
    }

    public MarqueeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MarqueeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MarqueeView);
        scrollType = array.getInt(R.styleable.MarqueeView_scrollType, SCROLL_NO);
        int color = array.getColor(R.styleable.MarqueeView_textColor, 0x000000);
        lineSpace = array.getInt(R.styleable.MarqueeView_lineSpace, 0);
        array.recycle();
        setSpeed(0, 1f);
        setSpeed(1, 5f);
        paint = getPaint();
        paint.setColor(color);
        rect = new Rect();
        startColor = getResources().getColor(R.color.color_03DAC5);
        endColor = getResources().getColor(R.color.color_6200EE);
        colors = new int[]{startColor, endColor, startColor, endColor, startColor};
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
        if (0 == type) {
            verticalSpeed = speed;
        } else {
            horizontalSpeed = speed;
        }
        invalidate();
    }

    /**
     * 设置行高
     *
     * @param lineSpace 行高
     */
    public void setLineSpace(float lineSpace) {
        this.lineSpace = lineSpace;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        String text = getText().toString();
        if (!TextUtils.isEmpty(text) && scrollType == SCROLL_BT) {
            //由下往上滚动需要测量高度
            setTextList(widthMeasureSpec, text);
        }
        mLinearGradient = new LinearGradient(0f, 0f, getMeasuredWidth(), 0f, colors, null, Shader.TileMode.CLAMP);
    }

    /**
     * 根据TextView宽度和字体大小，计算显示的行数。
     *
     * @param widthMeasureSpec 测量模式
     * @param text             文本
     */
    private void setTextList(int widthMeasureSpec, String text) {
        textList.clear();
        float width = MeasureSpec.getSize(widthMeasureSpec);
        float length = 0;
        for (int i = 0; i < text.length(); i++) {
            if (length <= width) {
                textBuilder.append(text.charAt(i));
                length += paint.measureText(text.substring(i, i + 1));
                if (i == text.length() - 1) {
                    if (length <= width) {
                        textList.add(textBuilder.toString());
                    } else {
                        if (textBuilder.toString().length() == 1) {
                            //每行最多显示一个字
                            textList.add(text.substring(text.length() - 1));
                        } else {
                            //去掉最后一个字，否则最后一个字显示不完整
                            textList.add(textBuilder.toString().substring(0, textBuilder.toString().length() - 1));
                            //最后一个字单独一行
                            textList.add(text.substring(text.length() - 1));
                        }
                    }
                }
            } else {
                if (textBuilder.toString().length() == 1) {
                    //每行最多显示一个字
                    textList.add(textBuilder.toString());
                    textBuilder.delete(0, textBuilder.length());
                    i--;
                    length = 0;
                } else {
                    //去掉最后一个字，否则最后一个字显示不完整
                    textList.add(textBuilder.toString().substring(0, textBuilder.toString().length() - 1));
                    textBuilder.delete(0, textBuilder.length() - 1);
                    i--;
                    length = paint.measureText(text.substring(i, i + 1));
                }
            }
        }
        //清空textBuilder
        textBuilder.delete(0, textBuilder.length());
    }

    @Override
    public void onDraw(Canvas canvas) {
        getPaint().setShader(mLinearGradient);
        String text = getText().toString();
        if (TextUtils.isEmpty(text)) {
            super.onDraw(canvas);
            return;
        }
        switch (scrollType) {
            case SCROLL_NO:
                super.onDraw(canvas);
                break;
            case SCROLL_BT:
                //从下往上滚动，首次不显示文字，后续从下往上显示
                float textSize = paint.getTextSize();
                for (int i = 0; i < textList.size(); i++) {
                    float currentY = getHeight() + (i + 1) * textSize - scrollStep;
                    if (i > 0) {
                        currentY = currentY + i * lineSpace;
                    }
                    if (textList.size() > 1) {
                        canvas.drawText(textList.get(i), 0, currentY, paint);
                    } else {
                        canvas.drawText(textList.get(i), getWidth() / 2 - paint.measureText(text) / 2, currentY, paint);
                    }
                }
                scrollStep = scrollStep + verticalSpeed;
                if (scrollStep >= getHeight() + textList.size() * textSize + (textList.size() - 1) * lineSpace) {
                    scrollStep = 0;
                }
                invalidate();
                break;
            case SCROLL_RL:
                //从右向左滚动，首次不显示文字，后续每次往左偏移speed像素
                paint.getTextBounds(text, 0, text.length(), rect);
                int textWidth = rect.width();
                int viewWidth = getWidth();
                float currentX = viewWidth - scrollStep;
                canvas.drawText(text, currentX, getHeight() / 2 + (paint.getFontMetrics().descent - paint.getFontMetrics().ascent) / 2 - paint.getFontMetrics().descent, paint);
                scrollStep = scrollStep + horizontalSpeed;
                if (scrollStep >= viewWidth + textWidth) {
                    scrollStep = 0;
                }
                invalidate();
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
                    mLinearGradient = new LinearGradient(0f, 0f, getMeasuredWidth() * 1, 0f, colors, null, Shader.TileMode.CLAMP);
                } else {
                    mLinearGradient = new LinearGradient(0f, 0f, getMeasuredWidth() * 1, 0f, startColor, endColor, Shader.TileMode.CLAMP);
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

}
