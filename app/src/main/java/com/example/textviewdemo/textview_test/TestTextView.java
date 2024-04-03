package com.example.textviewdemo.textview_test;


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

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.textviewdemo.thumb.Utils;

/**
 * name: TestTextView
 * desc: 测试TextView，比如获取某个字符串在TextView中的位置
 * 左右渐变的方案
 * 切换到阿拉伯语测试，需要把isAr设置成true
 *
 *
 * 参考文档
 * Android——字母轨迹动画
 * https://blog.csdn.net/u012230055/article/details/103053999
 *
 * 阿语模式下的算法不一样
 * RTL模式下，当最后一个是渐变SPAN，计算最后一个字符右侧到左侧的距离时，会自动获取右侧下一个空白字符，
 * 如果此时span是在最右侧，会导致获取的是下一行空白字符的坐标，导致left比right大，需要判断一下，如果
 * 这个span位于最后一个，把右侧坐标设置成TextView的宽度
 * 不光是最后一个是渐变SPAN, 第一个是渐变SPAN，获取开始的偏移也会获取到上一行的最后位置的偏移。
 * 阿语模式下，使用getPrimaryHorizontal(index) 获取到的是第index个字符右边到左侧的距离，使用getPrimaryHorizontal(index)获取到的是字符左边到左侧的距离
 * 需要处理的问题
 * 1.最后要一个时单独的渐变Span
 * 2.最后只有一个字符的渐变Span
 *
 * GradientSpan应该是会自动计算padding的，所以传入的left和right不需要添加paddingStart和paddingEnd
 *
 * 阿拉伯语设置
 * 1.添加阿拉伯语支持
 *         android:textAlignment="viewStart"
 *         android:textDirection="locale"
 *         android:gravity="start"
 */
public class TestTextView extends AppCompatTextView {

    private Context mContext;
    private IGradientSpan[] mSpans;

    private boolean isAr = false;

    public TestTextView(Context context) {
        super(context);
    }

    public TestTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

    }

    public TestTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
    protected void onDraw(Canvas canvas) {
//        getLine();
        CharSequence c = getText();
        if(!isAr) {
            initSpans(canvas);
        } else {
            initSpans1(canvas);
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
            mSpans = sb.getSpans(0, sb.length(), IGradientSpan.class);
        }

        if(mSpans != null && mSpans.length > 0) {
            for(int i = 0; i < mSpans.length; i ++) {
                IGradientSpan gradientSpan = mSpans[i];
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
            mSpans = sb.getSpans(0, sb.length(), IGradientSpan.class);
        }

        if(mSpans != null && mSpans.length > 0) {
            for(int i = 0; i < mSpans.length; i ++) {
                IGradientSpan gradientSpan = mSpans[i];
                if(gradientSpan != null) {
                    String spanStr = gradientSpan.getSpanText();
                    if (spanStr != null) {
                        String content = text.toString();
//                        int start = content.indexOf(spanStr);
                        int start = gradientSpan.getStartIndex();
                        int end = start + spanStr.length(); // 不包括
                        GradientInfo rectF = getTextViewSelectionBottomY1(start, end);
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
     * 为了预防阿语模式下最后一个是渐变的Span，采取传入的startIndex = startIndex + 1 endIndex = endIndex - 1
     * 如果startIndex == endIndex + 1 只有一个字符
     * 如果startIndex == endIndex 2个字符
     * 如果startIndex > endIndex 大于2个字符
     * 其他不处理
     */
    private GradientInfo getTextViewSelectionBottomY1(int start, int end) {
        int startIndex = start + 1;
        int endIndex = end - 1;
        Layout layout = getLayout();
        GradientInfo rectF = new GradientInfo();
        Rect bound = new Rect();
        int startLine = layout.getLineForOffset(startIndex);//获取字符在第几行
        int endLine = layout.getLineForOffset(endIndex);
        layout.getLineBounds(startLine, bound);//获取该行的Bound区域
        if(startLine != endLine) { // 换行,绘制区域是TextView的宽度
            rectF.left = 0;//字符左边x坐标(相对于TextView)
            rectF.top = bound.top;//字符顶部y坐标(相对于TextView的坐标)
            rectF.right = getWidth() - getPaddingStart() - getPaddingEnd();//字符右边x坐标(相对于TextView)
            rectF.bottom = bound.bottom;//字符底部y坐标(相对于TextView的坐标)
            rectF.sLineNum = startLine;
            rectF.eLineNum = endLine;
        } else { // 同一行
            if(startIndex == endIndex + 1) { // 需要取当前字符后一个字符的距离
                rectF.left = layout.getPrimaryHorizontal(startIndex);
                CharSequence text = getText();
                if(text != null && startIndex - 1 >= 0 && startIndex - 1 < text.length()) {
                    Character c = text.charAt(startIndex - 1);
                    if(c != null && getPaint() != null) {
                        rectF.right = rectF.left + getPaint().measureText( String.valueOf(c));
                    }
                }
            } else if(startIndex == endIndex) {
                float center = layout.getPrimaryHorizontal(startIndex);
                CharSequence text = getText();
                if(text != null && startIndex - 1 >= 0 && startIndex - 1 < text.length()) {
                    Character lc = text.charAt(startIndex - 1);
                    if(lc != null && getPaint() != null) {
                        rectF.left = center - getPaint().measureText( String.valueOf(lc));
                    }
                    Character rc = text.charAt(startIndex);
                    if(rc != null && getPaint() != null) {
                        rectF.right = center + getPaint().measureText( String.valueOf(rc));
                        int width = getWidth() - getPaddingStart() - getPaddingEnd();
                        if(width < rectF.right) {
                            rectF.right = width;
                        }
                    }
                }

            } else if(startIndex < endIndex) {
                rectF.left = layout.getPrimaryHorizontal(startIndex - 1);//字符左边x坐标(相对于TextView)
                // RTL模式下，当最后一个是渐变SPAN，计算最后一个字符右侧到左侧的距离时，会自动获取右侧下一个空白字符，
                // 如果此时span是在最右侧，会导致获取的是下一行空白字符的坐标，导致left比right大，需要判断一下，如果
                // 这个span位于最后一个，把右侧坐标设置成TextView的宽度
                float right = layout.getPrimaryHorizontal(endIndex + 1);//字符右边x坐标(相对于TextView)
                if(right < rectF.left) {
                    right = getWidth() - getPaddingStart() - getPaddingEnd();
                }
                rectF.right = right;
            } else {
                rectF.left = getPaddingEnd();//字符左边x坐标(相对于TextView)
                rectF.right = getWidth() - getPaddingStart();//字符右边x坐标(相对于TextView)
            }

            rectF.top = bound.top;//字符顶部y坐标(相对于TextView的坐标)
            rectF.bottom = bound.bottom;//字符底部y坐标(相对于TextView的坐标)
            rectF.sLineNum = startLine;
            rectF.eLineNum = endLine;
        }
        return rectF;
    }

}
