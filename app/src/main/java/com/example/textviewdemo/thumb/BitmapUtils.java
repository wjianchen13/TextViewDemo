package com.example.textviewdemo.thumb;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class BitmapUtils {

    public static Bitmap createEntryFontBitmap(Activity activity, String nick, String coming, String color, int shadowColor, int textSize, int textGravity,
                                               boolean isBold, boolean isItalic) {
        int width = 500;
        int height = 86;
        int realHeight = 56;
        if(TextUtils.isEmpty(nick ) || TextUtils.isEmpty(coming)) {
            return null;
        }
        int maxLen = 18;
        int minLen = 13;
        int count = maxLen - minLen;

//        if(GlobalConfig.isTest()) {
//            nick = "abcdefghijklmnopq";
//        }
        nick = "ـ❥ᬼ وكالة ༄༵ فخر ༄༵العرب ـ❥ᬼ";
        String suffix = nick.length() > maxLen ? ".. " : " ";


        if(nick.length() > maxLen) {
            nick = nick.substring(0, maxLen + 1);
        }

        String name = nick + suffix + coming;

        TextView bitmapFactoryView = new TextView(activity.getApplicationContext());
        bitmapFactoryView.setTextDirection(View.TEXT_DIRECTION_LTR);
        bitmapFactoryView.setBackgroundColor(Color.parseColor("#0000ff"));
        if (isBold && isItalic) {
            bitmapFactoryView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
        } else if (isBold) {
            bitmapFactoryView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }
//        bitmapFactoryView.setBackgroundColor(Color.parseColor("#ff0000"));
        bitmapFactoryView.setIncludeFontPadding(false);
        bitmapFactoryView.setVisibility(View.VISIBLE);
        bitmapFactoryView.setDrawingCacheEnabled(true);
        bitmapFactoryView.setMaxLines(2);
        bitmapFactoryView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 36);
        if (shadowColor != 0) {
            bitmapFactoryView.setShadowLayer(2, 0, 2, shadowColor);
        }

        bitmapFactoryView.setGravity(textGravity);
        bitmapFactoryView.setTextColor(Color.parseColor(color));

        //获取文字宽度
        float textWidth = bitmapFactoryView.getPaint().measureText(name);
        String subContent = name;
        int subCount = 1;
        try {
            while (textWidth > width - dip2px(activity, 12) && subCount < nick.length() && count < subCount) {
                subContent = nick.substring(0, nick.length() - subCount) + suffix + coming;
                textWidth = bitmapFactoryView.getPaint().measureText(subContent);
                subCount ++;
            }
            name = subContent;
        } catch (Exception e) {
            e.printStackTrace();
        }
        bitmapFactoryView.setText(name);
        switch (textGravity) {
            case Gravity.START:
            case Gravity.START | Gravity.CENTER:
                bitmapFactoryView.setEllipsize(TextUtils.TruncateAt.END);
                break;
            case Gravity.END:
            case Gravity.END | Gravity.CENTER:
                bitmapFactoryView.setEllipsize(TextUtils.TruncateAt.START);
                break;
        }
        bitmapFactoryView.measure(View.MeasureSpec.makeMeasureSpec(Math.max(width, 0), width > 0 ? View.MeasureSpec.EXACTLY : View.MeasureSpec.UNSPECIFIED)
                , View.MeasureSpec.makeMeasureSpec(Math.max(height, 0), height > 0 ? View.MeasureSpec.EXACTLY : View.MeasureSpec.UNSPECIFIED));
        bitmapFactoryView.layout(0, 0, bitmapFactoryView.getMeasuredWidth(), bitmapFactoryView.getMeasuredHeight());
        bitmapFactoryView.buildDrawingCache();
        Bitmap bitmap = view2Bitmap(bitmapFactoryView);
        int bWidth = bitmap.getWidth();
        int bHeight = bitmap.getHeight();
        System.out.println("======================> height: " + height + "  bHeight: " + bHeight + "   bWidth: " + bWidth);

        Bitmap bitmap1 = Bitmap.createBitmap(bitmap, 0, (height - realHeight) / 2, bWidth, realHeight);
        bitmap.recycle();
        return bitmap1;
    }

    /**
     * View to bitmap.
     *
     * @param view The view.
     * @return bitmap
     */
    public static Bitmap view2Bitmap(final View view) {
        if (view == null) return null;
        boolean drawingCacheEnabled = view.isDrawingCacheEnabled();
        boolean willNotCacheDrawing = view.willNotCacheDrawing();
        view.setDrawingCacheEnabled(true);
        view.setWillNotCacheDrawing(false);
        Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitmap;
        if (null == drawingCache || drawingCache.isRecycled()) {
            view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            view.buildDrawingCache();
            drawingCache = view.getDrawingCache();
            if (null == drawingCache || drawingCache.isRecycled()) {
                bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.RGB_565);
                Canvas canvas = new Canvas(bitmap);
                view.draw(canvas);
            } else {
                bitmap = Bitmap.createBitmap(drawingCache);
            }
        } else {
            bitmap = Bitmap.createBitmap(drawingCache);
        }
        view.setWillNotCacheDrawing(willNotCacheDrawing);
        view.setDrawingCacheEnabled(drawingCacheEnabled);
        return bitmap;
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        return (int) (dpValue * getDensity(context) + 0.5f);
    }


    /**
     * 返回屏幕密度
     */
    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

}
