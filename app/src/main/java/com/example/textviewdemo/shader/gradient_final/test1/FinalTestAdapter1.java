package com.example.textviewdemo.shader.gradient_final.test1;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.textviewdemo.R;
import com.example.textviewdemo.shader.gradientanimspan.GradientAnimTextView;

import java.util.List;

/**
 * 测试
 */
public class FinalTestAdapter1 extends BaseMultiItemQuickAdapter<FinalTestBean1, BaseViewHolder> {

    public static final int TYPE_TEXT = 0; // 普通类型
    public static final int TYPE_GRADIENT = 1; // 渐变类型

    private Context mContext;

    public FinalTestAdapter1(Context context, List<FinalTestBean1> list) {
        super(list);
        this.mContext = context;
        addItemType(TYPE_TEXT, R.layout.item_final_test1_text);
        addItemType(TYPE_GRADIENT, R.layout.item_final_test1_gradient);
    }

    @Override
    protected void convert(BaseViewHolder helper, FinalTestBean1 item) {
        if (item == null) {
            return;
        }
        log("=============================> item: " + item.getContent());
        if (item.getItemType() == TYPE_GRADIENT) {
            GradientAnimTextView tvTest = helper.getView(R.id.tv_name);
//            tvTest.setContent(item.getsContent());
            tvTest.setText(item.getContent());
        } else if (item.getItemType() == TYPE_TEXT) {
            helper.setText(R.id.tv_name, item.getContent());
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

    public List<FinalTestBean1> getData() {
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
