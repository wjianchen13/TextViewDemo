package com.example.textviewdemo.shader.gradient_final.test3;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.textviewdemo.BaseApp;
import com.example.textviewdemo.R;
import com.example.textviewdemo.shader.gradient_final.rainbow_view.spans.GradientAnimSpanV2;
import com.example.textviewdemo.shader.gradient_final.rainbow_view.spans.GradientSpanV2;

import java.util.ArrayList;
import java.util.List;

/**
 * 1.彩虹，需要支持滚动
 */
public class FinalGradientAnimTestActivity32 extends AppCompatActivity {

    private RecyclerView rvGradientAnim;
    private List<FinalTestBean3> mList;
    private int[] colors = new int[] {
            ContextCompat.getColor(BaseApp.getInstance(), R.color.cffde3d32),
            ContextCompat.getColor(BaseApp.getInstance(), R.color.cfffeb702),
            ContextCompat.getColor(BaseApp.getInstance(), R.color.cff80ff00),
            ContextCompat.getColor(BaseApp.getInstance(), R.color.cff00bfcb)
    };

    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_gradient_anim_test32);
        rvGradientAnim = findViewById(R.id.tv_gradient_anim);
        initData();
        initRv();
    }

    private void initData() {
        i = 0;
        mList = new ArrayList<>();
        mList.add(getTextBean());
        mList.add(getTest3());
        mList.add(getTest4());
        mList.add(getTextBean());
        mList.add(getTextBean());
        mList.add(getTextBean());
        mList.add(getTextBean());
        mList.add(getTextBean());
        mList.add(getTextBean());
        mList.add(getTextBean());
        mList.add(getTextBean());
        mList.add(getTextBean());
        mList.add(getTextBean());
        mList.add(getTextBean());
        mList.add(getTextBean());
    }

    private void initRv() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        rvGradientAnim.setLayoutManager(manager);
        FinalTestAdapter3 adapter = new FinalTestAdapter3(this, mList);
        rvGradientAnim.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = 20;
                outRect.bottom = 20;
            }
        });
        rvGradientAnim.setAdapter(adapter);
    }

    private FinalTestBean3 getTextBean() {
        FinalTestBean3 bean = new FinalTestBean3();
        bean.setContent("测试数据: " + (i ++));
        bean.setItemType(FinalTestAdapter3.TYPE_TEXT);
        return bean;
    }

    private FinalTestBean3 getTest1() {
        FinalTestBean3 bean = new FinalTestBean3();
        SpannableStringBuilder sContent = new SpannableStringBuilder();
        sContent.append(getSizeText(this, "hello ni hao ya!!", R.dimen.dp_20));
        sContent.append(" ");
        sContent.append(getGradientAnimText(this, "سجل ABمعركة BBالفريقCC", colors, sContent.length(), 1800)); // 18  32

        sContent.append(" ");
        sContent.append(getColorText(this, "可以", R.color.color_6200EE));
        sContent.append(" ");
        bean.setItemType(FinalTestAdapter3.TYPE_GRADIENT);
        bean.setsContent(sContent);
        return bean;
    }

    private FinalTestBean3 getTest2() {
        FinalTestBean3 bean = new FinalTestBean3();
        SpannableStringBuilder sContent = new SpannableStringBuilder();
        sContent.append(getGradientAnimText(this, "سجل ABمعركة BBالفريقCC", colors, sContent.length(), 1800)); // 18  32

        sContent.append(" ");
        sContent.append(getColorText(this, "可以", R.color.color_6200EE));
        sContent.append(" ");
        bean.setItemType(FinalTestAdapter3.TYPE_GRADIENT);
        bean.setsContent(sContent);
        return bean;
    }

    private FinalTestBean3 getTest3() {
        FinalTestBean3 bean = new FinalTestBean3();
        bean.setItemType(FinalTestAdapter3.TYPE_GRADIENT);
        SpannableStringBuilder sContent = new SpannableStringBuilder();
        int[] colors = new int[] {
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cffde3d32),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cfffeb702),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cff80ff00),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cff00bfcb)
        };
        sContent.append(getGradientAnimText(this, "测试滚动和渐变同时存在的情况，需要设置singleLine=true，设置之后Shader不起作用", colors, sContent.length(), 1800)); // 18  32
        bean.setsContent(sContent);
        return bean;
    }

    private FinalTestBean3 getTest4() {
        FinalTestBean3 bean = new FinalTestBean3();
        bean.setItemType(FinalTestAdapter3.TYPE_GRADIENT);
        SpannableStringBuilder sContent = new SpannableStringBuilder();
        int[] colors = new int[] {
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cffde3d32),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cfffeb702),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cff80ff00),
                ContextCompat.getColor(BaseApp.getInstance(), R.color.cff00bfcb)
        };
        sContent.append(getGradientAnimText(this, "测试滚动和渐变", colors, sContent.length(), 1800)); // 18  32
        bean.setsContent(sContent);
        return bean;
    }

    /**
     * 渐变测试 IGradientSpan
     * @param v
     */
    public void onTest1(View v) {

    }

    /**
     * 获得指定内容大小和颜色字符串
     */
    public SpannableString getGradientText(Context context, String txt, int[] colors, int startIndex, int maxWidth) {
        SpannableString spanString = new SpannableString(txt);
        if (context != null && !TextUtils.isEmpty(spanString)) {
            GradientSpanV2 span = new GradientSpanV2(txt, colors, startIndex, maxWidth);
            spanString.setSpan(span, 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spanString;
    }

    /**
     * 获得指定内容大小和颜色字符串
     */
    public SpannableString getColorText(Context context, String txt, int colorId) {
        SpannableString spanString = new SpannableString(txt);
        if (context != null && !TextUtils.isEmpty(spanString)) {
            spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, colorId)), 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spanString;
    }

    /**
     * 获得指定内容大小和颜色字符串
     */
    public SpannableString getSizeText(Context context, String txt, int sizeId) {
        SpannableString spanString = new SpannableString(txt);
        if (context != null && !TextUtils.isEmpty(spanString)) {
            AbsoluteSizeSpan span = new AbsoluteSizeSpan(context.getResources().getDimensionPixelOffset(sizeId));
            spanString.setSpan(span, 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spanString;
    }

    /**
     * 渐变动画测试 IGradientAnimSpan
     * @param v
     */
    public void onTest2(View v) {

    }

    /**
     * 获得指定内容大小和颜色字符串
     */
    public SpannableString getGradientAnimText(Context context, String txt, int[] colors, int startIndex, int maxWidth) {
        SpannableString spanString = new SpannableString(txt);
        if (context != null && !TextUtils.isEmpty(spanString)) {
            GradientAnimSpanV2 span = new GradientAnimSpanV2(txt, colors, startIndex, maxWidth);
            spanString.setSpan(span, 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spanString;
    }


}