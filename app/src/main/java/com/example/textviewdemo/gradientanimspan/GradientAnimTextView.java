package com.example.textviewdemo.gradientanimspan;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
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

import com.example.textviewdemo.R;
import com.example.textviewdemo.textview_test.GradientInfo;
import com.example.textviewdemo.textview_test.IGradientSpan;
import com.example.textviewdemo.thumb.Utils;

/**
 * name: GradientAnimTextView
 * desc: 支持渐变，动画TextView，使用BitmapShader
 *
 * 实现：
 * 1.渐变Span使用动态计算每个字符的位置的方式，计算出实际字符串的显示位置，
 * 其中阿语模式下的计算有坑，因为显示纯阿语，纯英语，以及阿语和英语混合拿到的位置都是不对的
 * 2.渐变span使用Shader动态更新，绘制文字，通过颜色数组，动态生成BitmapShader，然后平移BitmapShader实现字体渐变
 * 3.isAr在测试的时候使用，用到项目，每次使用isRtl标志进行设置，测试的时候要根据实际的语言修改标志，RTL的时候需要改成true
 * 4.
 *
 * 需要测试的项目
 * 1.超长显示。。。
 * 2.超长滚动
 * 3.RecyclerView使用
 *
 *
 * detach事停止动画
 * 不显示时停止动画 显示时重新开始
 * 可能出现的异常捕获
 * isScrollMode 就开启了动画，实际上如果文字比宽度小，不需要动画？应该也是需要的，因为有渐变动画
 * 看是否需要将LinearGradient改成BitmapShader，应该用LinearGradient比较好，因为BitmapShader需要创建bitmap，拉低性能
 * 把输入颜色的方法编写出来，是使用Mirror还是CLAMP
 * 单纯的渐变Shader和彩虹Shader应该要分开，渐变Shader使用BitmapShader也可以，彩虹的需要使用LinearGradient，因为要使用Mirror实现首尾颜色看起来连续。
 *
 * 注意
 * 1.设置是指定是正常模式还是滚动模式
 * 2.如果是正常模式，使用span自动启动动画，根据IGradientSpan1(单独就是渐变的功能)和IGradientAnimSpan(渐变动画功能)
 * 3.如果是滚动模式，滚动模式下如果是渐变 + 滚动或者，则走滚动逻辑，如果非渐变，则走普通的span逻辑（第2步的逻辑)
 * 4.注意只是有渐变动画的才走渐变的流程，其他的都走TextView默认流程
 *
 * 使用方法
 * 1.滚动+渐变 xml设置
 * android:singleLine="true"
 * app:mode="scroll"
 * 2.渐变
 * xml去掉android:singleLine="true"
 * app:mode="normal"
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
public class GradientAnimTextView extends AppCompatTextView {

    /**
     * 正常模式
     */
    private static final int MODE_NORMAL = 0;

    /**
     * 滚动模式
     */
    private static final int MODE_SCROLL = 1;

    //滚动方向
    //不滚动
    public static final int SCROLL_NO = 1;

    //从右往左
    public static final int SCROLL_RL = 3;

    private Context mContext;
    private IGradientSpan1[] mGradientSpans;
    private IGradientAnimSpan[] mGradientAnimSpans;

    private boolean isAr = false;

    private ValueAnimator mAnimator;

    /**
     * 是否有动画
     */
    private boolean isAnim = true;

    /**
     * 是否开始了动画，开始了动画说明已经初始化了span的信息
     */
    private boolean isStartAnim;

    /**
     * 显示计算的span区域
     */
    private boolean isShowTest = true;

    /**
     * 显示模式 0 常规模式  1 滚动模式
     */
    private int mMode = 0;

    private LinearGradient mLinearGradient = null;
    private Matrix mGradientMatrix = new Matrix();
    private int[] colors = new int[]{};

    private int scrollType;

    private Paint paint;

    private float  mTextWidth;

    private Rect rect;

    private int mOffset1;
    private int mOffset2;

    /**
     * 字体滚动间距
     */
    private int mSpace;


    @ColorInt
    private int startColor = 0;

    @ColorInt
    private int endColor = 0;

    //水平滚动需要的数据
    private float horizontalSpeed = 2f;

    private int color;

    private float translate = 0f;

    private boolean translateAnimate = true;


    public GradientAnimTextView(Context context) {
        super(context);
    }

    public GradientAnimTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initAttr(context, attrs);
        init();
    }

    public GradientAnimTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

    private void initAttr(@NonNull Context context, @Nullable AttributeSet attrs) {
        if(context != null && attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GradientAnimTextView);
            mMode = a.getInt(R.styleable.GradientAnimTextView_mode, MODE_NORMAL);
            color = a.getColor(R.styleable.GradientAnimTextView_text_color, 0x000000);
            a.recycle();
        }
    }

    private void init() {
        if(isScrollMode()) {
            setSpeed(1, 5f);
            paint = getPaint();
            paint.setColor(color);
            rect = new Rect();
            startColor = getResources().getColor(R.color.color_03DAC5);
            endColor = getResources().getColor(R.color.cffde3d32);
            colors = new int[]{startColor, endColor, startColor};
            mSpace = 200;
        }
    }

    private boolean isScrollMode() {
        return mMode == MODE_SCROLL;
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
        if(isScrollMode()) {
            initText();
        }
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
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnim();
    }

    public void setContent(CharSequence text) {
        stopAnim();
        setText(text);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(isScrollMode()) {
            setupGradient();
            startGradient(getMeasuredWidth());
            System.out.println("=================================> onSizeChanged width: " + getMeasuredWidth() + "   height: " + getMeasuredHeight());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(isScrollMode()) {// 滚动相关处理
            String text = getText().toString();
            if (TextUtils.isEmpty(text)) {
                super.onDraw(canvas);
                return;
            }
            if(mLinearGradient == null) {
                mLinearGradient = new LinearGradient(0f, 0f, getMeasuredWidth(), 0f, colors, null, Shader.TileMode.MIRROR);
            }
            if(mLinearGradient != null)
                getPaint().setShader(mLinearGradient);
            switch (scrollType) {
                case SCROLL_NO:
                    super.onDraw(canvas);
                    break;
                case SCROLL_RL:
                    if(isAr) {
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

        } else { // 常规模式
            if (!isStartAnim) {
                if (!isAr) {
                    initSpans(canvas);
                } else {
                    initSpans1(canvas);
                }
                if (mGradientAnimSpans != null && mGradientAnimSpans.length > 0) {
                    isAnim = true;
                    startAnim();
                }
            }

            // 显示测试的span区域
            if (!isAr) {
                drawTestBitmap(canvas);
            } else {
                drawTestArBitmap(canvas);
            }
            super.onDraw(canvas);
        }
    }

    private void initSpans(Canvas canvas) {
        CharSequence text = getText();
        if(TextUtils.isEmpty(text) || canvas == null)
            return ;
        if(text != null && text instanceof SpannedString) {
            SpannedString sb = (SpannedString)text;
            mGradientSpans = sb.getSpans(0, sb.length(), IGradientSpan1.class);
            mGradientAnimSpans = sb.getSpans(0, sb.length(), IGradientAnimSpan.class);
        }

        if(mGradientSpans != null && mGradientSpans.length > 0) {
            for(int i = 0; i < mGradientSpans.length; i ++) {
                IGradientSpan1 gradientSpan = mGradientSpans[i];
                if(gradientSpan != null) {
                    String spanStr = gradientSpan.getSpanText();
                    if (spanStr != null) {
                        String content = text.toString();
//                        int start = content.indexOf(spanStr);
                        int start = gradientSpan.getStartIndex();
                        int end = start + spanStr.length(); // 不包括
                        GradientInfo rectF = getTextViewSelectionBottomY(start, end);
                        gradientSpan.onDrawBefore(rectF);

//                        Bitmap bitmap = gradientSpan.getGradientBitmap();
//                        if(bitmap != null) {
//                            canvas.save(); // 这里只是在一定的偏移绘制出原来的Bitmap，方便对比效果
//                            canvas.translate(rectF.left + getPaddingStart(), Utils.dip2px(mContext, 38 + rectF.sLineNum * 45));
////                          canvas.drawRect(rectF, getPaint());
////                          canvas.drawBitmap(gradientSpan.getGradientBitmap(), new Matrix(), getPaint());
//
//                            canvas.drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), new RectF(0, 0, bitmap.getWidth(), 50), getPaint());
//                            canvas.restore();
//                        }
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
                IGradientSpan1 gradientSpan = mGradientSpans[i];
                if(gradientSpan != null) {
                    String spanStr = gradientSpan.getSpanText();
                    if (spanStr != null) {
                        GradientInfo rectF = gradientSpan.getGradientInfo();
                        getPaint().setColor(Color.parseColor("#ff0000"));
                        Bitmap bitmap = gradientSpan.getGradientBitmap();
                        if(bitmap != null) {
                            canvas.save(); // 这里只是在一定的偏移绘制出原来的Bitmap，方便对比效果
                            canvas.translate(rectF.left + getPaddingStart(), Utils.dip2px(mContext, 38 + rectF.sLineNum * 45));
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
            mGradientSpans = sb.getSpans(0, sb.length(), IGradientSpan1.class);
            mGradientAnimSpans = sb.getSpans(0, sb.length(), IGradientAnimSpan.class);
        }

        if(mGradientSpans != null && mGradientSpans.length > 0) {
            for(int i = 0; i < mGradientSpans.length; i ++) {
                IGradientSpan1 gradientSpan = mGradientSpans[i];
                if(gradientSpan != null) {
                    String spanStr = gradientSpan.getSpanText();
                    if (spanStr != null) {
                        String content = text.toString();
//                        int start = content.indexOf(spanStr);
                        int start = gradientSpan.getStartIndex();
                        int end = start + spanStr.length(); // 不包括
                        GradientInfo rectF = getTextViewSelectionBottomY1(start, end, spanStr);
                        gradientSpan.onDrawBefore(rectF);

//                        Bitmap bitmap = gradientSpan.getGradientBitmap();
//                        if(bitmap != null) {
//                            canvas.save(); // 这里只是在一定的偏移绘制出原来的Bitmap，方便对比效果
//                            canvas.translate(rectF.left + getPaddingEnd(), Utils.dip2px(mContext, 38 + rectF.sLineNum * 45));
////                          canvas.drawRect(rectF, getPaint());
////                          canvas.drawBitmap(gradientSpan.getGradientBitmap(), new Matrix(), getPaint());
//
//                            canvas.drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), new RectF(0, 0, bitmap.getWidth(), 50), getPaint());
//                            canvas.restore();
//                        }
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
                IGradientSpan1 gradientSpan = mGradientSpans[i];
                if(gradientSpan != null) {
                    String spanStr = gradientSpan.getSpanText();
                    if (spanStr != null) {
                        GradientInfo rectF = gradientSpan.getGradientInfo();
                        getPaint().setColor(Color.parseColor("#ff0000"));
                        Bitmap bitmap = gradientSpan.getGradientBitmap();
                        if(bitmap != null) {
                            canvas.save(); // 这里只是在一定的偏移绘制出原来的Bitmap，方便对比效果
                            canvas.translate(rectF.left + getPaddingEnd(), Utils.dip2px(mContext, 38 + rectF.sLineNum * 45));
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
     * 开始动画
     */
    public void startAnim() {
        if(isAnim) {
            if(mAnimator != null) {
                mAnimator.cancel();
                mAnimator = null;
            }
            mAnimator = ValueAnimator.ofInt(0, getMeasuredWidth());
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    if(isAnim) {
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
            isStartAnim = true;
        }
    }

    public void stopAnim() {
        if(mAnimator != null) {
            mAnimator.cancel();
            mAnimator = null;
        }
        isStartAnim = false;
    }

    private void setupGradient() {
        if (getMeasuredWidth() <= 0 || getMeasuredHeight() <= 0) {
            return ;
        }
//        mLinearGradient = new LinearGradient(0f, 0f, getMeasuredWidth() * 1, 0f, startColor, endColor, Shader.TileMode.MIRROR);

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
                        translate = value - getMeasuredWidth();
                        mGradientMatrix.setTranslate(translate, 0f);
                        if(mLinearGradient != null) {
                            mLinearGradient.setLocalMatrix(mGradientMatrix);
                        }
                        if(isAr) {
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
            mAnimator.setDuration(2000);
            mAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mAnimator.setRepeatMode(ValueAnimator.RESTART);
            mAnimator.setInterpolator(new LinearInterpolator());
//            mAnimator.addListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//
//
//                }
//
//                @Override
//                public void onAnimationStart(Animator animation) {
//
//                }
//            });
            mAnimator.start();
        }
    }

}
