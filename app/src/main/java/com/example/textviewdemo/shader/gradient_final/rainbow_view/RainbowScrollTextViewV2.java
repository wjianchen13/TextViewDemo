package com.example.textviewdemo.shader.gradient_final.rainbow_view;

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
import com.example.textviewdemo.shader.gradient_final.rainbow_view.interfaces.IGradientView;
import com.example.textviewdemo.shader.gradientanimspan.test5.ScreenUtils;

/**
 * 彩虹字体，需要滚动的用这个
 * 使用自定义滚动
 * 1.xml定义RainbowScrollTextViewV2
 * 2.调用setContent(CharSequence text, boolean rainbow, @ColorInt int[] colors)方法
 *
 * 使用系统滚动
 * 1.xml定义RainbowScrollTextViewV2
 * 2.代码设置，这样系统超过控件长度就会自动滚动，使用的TextView滚动方式，动态修改span的shader颜色值
 * sContent.append(GradientUtils.getGradientAnimText(this, "使用RainbowScrollTextViewV2时，普通字体", colors, sContent.length(), 0));
 * tvTest6.setContent(sContent);
 *
 *
 * 发现滚动时候使用RainbowScrollTextViewV2，彩虹动画使用getGradientAnimText也可以实现滚动，彩虹动画效果
 * TextView getPaint().setShader() 滚动的时候会失效，但是span里面设置的shader不会失效。
 */
public class RainbowScrollTextViewV2 extends FrameLayout implements IGradientView {

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
     * view 的唯一标记，用于调试
     */
    private String mViewTag;

    /**
     * 字体设置，加粗，常规
     */
    private int mTextStyle;
    
    private int mType;

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
     */
    public void setContent(CharSequence text) {
        setContent(text, null);
    }

    /**
     * 设置显示内容
     * @param text 显示内容
     * @param colors 滚动彩虹效果颜色
     */
    public void setContent(CharSequence text, @ColorInt int[] colors) {
        if(colors != null && colors.length > 0) {
            if (tv != null) {
                tv.setScrollMode();
                tv.setContent(text, colors);
                tv.setSelected(false);
            }
        } else {
            if (tv != null) {
                tv.setSpanMode();
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
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
//        AnimManager.getInstance().addView(1, this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
//        AnimManager.getInstance().removeView(1, this);
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
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RainbowScrollTextViewV2);
        textColor = a.getColor(R.styleable.RainbowScrollTextViewV2_rainbow_scroll_text_color, 0);
        textSize = a.getDimensionPixelOffset(R.styleable.RainbowScrollTextViewV2_rainbow_scroll_text_size, 0);
        mAutoSize = a.getBoolean(R.styleable.RainbowScrollTextViewV2_rainbow_scroll_auto_width, false);
        mGravity = a.getInt(R.styleable.RainbowScrollTextViewV2_rainbow_scroll_layout_gravity, 0);
        mMaxWidth = a.getDimensionPixelOffset(R.styleable.RainbowScrollTextViewV2_rainbow_scroll_max_size, ScreenUtils.getScreenWidth(mContext));
        mTextStyle = a.getInt(R.styleable.RainbowScrollTextViewV2_rainbow_scroll_text_style, 0);
        mType = a.getInt(R.styleable.RainbowScrollTextViewV2_rainbow_scroll_textview_type, 0);
        mViewTag = a.getString(R.styleable.RainbowScrollTextViewV2_rainbow_scroll_textview_tag);
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
        tv.setType(mType);
        tv.setViewTag(mViewTag);
//        if(mTextDirection == 1) {
//            tv.setTextDirection(View.TEXT_DIRECTION_LTR);
//        }
        if(mTextStyle > 0)
            tv.setTypeface(Typeface.defaultFromStyle(mTextStyle));
    }

    public void setViewTag(String viewTag) {
        if(tv != null)
            tv.setViewTag(viewTag);
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

    @Override
    public String getViewTag() {
        return mViewTag;
    }
}
