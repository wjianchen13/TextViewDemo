package com.example.textviewdemo.shader.gradient_final.test5;

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
import com.example.textviewdemo.shader.gradient_final.manager.AnimManager;
import com.example.textviewdemo.shader.gradient_final.rainbow_view.spans.GradientAnimSpanV2;
import com.example.textviewdemo.shader.gradient_final.rainbow_view.spans.GradientSpanV2;

import java.util.ArrayList;
import java.util.List;

/**
 * 1.彩虹，需要支持滚动
 */
public class FinalGradientAnimTestActivity52 extends AppCompatActivity {

    private RecyclerView rvGradientAnim;
    private List<FinalTestBean5> mList;
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
        setContentView(R.layout.activity_final_gradient_anim_test52);
        rvGradientAnim = findViewById(R.id.tv_gradient_anim);
        initData();
        initRv();
    }

    private void initData() {
        i = 0;
        mList = new ArrayList<>();
        mList.add(getTextBean());
        mList.add(getTest3());
//        mList.add(getTest4());
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
        FinalTestAdapter5 adapter = new FinalTestAdapter5(this, mList);
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

    private FinalTestBean5 getTextBean() {
        FinalTestBean5 bean = new FinalTestBean5();
        bean.setContent("测试数据: " + (i ++));
        bean.setItemType(FinalTestAdapter5.TYPE_TEXT);
        return bean;
    }

    private FinalTestBean5 getTest1() {
        FinalTestBean5 bean = new FinalTestBean5();
        SpannableStringBuilder sContent = new SpannableStringBuilder();
        sContent.append(getSizeText(this, "hello ni hao ya!!", R.dimen.dp_20));
        sContent.append(" ");
        sContent.append(getGradientAnimText(this, "سجل ABمعركة BBالفريقCC", colors, sContent.length(), 1800)); // 18  32

        sContent.append(" ");
        sContent.append(getColorText(this, "可以", R.color.color_6200EE));
        sContent.append(" ");
        bean.setItemType(FinalTestAdapter5.TYPE_GRADIENT);
        bean.setsContent(sContent);
        return bean;
    }

    private FinalTestBean5 getTest2() {
        FinalTestBean5 bean = new FinalTestBean5();
        SpannableStringBuilder sContent = new SpannableStringBuilder();
        sContent.append(getGradientAnimText(this, "سجل ABمعركة BBالفريقCC", colors, sContent.length(), 1800)); // 18  32

        sContent.append(" ");
        sContent.append(getColorText(this, "可以", R.color.color_6200EE));
        sContent.append(" ");
        bean.setItemType(FinalTestAdapter5.TYPE_GRADIENT);
        bean.setsContent(sContent);
        return bean;
    }

    private FinalTestBean5 getTest3() {
        FinalTestBean5 bean = new FinalTestBean5();
        bean.setItemType(FinalTestAdapter5.TYPE_GRADIENT);
        bean.setRainbow(true);
        int color1 = ContextCompat.getColor(this, R.color.cffde3d32);
        int color2 = ContextCompat.getColor(this, R.color.cfffeb702);
        int color3 = ContextCompat.getColor(this, R.color.cff80ff00);
        int color4 = ContextCompat.getColor(this, R.color.cff00bfcb);
        bean.setColors(new int[]{color1, color2, color3, color4});
        bean.setContent("渐变，渐变动画列表使用 渐变，渐变动画列表使用1");
        return bean;
    }

    private FinalTestBean5 getTest4() {
        FinalTestBean5 bean = new FinalTestBean5();
        bean.setRainbow(false);
        bean.setItemType(FinalTestAdapter5.TYPE_GRADIENT);
        bean.setContent("渐变，渐变动画列表使用 渐变，渐变动画列表使用1");
        return bean;
    }

    /**
     * 回收测试
     * @param v
     */
    public void onTest2(View v) {
        AnimManager.getInstance().logAllView();
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
    public void onTest21(View v) {

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