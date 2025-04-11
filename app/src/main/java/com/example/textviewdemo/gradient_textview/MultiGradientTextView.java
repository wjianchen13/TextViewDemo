package com.example.textviewdemo.gradient_textview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ReplacementSpan;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import java.util.ArrayList;
import java.util.List;

public class MultiGradientTextView extends AppCompatTextView {

    private final List<GradientSpan> spans = new ArrayList<>();
    private static boolean isRtl = false;

    public MultiGradientTextView(@NonNull Context context) {
        super(context);
    }

    public MultiGradientTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MultiGradientTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // 添加渐变片段
    public void addGradientSpan(String text, int[] colors) {
        if (getText() == null) {
            throw new RuntimeException("you must set a text first!!!");
        }
        if (!getText().toString().contains(text)) {
            throw new RuntimeException("source text must contains target text!!!");
        }
        spans.add(new GradientSpan(text, colors));
    }

    public void updateSpannable() {
        if (getText() == null) {
            return;
        }
        SpannableString spannable = new SpannableString(getText());
        for (GradientSpan span : spans) {
            if (span.text != null) {
                int index = getText().toString().indexOf(span.text);
                if (index != -1) {
                    spannable.setSpan(span, index, index + span.text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
        setText(spannable);
    }

    public void clearSpannable() {
        spans.clear();
        setText(getText().toString());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 清空原有绘制
        canvas.save();
        super.onDraw(canvas);
        canvas.restore();
    }

    public static class GradientSpan extends ReplacementSpan {

        private final String text;
        private final int[] colors;

        public GradientSpan(String text, int[] colors) {
            this.text = text;
            this.colors = colors;
        }

        @Override
        public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
            return (int) paint.measureText(text, start, end);
        }

        @Override
        public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
            // 保存原始 Shader
            Shader originalShader = paint.getShader();

            // 计算渐变方向
            float x1 = !isRtl ? x : x + paint.measureText(text, start, end);
            float x2 = !isRtl ? x + paint.measureText(text, start, end) : x;

            LinearGradient gradient = new LinearGradient(
                    x1, y, x2, y,
                    colors, null,
                    Shader.TileMode.CLAMP
            );
            paint.setShader(gradient);

            // 绘制文本
            canvas.drawText(text, start, end, x, y, paint);

            // 恢复原始 Shader
            paint.setShader(originalShader);
        }
    }
}

