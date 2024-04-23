package com.example.textviewdemo.shader.gradient_final.test1;

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
import com.example.textviewdemo.shader.gradientanimspan.GradientAnimSpan;
import com.example.textviewdemo.shader.gradientanimspan.GradientSpan1;
import com.example.textviewdemo.shader.gradientanimspan.test3.GradientAnimSpanAdapter3;
import com.example.textviewdemo.shader.gradientanimspan.test3.TestBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 1.彩虹，需要支持滚动
 */
public class FinalGradientAnimTestActivity12 extends AppCompatActivity {

    private RecyclerView rvGradientAnim;
    private List<TestBean> mList;
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
        setContentView(R.layout.activity_final_gradient_anim_test12);
        rvGradientAnim = findViewById(R.id.tv_gradient_anim);
        initData();
        initRv();
    }

    private void initData() {
        i = 0;
        mList = new ArrayList<>();
        mList.add(getTextBean());
        mList.add(getTest3());
//        mList.add(getTest2());
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
        FinalTestAdapter1 adapter = new FinalTestAdapter1(this, mList);
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

    private TestBean getTextBean() {
        TestBean bean = new TestBean();
        bean.setContent("测试数据: " + (i ++));
        bean.setItemType(FinalTestAdapter1.TYPE_TEXT);
        return bean;
    }

    private TestBean getTest1() {
        TestBean bean = new TestBean();
        SpannableStringBuilder sContent = new SpannableStringBuilder();
        sContent.append(getSizeText(this, "hello ni hao ya!!", R.dimen.dp_20));
        sContent.append(" ");
        sContent.append(getGradientAnimText(this, "سجل ABمعركة BBالفريقCC", colors, sContent.length(), 1800)); // 18  32

        sContent.append(" ");
        sContent.append(getColorText(this, "可以", R.color.color_6200EE));
        sContent.append(" ");
        bean.setItemType(FinalTestAdapter1.TYPE_GRADIENT);
        bean.setsContent(sContent);
        return bean;
    }

    private TestBean getTest2() {
        TestBean bean = new TestBean();
        SpannableStringBuilder sContent = new SpannableStringBuilder();
        sContent.append(getGradientAnimText(this, "سجل ABمعركة BBالفريقCC", colors, sContent.length(), 1800)); // 18  32

        sContent.append(" ");
        sContent.append(getColorText(this, "可以", R.color.color_6200EE));
        sContent.append(" ");
        bean.setItemType(FinalTestAdapter1.TYPE_GRADIENT);
        bean.setsContent(sContent);
        return bean;
    }

    private TestBean getTest3() {
        TestBean bean = new TestBean();
        bean.setItemType(GradientAnimSpanAdapter3.TYPE_GRADIENT);
        bean.setContent("渐变，渐变动画列表使用 渐变，渐变动画列表使用1");
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
            GradientSpan1 span = new GradientSpan1(txt, colors, startIndex, maxWidth);
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
            GradientAnimSpan span = new GradientAnimSpan(txt, colors, startIndex, maxWidth);
            spanString.setSpan(span, 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spanString;
    }


}