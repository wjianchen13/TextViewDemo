package com.example.textviewdemo.gradientanimspan;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.Layout;
import android.text.SpannedString;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

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
 */
public class GradientAnimTextView extends AppCompatTextView {

    private Context mContext;
    private IGradientSpan[] mGradientSpans;
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

    public GradientAnimTextView(Context context) {
        super(context);
    }

    public GradientAnimTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

    }

    public GradientAnimTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

    @Override
    public boolean onPreDraw() {
        return super.onPreDraw();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnim();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        getLine();
        CharSequence c = getText();
        if (!isStartAnim) {
            if (!isAr) {
                initSpans(canvas);
            } else {
                initSpans1(canvas);
            }
            if(mGradientAnimSpans != null && mGradientAnimSpans.length > 0) {
                startAnim();
                isStartAnim = true;
            }
        }
        String str = c.toString();
        int index = str.indexOf("xing", 1);
//        RectF rectF = getTextViewSelectionBottomY(index);
//        getPaint().setColor(Color.parseColor("#ff0000"));
//        canvas.drawRect(rectF, getPaint());
        super.onDraw(canvas);

    }

    private void initSpans(Canvas canvas) {

        CharSequence text = getText();
        if(TextUtils.isEmpty(text) || canvas == null)
            return ;
        if(text != null && text instanceof SpannedString) {
            SpannedString sb = (SpannedString)text;
            mGradientSpans = sb.getSpans(0, sb.length(), IGradientSpan.class);
            mGradientAnimSpans = sb.getSpans(0, sb.length(), IGradientAnimSpan.class);
        }

        if(mGradientSpans != null && mGradientSpans.length > 0) {
            for(int i = 0; i < mGradientSpans.length; i ++) {
                IGradientSpan gradientSpan = mGradientSpans[i];
                if(gradientSpan != null) {
                    String spanStr = gradientSpan.getSpanText();
                    if (spanStr != null) {
                        String content = text.toString();
//                        int start = content.indexOf(spanStr);
                        int start = gradientSpan.getStartIndex();
                        int end = start + spanStr.length(); // 不包括
                        GradientInfo rectF = getTextViewSelectionBottomY(start, end);
                        getPaint().setColor(Color.parseColor("#ff0000"));
                        gradientSpan.onDrawBefore(rectF.left, rectF.right);

                        Bitmap bitmap = gradientSpan.getGradientBitmap();
                        if(bitmap != null) {
                            canvas.save(); // 这里只是在一定的偏移绘制出原来的Bitmap，方便对比效果
                            canvas.translate(rectF.left + getPaddingStart(), Utils.dip2px(mContext, 38 + rectF.sLineNum * 45));
//                          canvas.drawRect(rectF, getPaint());
//                          canvas.drawBitmap(gradientSpan.getGradientBitmap(), new Matrix(), getPaint());

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
            mGradientSpans = sb.getSpans(0, sb.length(), IGradientSpan.class);
            mGradientAnimSpans = sb.getSpans(0, sb.length(), IGradientAnimSpan.class);
        }

        if(mGradientSpans != null && mGradientSpans.length > 0) {
            for(int i = 0; i < mGradientSpans.length; i ++) {
                IGradientSpan gradientSpan = mGradientSpans[i];
                if(gradientSpan != null) {
                    String spanStr = gradientSpan.getSpanText();
                    if (spanStr != null) {
                        String content = text.toString();
//                        int start = content.indexOf(spanStr);
                        int start = gradientSpan.getStartIndex();
                        int end = start + spanStr.length(); // 不包括
                        GradientInfo rectF = getTextViewSelectionBottomY1(start, end, spanStr);
                        getPaint().setColor(Color.parseColor("#ff0000"));
                        gradientSpan.onDrawBefore(rectF.left, rectF.right);

                        Bitmap bitmap = gradientSpan.getGradientBitmap();
                        if(bitmap != null) {
                            canvas.save(); // 这里只是在一定的偏移绘制出原来的Bitmap，方便对比效果
                            canvas.translate(rectF.left + getPaddingEnd(), Utils.dip2px(mContext, 38 + rectF.sLineNum * 45));
//                          canvas.drawRect(rectF, getPaint());
//                          canvas.drawBitmap(gradientSpan.getGradientBitmap(), new Matrix(), getPaint());

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
            mAnimator = ValueAnimator.ofInt(0, 100);
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
        }
    }

    public void stopAnim() {
        if(mAnimator != null) {
            mAnimator.cancel();
            mAnimator = null;
        }
        isAnim = false;
    }

}
