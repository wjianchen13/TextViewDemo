package com.example.textviewdemo.shader.textview_test;


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
 * 切换到阿拉伯语测试，需要把isAr设置成true，否则显示有问题
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
 * 3.阿语模式下当渐变是第一个的时候，取index = 0 等于控件宽度
 *
 *
 * GradientSpan应该是会自动计算padding的，所以传入的left和right不需要添加paddingStart和paddingEnd
 *
 * 阿拉伯语设置
 * 1.添加阿拉伯语支持
 *         android:textAlignment="viewStart"
 *         android:textDirection="locale"
 *         android:gravity="start"
 *
 * 纯阿语 如果是同一行
 * get(1)       942 -55
 * get(0)       997
 * get(end)   436
 *
 * 纯英文
 * get(1)       316
 * get(0)       997
 * get(end)   810
 *
 * 阿语模式下，通过 getPrimaryHorizontal()获取的是字符右侧到控件左侧的距离
 * 1.阿语和英语混合的情况下 getPrimaryHorizontal(0) = 997 lastIndex = 170，测试文本："سجل AAمعركة BBالفريقCC" 结论，
 * 计算的是字符右侧到控件左边的距离，所以计算最后要一个字符到左侧的距离需要getPrimaryHorizontal(lastIndex) - 最后一个字符的宽度
 * getPrimaryHorizontal(index) 获取到的是第index字符到控件左边的距离
 * 2.纯阿语的情况下，layout.getPrimaryHorizontal(0) = 997 layout.getPrimaryHorizontal(lastIndex) = 561
 *
 * 3.纯英语的情况下，layout.getPrimaryHorizontal(0) = 997  layout.getPrimaryHorizontal(1) = 115 第一个字符在左边  layout.getPrimaryHorizontal(lastIndex) = 495
 * 如果渐变span后面还有其他字符串，第0个也是直接指到控件最右侧，
 * 假设span前面还有字符，那layout.getPrimaryHorizontal(lastIndex) = 前面字符左侧到控件左侧的距离
 * 假设span后面还有字符，那layout.getPrimaryHorizontal(0) = 控件宽度，也会包括后面的字符
 * 如果是存英文，layout.getPrimaryHorizontal()获取到的是字符左侧距离控件左侧的距离
 *
 * 如果在同一行的情况下，处理的细节参考上面1 2 3
 * 4 中情况 需要处理
 * 需要判断该字符串是否在一行的开始位置
 * 是否在一行的结束位置
 * 是否既是开始，又是结束
 * 既不是开始，又不是结束
 * span 分 一个字符 2 个 和3个
 *
 * 流程
 *
 * 单行
 * 1.既是行首也是行尾
 *      直接交换 start 和 end
 * 2.如果在行首，不在行尾， 不能用start，因为这样会包括span后面的非span区域
 *      走下面区分长度，计算顺序
 * 3.如果在行尾，不在行首，不能用end，因为这样会包括其那面的非span区域
 *      走下面区分长度，计算顺序
 * 4.如果在中间，start和end都可以使用
 *      直接交换 start 和 end
 *
 * 区分长度，计算顺序
 * 计算一行的长度，区分
 * 有换行符的另外处理
 * 1 length = 1 这个应该就是单独一行
 * 2. length = 2计算end=index+1 start=end-getPaint().measureText
 * 3.length 大于等于3
 *
 * 多行
 * 直接start = 0 end = width
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

}
