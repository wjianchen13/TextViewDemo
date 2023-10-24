package com.example.textviewdemo.vertical;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * FrameLayout本来就可以实现子控件大于父控件高度，居中显示子控件的垂直居中的部分
 * 但是需要子控件测量的时候给一个尽可能多的高度，否则子控件的高度会被限制成父控件的高度
 */
public class MyLayout extends FrameLayout {

    public MyLayout(@NonNull Context context) {
        super(context);
    }

    public MyLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        View v = getChildAt(0);

        if(v != null) {
            System.out.println("========================> measure width: " + getMeasuredWidth() + "   measure height: " + getMeasuredHeight());
            System.out.println("========================> child measure width: " + v.getMeasuredWidth() + "   child measure height: " + v.getMeasuredHeight());
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        View v = getChildAt(0);
        int measureWidth = v.getMeasuredWidth();
        int childWidth = v.getWidth();
        int measureHeight = v.getMeasuredHeight();
        int childHeight = v.getHeight();
        System.out.println("========================> onLayout measureWidth: " + measureWidth + "   childWidth: " + childWidth + "   measureHeight: " + measureHeight + "   childHeight: " + childHeight);
        if(v != null) {
            int cl = v.getLeft();
            int ct = v.getTop();
            int cr = v.getRight();
            int cb = v.getBottom();
            System.out.println("========================> my width: " + getWidth() + "   my height: " + getHeight());
            System.out.println("========================> cl: " + cl + "   ct: " + ct + "   cr: " + cr + "   cb: " + cb);
            int childMeasureHeight = v.getMeasuredHeight();
            int height = getHeight();
            if(height < childMeasureHeight) {
                int offset = (childMeasureHeight - height ) / 2;
//                v.layout(v.getLeft(), v.getTop() - offset, v.getRight(), v.getBottom() - offset);
            }
            System.out.println("========================> childMeasureHeight: " + childMeasureHeight + "   height: " + height);

        }
    }
}
