package com.example.textviewdemo.shader.gradient_final.rainbow_view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.Layout;
import android.text.SpannedString;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.textviewdemo.BaseApp;
import com.example.textviewdemo.R;
import com.example.textviewdemo.shader.gradient_final.rainbow_view.manager.AnimManager;
import com.example.textviewdemo.shader.gradient_final.rainbow_view.bean.GradientInfo;
import com.example.textviewdemo.shader.gradient_final.rainbow_view.interfaces.IGradientAnimSpanV2;
import com.example.textviewdemo.shader.gradient_final.rainbow_view.interfaces.IGradientSpanV2;
import com.example.textviewdemo.shader.gradient_final.rainbow_view.interfaces.IGradientView;
import com.example.textviewdemo.shader.gradient_final.rainbow_view.utils.GradientUtils;
import com.example.textviewdemo.shader.gradient_final.rainbow_view.utils.RainbowConstants;
import com.example.textviewdemo.thumb.Utils;

/**
 * 彩虹字体
 * 超出显示...
 * 1.xml定义GradientAnimTextViewV2
 *  设置android:singleLine="true"
 * 2.调用setContent(CharSequence text)方法
 * 普通字体效果，直接设置setContent(CharSequence text)
 * 渐变字体效果，使用GradientSpanV2
 * 动态渐变字体效果，使用GradientAnimSpanV2
 *
 * 彩虹，换行
 * 1.使用GradientAnimTextViewV2
 * 不设置android:singleLine="true"
 * 2.调用setContent(CharSequence text)
 * 普通字体效果，直接设置setContent(CharSequence text)
 * 渐变字体效果，使用GradientSpanV2
 * 动态渐变字体效果，使用GradientAnimSpanV2
 */
public class GradientAnimTextViewV2 extends AppCompatTextView implements IGradientView {

    /**
     * 正常模式
     */
    public static final int MODE_NORMAL = 0;

    /**
     * 支持渐变动画模式 多行
     */
    public static final int MODE_SPAN = 1;

    /**
     * 彩虹字体滚动模式 单行
     */
    public static final int MODE_SCROLL = 2;

    /**
     * MODE_SCROLL 模式下，如果字体长度小于控件长度，则不滚动
     */
    public static final int SCROLL_NO = 0;

    /**
     * MODE_SCROLL 模式下，如果字体长度小于控件长度，则不滚动，但是有彩虹动画
     */
    public static final int SCROLL_GRADIENT = 1;

    /**
     *  MODE_SCROLL 模式下，如果字体长度大于控件长度，则滚动
     */
    public static final int SCROLL_ANIM = 2;

    private Context mContext;

    /**
     * 是否是从右向左显示
     */
    private boolean isAr = false;

    /**
     * 显示模式 0 常规模式  1 滚动模式
     */
    private int mMode = 0;

    /***********************************************************************************************
     * 滚动相关
     ***********************************************************************************************/
    /**
     * mMode == MODE_SCROLL，这个字段才起作用
     * 支持滚动的情况下，是否滚动 SCROLL_NO:不滚动  SCROLL_RL：滚动
     */
    private int scrollType;

    /**
     * 渐变span
     */
    private IGradientSpanV2[] mGradientSpans;

    /**
     * 彩虹渐变span
     */
    private IGradientAnimSpanV2[] mGradientAnimSpans;

    /**
     * 彩虹渐变动画，包括滚动
     */
    private ValueAnimator mAnimator;

    /**
     * 滚动渐变Shader
     */
    private Shader mScrollShader = null;

    /**
     * 滚动渐变
     */
    private Matrix mGradientMatrix;

    /**
     * 渐变颜色数组
     */
    private int[] mGradientColors;

    /**
     * 字体滚动偏移1
     */
    private int mOffset1;

    /**
     * 字体滚动偏移1
     */
    private int mOffset2;

    /**
     * 字体滚动间距
     */
    private int mSpace;

    /**
     * 水平滚动需要的距离，可以用来调整滚动快慢
     */
    private float horizontalSpeed = 2f;

    /**
     * 渐变Shader是否已经创建
     * 动态设置颜色时，如果是第一次，还没有设置好内容，通过getMeasureWidth()获取的宽度时0，所以需要在onMeasure里面重新创建Shader
     */
    private boolean isGradientCreate;

    /**
     * 渐变字体的偏移量
     */
    private float mTranslate;

    /**
     * 当前字体滚动的偏移
     */
    private int mProgress;

    /***********************************************************************************************
     * 彩虹字体颜色动画
     ***********************************************************************************************/
    /**
     * 是否有彩虹渐变动画
     */
    private boolean hasGradientAnim = false;

    /**
     * 是否开始了动画，开始了动画说明已经初始化了span的信息
     */
    private boolean isStartAnim;

    /**
     * 滚动模式下，超长显示... 的方式
     */
    private boolean isScrollEllipsize;

    /**
     * 计算字体宽度
     */
    private Rect mTextRect;

    /**
     * 是否显示计算的span区域，用于调试使用
     */
    private boolean isShowTest = true;

    /**
     * view 的唯一标记，用于调试
     */
    private String mViewTag;

    /**
     * 标记是滚动类型还是常规类型，用于测试
     */
    private int mType;

    public GradientAnimTextViewV2(Context context) {
        super(context);
    }

    public GradientAnimTextViewV2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initAttr(context, attrs);
    }

    public GradientAnimTextViewV2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setType(int type) {
        this.mType = type;
    }

    private void initAttr(@NonNull Context context, @Nullable AttributeSet attrs) {
        if(context != null && attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GradientAnimTextViewV2);
            mMode = a.getInt(R.styleable.GradientAnimTextViewV2_gradient_anim_mode, MODE_SPAN);
            mType = a.getInt(R.styleable.GradientAnimTextViewV2_gradient_anim_textview_type, 0);
            mViewTag = a.getString(R.styleable.GradientAnimTextViewV2_gradient_anim_textview_tag);
            horizontalSpeed = a.getFloat(R.styleable.GradientAnimTextViewV2_gradient_anim_scroll_speed, 1.0f);
            mSpace = a.getDimensionPixelOffset(R.styleable.GradientAnimTextViewV2_gradient_anim_scroll_space, 200);
            a.recycle();
        }
    }

    /**
     *
     * @param text
     */
    public void setContent(CharSequence text) {
        setContent(text, null);
    }

    /**
     * 设置显示内容
     * 如果有渐变和滚动效果，需要加上colors参数
     * @param text
     * @param colors
     */
    public void setContent(CharSequence text, @ColorInt int[] colors) {
        reset();
        setGradientColor(colors);
        setText(text);
    }

    /**
     * 设置显示内容
     * 如果有渐变和滚动效果，需要加上colors参数
     * @param text
     * @param colors
     */
    public void setContentV2(CharSequence text, @ColorInt int... colors) {
        reset();
        setGradientColor(colors);
        setText(text);
    }

    public void setViewTag(String viewTag) {
        this.mViewTag = viewTag;
    }

    /***********************************************************************************************
     * 滚动相关
     **********************************************************************************************/
    private void reset() {
        stopAnim();
        scrollType = SCROLL_NO;
        mGradientSpans = null;
        mGradientAnimSpans = null;
        mScrollShader = null;
        mGradientMatrix = null;
        mGradientColors = null;
        mOffset1 = 0;
        mOffset2 = 0;
        isGradientCreate = false;
        hasGradientAnim = false;
        isScrollEllipsize = false;
        mTranslate = 0;
        mProgress = 0;
    }

    /**
     * 系统TextView滚动模式
     */
    public void setSpanMode() {
        this.mMode = MODE_SPAN;
        stopAnim();
        setSingleLine();
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
        setFocusable(true);
        setFocusableInTouchMode(true);
        getPaint().setShader(null);
    }
    
    /**
     * 设置滚动模式
     */
    public void setScrollMode() {
        this.mMode = MODE_SCROLL;
        this.isScrollEllipsize = false;
        stopAnim();
        setSingleLine();
        setEllipsize(TextUtils.TruncateAt.END);
        setFocusable(false);
        setFocusableInTouchMode(false);
        initText();
    }

    /**
     * 设置显示模式 省略号显示
     * xml 直接设置 android:singleLine="true" 不需要设置 android:ellipsize="end" 都可以实现省略号效果
     * 但是在代码中 设置etSingleLine();必须要设置setEllipsize(TextUtils.TruncateAt.END)才可以实现省略号效果
     */
    public void setScrollModeEllipsize() {
        this.mMode = MODE_SCROLL;
        this.isScrollEllipsize = true;
        stopAnim();
        setSingleLine();
        setEllipsize(TextUtils.TruncateAt.END);
        setFocusable(false);
        setFocusableInTouchMode(false);
        scrollType = SCROLL_ANIM;
    }


    /**
     * 设置显示模式 省略号显示
     * xml 直接设置 android:singleLine="true" 不需要设置 android:ellipsize="end" 都可以实现省略号效果
     * 但是在代码中 设置etSingleLine();必须要设置setEllipsize(TextUtils.TruncateAt.END)才可以实现省略号效果
     */
    public void setSpanModeEllipsize() {
        this.mMode = MODE_SPAN;
        stopAnim();
        setSingleLine();
        setEllipsize(TextUtils.TruncateAt.END);
        setFocusable(false);
        setFocusableInTouchMode(false);
    }

    private boolean isScrollMode() {
        return mMode == MODE_SCROLL;
    }

    private boolean isSpanMode() {
        return mMode == MODE_SPAN;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(isScrollMode()) {
            initText();
            initShader();
        } else {
            getPaint().setShader(null);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if(isScrollMode() || isSpanMode()) {
            AnimManager.getInstance().addView(mType, this);
        }
    }

    /**
     * 在View从窗口中移除的时候被调用
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(isScrollMode() || isSpanMode()) {
            AnimManager.getInstance().removeView(mType, this);
            stopAnim();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(isScrollMode()) {// 滚动相关处理
            String text = getText().toString();
            if (TextUtils.isEmpty(text)) {
                super.onDraw(canvas);
                return;
            }

            switch (scrollType) {
                case SCROLL_NO:
                    stopAnim();
                    super.onDraw(canvas);
                    break;
                case SCROLL_GRADIENT:
                    if(!isStartAnim) {
                        startAnim();
                    }
                    onGradientText(canvas, text);
                    break;
                case SCROLL_ANIM:
                    if(!isStartAnim) {
                        startAnim();
                    }
                    onScrollText(canvas, text);
                    break;
                default:
                    break;
            }

        } else if(isSpanMode()){ // Span模式
            if (!isStartAnim) {
                if (!isAr) {
                    initSpans(canvas);
                } else {
                    initSpans1(canvas);
                }
                if (mGradientAnimSpans != null && mGradientAnimSpans.length > 0) {
                    hasGradientAnim = true;
                }
                startAnim();
            }

            // 显示测试的span区域
            if (!isAr) {
                drawTestBitmap(canvas);
            } else {
                drawTestArBitmap(canvas);
            }
            super.onDraw(canvas);
        } else {
            super.onDraw(canvas);
        }
    }

    /**
     * 开始动画
     */
    public void startAnim() {
        if(!isStartAnim && (hasGradientAnim || isSupportAnim())) {
            GradientUtils.log("GradientAnimTextViewV2 startAnim2");
            if(mAnimator == null) {
                mAnimator = ValueAnimator.ofInt(0, RainbowConstants.mAnimWidth);
                mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        // 这里必须是显示才进行刷新，因为GradientAnimSpanV2只有一个实例时，房间聊天区如果显示软键盘，
                        // 会导致2个ValueAnimator同时操作一个GradientAnimSpanV2实例，渐变速度会变快
                        if(isShown()) {
                            if (isScrollMode()) {
                                updateScrollAnim(animation);
                            } else {
                                updateGradientAnim(animation);
                            }
                        }
                    }
                });
                ValueAnimator.setFrameDelay(50L);
                mAnimator.setDuration(15000);
                mAnimator.setRepeatCount(ValueAnimator.INFINITE);
                mAnimator.setRepeatMode(ValueAnimator.RESTART);
                mAnimator.setInterpolator(new LinearInterpolator());
            }
            mAnimator.start();
            isStartAnim = true;
        }
    }

    public void stopAnim() {
        if(mAnimator != null) {
            mAnimator.cancel();
        }
        isStartAnim = false;
    }

    public void clearAnim() {
        if(mAnimator != null) {
            mAnimator.cancel();
            mAnimator = null;
        }
        isStartAnim = false;
    }

    /***********************************************************************************************
     * 滚动
     ***********************************************************************************************/
    /**
     * 是否支持动画，包括渐变或者滚动
     * @return
     */
    private boolean isSupportAnim() {
        return scrollType == SCROLL_ANIM
                || scrollType == SCROLL_GRADIENT;
    }

    /**
     * 滚动模式，不需要滚动字体，彩虹渐变动画
     * @param canvas
     * @param text
     */
    private void onGradientText(Canvas canvas, String text) {
        if(canvas == null  || TextUtils.isEmpty(text)) {
            return ;
        }
        if(isAr) {
            getPaint().getTextBounds(text, 0, text.length(), getTextRect());
            Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
            canvas.drawText(text, 0, getHeight() / 2 + (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent, getPaint());

        } else {
            getPaint().getTextBounds(text, 0, text.length(), getTextRect());
            Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
            canvas.drawText(text, 0, getHeight() / 2 + (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent, getPaint());
        }
    }

    /**
     * 滚动模式，滚动字体，彩虹渐变动画
     * @param canvas
     * @param text
     */
    private void onScrollText(Canvas canvas, String text) {
        if(canvas == null  || TextUtils.isEmpty(text)) {
            return ;
        }
        if(isAr) {
            getPaint().getTextBounds(text, 0, text.length(), getTextRect());
            int textWidth = getTextRect().width();
            float textWidth1 = getPaint().measureText(text);
//                        Utils.log("=================> textWidth: " + textWidth + "   textWidth1: " + textWidth1 + "   getWidth(): " + getWidth());
            int distance = textWidth + mSpace;
            float currentX = (getWidth() - getPaddingRight()) - textWidth + mOffset1;
            Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
            canvas.drawText(text, currentX, getHeight() / 2 + (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent, getPaint());
            if (mOffset1 > distance) {
//                            Utils.log("=========================================> getWidth(): " + getWidth() + "   getPaddingRight(): " + getPaddingRight() + "   textWidth: " + textWidth + "   distance: " + distance);
                mOffset1 = -distance;
            }
            float currentX2 = (getWidth() - getPaddingRight()) - textWidth - distance + mOffset2;
//                        Utils.log("=================> currentX: " + currentX + "   currentX2: " + currentX2);
            canvas.drawText(text, currentX2, getHeight() / 2 + (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent, getPaint());
            if (mOffset2 > 2 * distance) {
                mOffset2 = 0;
            }
        } else {
            //从右向左滚动，首次不显示文字，后续每次往左偏移speed像素
            getPaint().getTextBounds(text, 0, text.length(), getTextRect());
            int textWidth = getTextRect().width();
            float textWidth1 = getPaint().measureText(text);
//                        Utils.log("=================> textWidth: " + textWidth + "   textWidth1: " + textWidth1);
            int distance = textWidth + mSpace;
            float currentX = getPaddingLeft() - mOffset1;
            Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
            canvas.drawText(text, currentX, getHeight() / 2 + (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent, getPaint());
            if (mOffset1 > distance) {
                mOffset1 = -(distance);
            }
            float currentX2 = getPaddingLeft() + distance - mOffset2;
//                        canvas.drawText("hello", 0, 30, paint);
            canvas.drawText(text, currentX2, getHeight() / 2 + (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent, getPaint());
            if (mOffset2 > 2 * distance) {
                mOffset2 = 0;
            }
        }
    }

    /***********************************************************************************************
     * 渐变
     ***********************************************************************************************/
    private void initSpans(Canvas canvas) {
        CharSequence text = getText();
        if(TextUtils.isEmpty(text) || canvas == null)
            return ;
        if(text != null && text instanceof SpannedString) {
            SpannedString sb = (SpannedString)text;
            mGradientSpans = sb.getSpans(0, sb.length(), IGradientSpanV2.class);
            mGradientAnimSpans = sb.getSpans(0, sb.length(), IGradientAnimSpanV2.class);
        }

        if(mGradientSpans != null && mGradientSpans.length > 0) {
            for(int i = 0; i < mGradientSpans.length; i ++) {
                IGradientSpanV2 gradientSpan = mGradientSpans[i];
                if(gradientSpan != null) {
                    String spanStr = gradientSpan.getSpanText();
                    if (spanStr != null) {
                        int start = gradientSpan.getStartIndex();
                        int end = start + spanStr.length(); // 不包括
                        GradientInfo rectF = getTextViewSelectionBottomY(start, end);
                        gradientSpan.onDrawBefore(rectF);
                    }
                }
            }
        }
    }

    private void drawTestBitmap(Canvas canvas) {
        if(canvas == null || !isShowTest)
            return ;
        if(mGradientSpans != null && mGradientSpans.length > 0) {
            for(int i = 0; i < mGradientSpans.length; i ++) {
                IGradientSpanV2 gradientSpan = mGradientSpans[i];
                if(gradientSpan != null) {
                    String spanStr = gradientSpan.getSpanText();
                    if (spanStr != null) {
                        GradientInfo rectF = gradientSpan.getGradientInfo();
                        getPaint().setColor(Color.parseColor("#ff0000"));
                        Bitmap bitmap = gradientSpan.getGradientBitmap();
                        if(bitmap != null) {
                            canvas.save(); // 这里只是在一定的偏移绘制出原来的Bitmap，方便对比效果
                            canvas.translate(rectF.left + getPaddingStart(), GradientUtils.dip2px(mContext, 38 + rectF.sLineNum * 45));
                            canvas.drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), new RectF(0, 0, bitmap.getWidth(), 50), getPaint());
                            canvas.restore();
                        }
                    }
                }
            }
        }
    }


    /**
     * 获取TextView某一个字符的坐标
     *
     * @return 返回的是相对坐标
     * @parms tv
     * @parms index 字符下标
     *
     * getLineForOffset(int offset)获取指定字符的行号；
     * getLineBounds(int line, Rect bounds)获取指定行的所在的区域；
     * getPrimaryHorizontal(int offset) 获取指定字符的左坐标；
     * getSecondaryHorizontal(int offset)获取指定字符的辅助水平偏移量；
     * getLineMax(int line)获取指定行的宽度，包含缩进但是不包含后面的空白，可以认为是获取文本区域显示出来的一行的宽度；
     * getLineStart(int line)获取指定行的第一个字符的下标；
     */
    private GradientInfo getTextViewSelectionBottomY(int index, int end) {
        Layout layout = getLayout();
        GradientInfo rectF = new GradientInfo();
        Rect bound = new Rect();
        int startLine = layout.getLineForOffset(index);//获取字符在第几行
        int endLine = layout.getLineForOffset(end);
        layout.getLineBounds(startLine, bound);//获取该行的Bound区域
        if(startLine != endLine) { // 换行,绘制区域是TextView的宽度
            rectF.left = 0;//字符左边x坐标(相对于TextView)
            rectF.top = bound.top;//字符顶部y坐标(相对于TextView的坐标)
            rectF.right = getWidth() - getPaddingEnd();//字符右边x坐标(相对于TextView)
            rectF.bottom = bound.bottom;//字符底部y坐标(相对于TextView的坐标)
            rectF.sLineNum = startLine;
            rectF.eLineNum = endLine;
        } else { // 同一行
            rectF.left = layout.getPrimaryHorizontal(index);//字符左边x坐标(相对于TextView)
            rectF.top = bound.top;//字符顶部y坐标(相对于TextView的坐标)

            // RTL模式下，当最后一个是渐变SPAN，计算最后一个字符右侧到左侧的距离时，会自动获取右侧下一个空白字符，
            // 如果此时span是在最右侧，会导致获取的是下一行空白字符的坐标，导致left比right大，需要判断一下，如果
            // 这个span位于最后一个，把右侧坐标设置成TextView的宽度
            float right = layout.getPrimaryHorizontal(end);//字符右边x坐标(相对于TextView)
            if(right < rectF.left) {
                right = getWidth();
            }

            rectF.right = right;
            rectF.bottom = bound.bottom;//字符底部y坐标(相对于TextView的坐标)
            rectF.sLineNum = startLine;
            rectF.eLineNum = endLine;
        }
        return rectF;
    }

    /**
     * 阿拉伯语处理
     */
    private void initSpans1(Canvas canvas) {
        CharSequence text = getText();
        if(TextUtils.isEmpty(text) || canvas == null)
            return ;
        if(text != null && text instanceof SpannedString) {
            SpannedString sb = (SpannedString)text;
            mGradientSpans = sb.getSpans(0, sb.length(), IGradientSpanV2.class);
            mGradientAnimSpans = sb.getSpans(0, sb.length(), IGradientAnimSpanV2.class);
        }

        if(mGradientSpans != null && mGradientSpans.length > 0) {
            for(int i = 0; i < mGradientSpans.length; i ++) {
                IGradientSpanV2 gradientSpan = mGradientSpans[i];
                if(gradientSpan != null) {
                    String spanStr = gradientSpan.getSpanText();
                    if (spanStr != null) {
                        int start = gradientSpan.getStartIndex();
                        int end = start + spanStr.length(); // 不包括
                        GradientInfo rectF = getTextViewSelectionBottomY1(start, end, spanStr);
                        gradientSpan.onDrawBefore(rectF);
                    }
                }
            }
        }
    }

    private void drawTestArBitmap(Canvas canvas) {
        if(canvas == null || !isShowTest)
            return ;
        if(mGradientSpans != null && mGradientSpans.length > 0) {
            for(int i = 0; i < mGradientSpans.length; i ++) {
                IGradientSpanV2 gradientSpan = mGradientSpans[i];
                if(gradientSpan != null) {
                    String spanStr = gradientSpan.getSpanText();
                    if (spanStr != null) {
                        GradientInfo rectF = gradientSpan.getGradientInfo();
                        getPaint().setColor(Color.parseColor("#ff0000"));
                        Bitmap bitmap = gradientSpan.getGradientBitmap();
                        if(bitmap != null) {
                            canvas.save(); // 这里只是在一定的偏移绘制出原来的Bitmap，方便对比效果
                            canvas.translate(rectF.left + getPaddingEnd(), GradientUtils.dip2px(mContext, 38 + rectF.sLineNum * 45));
                            canvas.drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), new RectF(0, 0, bitmap.getWidth(), 50), getPaint());
                            canvas.restore();
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取TextView某一个字符的坐标
     *
     * @return 返回的是相对坐标
     * @parms tv
     * @parms index 字符下标
     * @param str span 的显示内容
     * 为了预防阿语模式下最后一个是渐变的Span，采取传入的startIndex = startIndex + 1 endIndex = endIndex - 1
     * 如果startIndex == endIndex + 1 只有一个字符
     * 如果startIndex == endIndex 2个字符
     * 如果startIndex > endIndex 大于2个字符
     * 其他不处理
     */
    private GradientInfo getTextViewSelectionBottomY1(int start, int end, String str) {
        if(TextUtils.isEmpty(str) || str.length() == 0 || start < 0) {
            return null;
        }
        int startIndex = start + 1;
        int endIndex = end - 1;
        Layout layout = getLayout();
        GradientInfo rectF = new GradientInfo();
        Rect bound = new Rect();
        int startLine = layout.getLineForOffset(startIndex);//获取字符在第几行
        int endLine = layout.getLineForOffset(endIndex);
        int lineStartIndex = layout.getLineStart(startLine); // 当前行开始字符的index
        int lineEndIndex = layout.getLineEnd(startLine); // 当前行结束字符的index 开区间
        int count = lineEndIndex - lineStartIndex - 1;
        layout.getLineBounds(startLine, bound);//获取该行的Bound区域
        CharSequence text = getText();
        if(TextUtils.isEmpty(text)) {
            rectF.left = 0;//字符左边x坐标(相对于TextView)
            rectF.top = bound.top;//字符顶部y坐标(相对于TextView的坐标)
            rectF.right = getWidth() - getPaddingStart() - getPaddingEnd();//字符右边x坐标(相对于TextView)
            rectF.bottom = bound.bottom;//字符底部y坐标(相对于TextView的坐标)
            rectF.sLineNum = startLine;
            rectF.eLineNum = endLine;
            return rectF;
        }
        rectF.top = bound.top;//字符顶部y坐标(相对于TextView的坐标)
        rectF.bottom = bound.bottom;//字符底部y坐标(相对于TextView的坐标)
        rectF.sLineNum = startLine;
        rectF.eLineNum = endLine;
        if(startLine != endLine) { // 换行,绘制区域是TextView的宽度
            rectF.left = 0;//字符左边x坐标(相对于TextView)
            rectF.right = getWidth() - getPaddingStart() - getPaddingEnd();//字符右边x坐标(相对于TextView)
        } else { // 同一行
            // layout.getPrimaryHorizontal(0)
            // getText().toString().charAt()
            if(lineStartIndex == start && lineEndIndex == end) { // span在行首，也在行尾
                float gradientStart = layout.getPrimaryHorizontal(start);
                float gradientEnd = layout.getPrimaryHorizontal(end); // span右侧到控件左侧距离
                rectF.left = gradientStart < gradientEnd ? gradientStart : gradientEnd;
                rectF.right = gradientStart < gradientEnd ? gradientEnd : gradientStart;
            } else if(lineStartIndex == start && lineEndIndex != end) { // span位于一行的开始，但不是在一行的结尾,如果有rtl，直接可以使用start和end对调
                boolean isContainsRtl = isContainsRtl(lineStartIndex, lineEndIndex);
                if(isContainsRtl) { // 有阿语，从右向左
                    float[] space = getSpaceInfo(start, end + 1);
                    if(space != null && space.length >= 2) {
                        rectF.left = space[0];//字符左边x坐标(相对于TextView)
                        rectF.right = space[1]; //
                    }
                } else { // 从左向右
                    float[] space = getSpaceInfo(1, end + 1);
                    if(space != null && space.length >= 2) {
                        rectF.left = space[0] - getPaint().measureText(String.valueOf(text.charAt(0)));//字符左边x坐标(相对于TextView)
                        rectF.right = space[1]; //
                    }
                }
            } else if(lineStartIndex != start && lineEndIndex == end) { // span不在行首，在行尾,不能使用end，可以使用start
                boolean isContainsRtl = isContainsRtl(lineStartIndex, lineEndIndex);
                if(isContainsRtl) { // 有阿语，从右向左
                    float[] space = getSpaceInfo(start, end + 1);
                    if(space != null && space.length >= 2) {
                        rectF.left = space[0];//字符左边x坐标(相对于TextView)
                        rectF.right = space[1]; //
                    }
                } else { // 从左向右
                    float[] space = getSpaceInfo(start, end);
                    if(space != null && space.length >= 2) {
                        rectF.left = space[0];//字符左边x坐标(相对于TextView)
                        String endStr = "";
                        if(end <= text.length())
                            endStr = String.valueOf(text.charAt(end - 1));
                        rectF.right = space[1] + getPaint().measureText(endStr); //
                    }
                }
            } else if(lineStartIndex != start && lineEndIndex != end){ // span位于中间 start 和 end 都可以用
                boolean isContainsRtl = isContainsRtl(lineStartIndex, lineEndIndex);
                if(isContainsRtl) { // 有阿语，从右向左 有阿语和无阿语他们的方向也不一致，取start + 1 到 end - 1的距离，然后左右各加上start和end的最大宽度。
                    float[] space = getSpaceInfo(start + 1, end);
                    if(space != null && space.length >= 2) {
                        String startStr = "";
                        if(end <= text.length())
                            startStr = String.valueOf(text.charAt(start));
                        float startWidth = getPaint().measureText(startStr);
                        String endStr = "";
                        if(end <= text.length())
                            endStr = String.valueOf(text.charAt(end));
                        float endWidth = getPaint().measureText(endStr);
                        float width = Math.max(startWidth, endWidth);
                        rectF.left = space[0] - width;//字符左边x坐标(相对于TextView)
                        rectF.right = space[1] + width; //
                    }
                } else { // 从左向右
                    float[] space = getSpaceInfo(start, end);
                    if(space != null && space.length >= 2) {
                        rectF.left = space[0];//字符左边x坐标(相对于TextView)
                        String endStr = "";
                        if(end <= text.length())
                            endStr = String.valueOf(text.charAt(end - 1));
                        rectF.right = space[1] + getPaint().measureText(endStr); //
                    }
                }
            }
        }
        return rectF;
    }

    /**
     * 是否包含从右向左的字符,如果不包含，显示的英文的位置需要特别处理
     * @param start 某一行开始的index 包含
     * @param end 某一行结束的index 不包含
     * @return
     */
    private boolean isContainsRtl(int start, int end) {
        for(int i = start; i < end; i ++) {
            Layout layout = getLayout();
            CharSequence text = getText();
            if(layout != null && layout.isRtlCharAt(i) && text != null && ' ' != text.charAt(i)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取某段字符串的左边和右边距离
     * @return
     */
    private float[] getSpaceInfo(int start, int end) {
        float left = getWidth();
        float right = 0;
        Layout layout = getLayout();
        if(layout != null) {
            for (int i = start; i < end; i ++) {
                float space = layout.getPrimaryHorizontal(i);
                if(left > space)
                    left = space;
                if(right < space)
                    right = space;
            }
        }
        return new float[]{left, right};
    }

    /**
     * 渐变动画
     */
    private void updateGradientAnim(ValueAnimator animation) {
        if(animation == null)
            return ;
        if(mGradientAnimSpans != null) {
            int value = (int) animation.getAnimatedValue();
            for(int i = 0; i < mGradientAnimSpans.length; i ++) {
                if(mGradientAnimSpans[i] != null) {
                    mGradientAnimSpans[i].onAnim(value);
                }
            }
            invalidate();
        }
    }

    /***********************************************************************************************
     * 滚动动画
     ***********************************************************************************************/
    /**
     * 设置渐变颜色
     * @param colors
     */
    public void setGradientColor(@ColorInt int[] colors) {
        if(colors != null && colors.length > 0) {
            mGradientColors = new int[colors.length * 2 - 1];
            int i = 0;
            for (int c : colors) {
                mGradientColors[i] = c;
                mGradientColors[2 * colors.length - 2 - i] = c;
                i ++;
            }
            isGradientCreate = false;
        } else {
            mGradientColors = null;
            isGradientCreate = true;
        }
    }

    private void initShader() {
        if(mGradientColors != null) { // 有渐变
            if(!isGradientCreate) {
//                mScrollShader = new LinearGradient(0f, 0f, getMeasuredWidth(), 0f, mGradientColors, null, Shader.TileMode.MIRROR);
                mScrollShader = GradientUtils.createBitmapShader(RainbowConstants.getShaderRealWidth(getMeasuredWidth()), RainbowConstants.createBitmapHeight, mGradientColors);
                isGradientCreate = true;
                getPaint().setShader(mScrollShader);
            }
        } else { // 没有渐变
            getPaint().setShader(null);
        }
    }

    private Rect getTextRect() {
        if(mTextRect == null)
            mTextRect = new Rect();
        return mTextRect;
    }

    private void initText() {
        if(getPaint() != null) {
            String text = getText().toString();
            float textWidth = getPaint().measureText(text);
            if(textWidth <= getMeasuredWidth()) { // 不滚动
                if(mGradientColors != null && mGradientColors.length > 0) {
                    scrollType = SCROLL_GRADIENT;
                } else {
                    scrollType = SCROLL_NO;
                }
            } else { // 滚动
                scrollType = SCROLL_ANIM;
            }
        }
    }

    /**
     * 滚动动画
     */
    private void updateScrollAnim(ValueAnimator animation) {
        if(animation == null) {
            return;
        }
        int value = (int) animation.getAnimatedValue();
//        int translate = value - getMeasuredWidth();
//        if(isAr) {
//            translate = -translate;
//        }

        if(mProgress != value) {
            if(Math.abs(mTranslate) > RainbowConstants.getShaderRealWidth(getMeasuredWidth())) { // 超出范围，重新开始
                mTranslate = 0;
            }
            if(isAr) {
                mTranslate -= GradientUtils.dip2px(BaseApp.getInstance(), 1);
            } else {
                mTranslate += GradientUtils.dip2px(BaseApp.getInstance(), 1);
            }
        }
        mProgress = value;

        if(mGradientMatrix == null) {
            mGradientMatrix = new Matrix();
        }
        mGradientMatrix.setTranslate(mTranslate, 0f);
        if(mScrollShader != null) {
            mScrollShader.setLocalMatrix(mGradientMatrix);
        }
        if(!isScrollEllipsize && isScrollAnim()) {
            if (isAr) {
                mOffset1 += horizontalSpeed;
                mOffset2 += horizontalSpeed;
            } else {
                mOffset1 += horizontalSpeed;
                mOffset2 += horizontalSpeed;
            }
        }
        invalidate();
    }

    private boolean isGradientAnim() {
        return scrollType == SCROLL_GRADIENT;
    }

    private boolean isScrollAnim() {
        return scrollType == SCROLL_ANIM;
    }

    @Override
    public String getViewTag() {
        return mViewTag;
    }

}
