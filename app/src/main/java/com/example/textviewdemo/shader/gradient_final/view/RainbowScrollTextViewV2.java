package com.example.textviewdemo.shader.gradient_final.view;

import static android.view.Gravity.CENTER;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.textviewdemo.R;
import com.example.textviewdemo.shader.gradientanimspan.GradientAnimTextView;
import com.example.textviewdemo.shader.gradientanimspan.test5.ScreenUtils;


/**
 * 彩虹字体
 *
 */
public class RainbowScrollTextViewV2 extends FrameLayout {

    /**
     * 常规
     */
    public static final int TYPE_NORMAL = 0;

    /**
     * 滚动
     */
    public static final int TYPE_SCROLL = 1;

    /**
     * 不滚动，结尾显示省略号
     */
    public static final int TYPE_END = 2;

    private Context mContext;
    private GradientAnimTextViewV2 tv;

    /**
     * 字体大小
     */
    private int textColor;

    /**
     * id字体颜色
     */
    private int textSize;

    /**
     * 宽度自适应
     */
    private boolean mAutoSize;

    /**
     * 最大宽度
     */
    private int mMaxWidth;

    /**
     * 对齐方式
     * 0 默认，不设置
     * 1 居左对齐
     * 2 居中对齐
     */
    private int mGravity;

    /**
     * 文字方向
     * 0 默认，不设置
     * 1 左向右
     */
    private int mTextDirection;

    /**
     * 0：常规模式
     * 1：滚动模式
     * 2: 省略号
     */
    private int mMode;

    public RainbowScrollTextViewV2(@NonNull Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public RainbowScrollTextViewV2(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initAttrs(context, attrs);
        init();
    }

    public RainbowScrollTextViewV2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initAttrs(context, attrs);
        init();
    }

    /**
     * 设置显示内容
     * @param text 显示内容
     * @param rainbow 是否有彩虹
     * @param colors 彩虹颜色
     */
    public void setContent(CharSequence text, boolean rainbow, @ColorInt int... colors) {
        if(rainbow) {
            if (tv != null) {
                tv.setScrollMode();
                tv.setContent(text, colors);
                tv.setSelected(false);
            }
        } else {
            if (tv != null) {
                tv.setNormalMode();
                tv.setContent(text);
                tv.setSelected(true);
            }
        }
    }

    public void stopAnim() {
        if(tv != null) {
            tv.stopAnim();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnim();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = widthMeasureSpec;
        if(mAutoSize && mMaxWidth > 0) {
            final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
            final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
            if (widthMode == MeasureSpec.AT_MOST && widthSize > mMaxWidth) {
                expandSpec = MeasureSpec.makeMeasureSpec(mMaxWidth, MeasureSpec.AT_MOST);
            }
        }
        super.onMeasure(expandSpec, heightMeasureSpec);
    }

    /**
     * init attr
     *
     * @param context
     * @param attrs
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.rainbow_view);
        textColor = a.getColor(R.styleable.rainbow_view_marquee_text_color, 0);
        textSize = a.getDimensionPixelOffset(R.styleable.rainbow_view_marquee_text_size, 0);
        mAutoSize = a.getBoolean(R.styleable.rainbow_view_marquee_auto_width, false);
        mGravity = a.getInt(R.styleable.rainbow_view_marquee_layout_gravity, 0);
        mTextDirection = a.getInt(R.styleable.rainbow_view_marquee_text_direction, 0);
        mMaxWidth = a.getDimensionPixelOffset(R.styleable.rainbow_view_marquee_max_size, ScreenUtils.getScreenWidth(mContext));
        a.recycle();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_rainbow_scroll_v2, this, true);
        tv = findViewById(R.id.tv);
        if (textColor != 0)
            tv.setTextColor(textColor);
        if (textSize != 0)
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        LayoutParams layoutParams = (LayoutParams)tv.getLayoutParams();
        if(layoutParams != null) {
            if(mGravity == 1) {
                layoutParams.gravity = Gravity.LEFT;
                tv.setLayoutParams(layoutParams);
            } else if(mGravity == 2) {
                layoutParams.gravity = CENTER;
                tv.setLayoutParams(layoutParams);
            }
        }
//        if(mTextDirection == 1) {
//            tv.setTextDirection(View.TEXT_DIRECTION_LTR);
//        }


    }

//    public void setContent(String str) {
//        if (tv != null) {
//            tv.setText(str);
//            tv.setSelected(true);
//        }
//    }

//    public void setContent(CharSequence text) {
//        if (tv != null&&text!=null) {
//            tv.setText(text);
//            tv.setSelected(true);
//        }
//    }

    public void setTypeface(Typeface tf){
        if (tv != null) {
            tv.setTypeface(tf);
        }
    }
    /**
     * 静止不动*/
    public void setStatic(){
        if(tv!=null){
            tv.setSelected(false);
            tv.setEllipsize(TextUtils.TruncateAt.END);
            tv.setSingleLine(true);
            tv.setGravity(CENTER);
            //tv.setShadowLayer(3.0f, 2.0f, 2.0f, Color.BLACK);
        }
    }
    /**
     * 加阴影
     * */
    public void setShadow(){
        if(tv!=null){
            //tv.setSelected(false);
            //tv.setEllipsize(TextUtils.TruncateAt.END);
            //tv.setSingleLine(true);
            tv.setShadowLayer(1.0f, 1.0f, 1.0f,0xFF000000);
        }
    }
}
