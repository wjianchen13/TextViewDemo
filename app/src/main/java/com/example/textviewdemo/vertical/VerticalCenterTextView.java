package com.example.textviewdemo.vertical;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.example.textviewdemo.R;

/**
 * 当TextView的实际高度大于父控件的高度时，垂直显示TextView的中间部分
 */
public class VerticalCenterTextView extends FrameLayout {

    private Context mContext;
    private MyTextView tvContent;
    private String mText;
    private int mTextSize;
    private ColorStateList mTextColor;
    private int mTextStyle;

    public VerticalCenterTextView(@NonNull Context context) {
        super(context);
        initAttrs(context, null);
        init(context);
    }

    public VerticalCenterTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        init(context);
    }

    public VerticalCenterTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * init attr
     * @param context
     * @param attrs
     */
    private void initAttrs(Context context, AttributeSet attrs){
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CenterTextView);
        mText = a.getString(R.styleable.CenterTextView_android_text);
        mTextSize = a.getDimensionPixelSize(R.styleable.CenterTextView_android_textSize, -1);
        mTextColor = a.getColorStateList(R.styleable.CenterTextView_android_textColor);
        mTextStyle = a.getInt(R.styleable.CenterTextView_android_textStyle, 0);
        a.recycle();
    }

    private void init(Context context) {
        if (context == null)
            return;
        this.mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.view_vertical_center, this);
        tvContent = findViewById(R.id.tv_content);
        if(mTextStyle == 1) {
            tvContent.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        } else if(mTextStyle == 2) {
            tvContent.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
        }
        if(mTextColor != null)
            tvContent.setTextColor(mTextColor);
        if(mTextSize != -1)
            tvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        tvContent.setText(mText);

    }

    public void setText(CharSequence text) {
        tvContent.setText(text);
        tvContent.setSelected(true);
    }

    public void setText(@StringRes int resid) {
        tvContent.setText(resid);
        tvContent.setSelected(true);
    }

    public void setText(@StringRes int resid, TextView.BufferType type) {
        tvContent.setText(resid, type);
        tvContent.setSelected(true);
    }

    public void setText(CharSequence text, TextView.BufferType type) {
        tvContent.setText(text, type);
        tvContent.setSelected(true);
    }

    public void setText(char[] text, int start, int len) {
        tvContent.setText(text, start, len);
        tvContent.setSelected(true);
    }
}
