package com.example.textviewdemo.gradientanimspan.test3;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.textviewdemo.R;
import com.example.textviewdemo.gradientanimspan.GradientAnimTextView;

import java.util.List;

/**
 * 测试
 */
public class GradientAnimSpanAdapter3 extends BaseMultiItemQuickAdapter<TestBean, BaseViewHolder> {

    public static final int TYPE_NORMAL = 0;//普通类型
    public static final int TYPE_SECTION = 1;//特殊类型

    private Context mContext;

    public GradientAnimSpanAdapter3(Context context, List<TestBean> list) {
        super(list);
        this.mContext = context;
        addItemType(TYPE_NORMAL, R.layout.item_gradient_anim_span_1);
        addItemType(TYPE_SECTION, R.layout.item_gradient_anim_span_2);
    }

    @Override
    protected void convert(BaseViewHolder helper, TestBean item) {
        if (item == null) {
            return;
        }
        log("=============================> item: " + item.getContent());
        if (item.getItemType() == TYPE_NORMAL) {
            GradientAnimTextView tvTest = helper.getView(R.id.tv_name);
            tvTest.setContent(item.getsContent());
        } else if (item.getItemType() == TYPE_SECTION) {
            helper.setText(R.id.tv_name, item.getContent());
            startAnim1(helper.getView(R.id.imgv_test));
        }
    }

    /**
     * 指定RecyclerView有多少个Item
     * @return
     */
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public List<TestBean> getData() {
        return mData;
    }
    
    public static void log(String str) {
        System.out.println("=================================> " + str);
    }

    private void startAnim1(View v) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "rotation", 0f, -360f);
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.setRepeatMode(ObjectAnimator.RESTART);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(2000);
        animator.start();
    }
}
