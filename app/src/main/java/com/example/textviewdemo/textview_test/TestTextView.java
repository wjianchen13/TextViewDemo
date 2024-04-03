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
 *
 *
 * 参考文档
 * Android——字母轨迹动画
 * https://blog.csdn.net/u012230055/article/details/103053999
 */
public class TestTextView extends AppCompatTextView {

    private Context mContext;
    private IGradientSpan[] mSpans;

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
        initSpans(canvas);
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

                        canvas.save(); // 这里只是在一定的偏移绘制出原来的Bitmap，方便对比效果
                        canvas.translate(rectF.left, Utils.dip2px(mContext, 38 + rectF.sLineNum * 45));
//                        canvas.drawRect(rectF, getPaint());
//                        canvas.drawBitmap(gradientSpan.getGradientBitmap(), new Matrix(), getPaint());
                        Bitmap bitmap = gradientSpan.getGradientBitmap();
                        canvas.drawBitmap(gradientSpan.getGradientBitmap(), new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), new RectF(0, 0, bitmap.getWidth(), 50), getPaint());
                        canvas.restore();
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
            rectF.left = getPaddingStart();//字符左边x坐标(相对于TextView)
            rectF.top = bound.top;//字符顶部y坐标(相对于TextView的坐标)
            rectF.right = getWidth() - getPaddingEnd();//字符右边x坐标(相对于TextView)
            rectF.bottom = bound.bottom;//字符底部y坐标(相对于TextView的坐标)
            rectF.sLineNum = startLine;
            rectF.eLineNum = endLine;
        } else { // 同一行
            rectF.left = layout.getPrimaryHorizontal(index);//字符左边x坐标(相对于TextView)
            rectF.top = bound.top;//字符顶部y坐标(相对于TextView的坐标)
            rectF.right = layout.getPrimaryHorizontal(end);//字符右边x坐标(相对于TextView)
            rectF.bottom = bound.bottom;//字符底部y坐标(相对于TextView的坐标)
            rectF.sLineNum = startLine;
            rectF.eLineNum = endLine;
        }
        return rectF;
    }
}
