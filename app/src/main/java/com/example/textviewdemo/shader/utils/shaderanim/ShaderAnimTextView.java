package com.example.textviewdemo.shader.utils.shaderanim;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.SpannedString;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.textviewdemo.shader.utils.shaderanim.interfaces.IAnimSpan;
import com.example.textviewdemo.thumb.Utils;

/**
 * 彩虹字体带动画TextView
 * 1. 添加addTextChangedListener(this);之后每次调用invalidate()方法，span内部的updateDrawState()方法不会被调用
 */
public class ShaderAnimTextView extends TextView implements TextWatcher {

    private Context mContext;

    private ValueAnimator mAnimator;
    private float translate = 0f;

    /**
     * 是否有动画
     */
    private boolean isAnim = true;

    private IAnimSpan[] mSpans;

    public ShaderAnimTextView(Context context) {
        super(context);
    }

    public ShaderAnimTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ShaderAnimTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context) {
        this.mContext = context;
        setAnim(true);
//        addTextChangedListener(this);
    }

    public void setText() {

    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        if(text != null && text instanceof SpannableStringBuilder) {
            SpannableStringBuilder sb = (SpannableStringBuilder)text;
            mSpans = sb.getSpans(0, sb.length(), IAnimSpan.class);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnim();
    }

    /**
     * 是否支持动画
     * @param anim
     */
    public void setAnim(boolean anim) {
        this.isAnim = anim;
    }

    /**
     * 开始动画
     */
    public void startAnim() {
        if(isAnim) {
            if(mAnimator != null) {
                mAnimator.cancel();
                mAnimator = null;
            }
            mAnimator = ValueAnimator.ofInt(0, 100);
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    if(isAnim) {
                        if(mSpans != null) {
                            int value = (int) animation.getAnimatedValue();
                            for(int i = 0; i < mSpans.length; i ++) {
                                if(mSpans[i] != null) {
                                    mSpans[i].onAnim(value);
                                }
                            }
                            invalidate();
                        }

                    }
                }
            });
            ValueAnimator.setFrameDelay(50L);
            mAnimator.setDuration(2000);
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

    public void stopAnim() {
        if(mAnimator != null) {
            mAnimator.cancel();
            mAnimator = null;
        }
        isAnim = false;
    }

    public void initSpan() {
        CharSequence c = getText();
//        if(c != null && c instanceof SpannableStringBuilder) {
//            SpannableStringBuilder sb = (SpannableStringBuilder)c;
//            sb.getSpans()
//        }
        if(c != null && c instanceof SpannedString) {
            SpannedString sb = (SpannedString)c;
            IAnimSpan[] spans = sb.getSpans(0, sb.length(), IAnimSpan.class);
            for(int i = 0; i < spans.length; i ++) {

            }
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    /**
     * 这个方法初始化的时候也会调用
     * @param s The text the TextView is displaying
     * @param start The offset of the start of the range of the text that was
     * modified
     * @param before The length of the former text that has been replaced
     * @param count The length of the replacement modified text
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Utils.log("onTextChanged s: " + s);

        if(s != null && s instanceof SpannableStringBuilder) {
            SpannableStringBuilder sb = (SpannableStringBuilder)s;
            mSpans = sb.getSpans(0, sb.length(), IAnimSpan.class);
        }
    }
}
