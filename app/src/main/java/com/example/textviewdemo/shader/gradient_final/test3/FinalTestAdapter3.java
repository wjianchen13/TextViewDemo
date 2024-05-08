package com.example.textviewdemo.shader.gradient_final.test3;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.textviewdemo.R;
import com.example.textviewdemo.shader.gradient_final.rainbow_view.GradientAnimTextViewV2;

import java.util.List;

/**
 * 测试
 */
public class FinalTestAdapter3 extends BaseMultiItemQuickAdapter<FinalTestBean3, BaseViewHolder> {

    public static final int TYPE_TEXT = 0; // 普通类型
    public static final int TYPE_GRADIENT = 1; // 渐变类型

    private Context mContext;

    public FinalTestAdapter3(Context context, List<FinalTestBean3> list) {
        super(list);
        this.mContext = context;
        addItemType(TYPE_TEXT, R.layout.item_final_test3_text);
        addItemType(TYPE_GRADIENT, R.layout.item_final_test3_gradient);
    }

    @Override
    protected void convert(BaseViewHolder helper, FinalTestBean3 item) {
        if (item == null) {
            return;
        }
        log("=============================> item: " + item.getContent());
        if (item.getItemType() == TYPE_GRADIENT) {
            GradientAnimTextViewV2 tvTest = helper.getView(R.id.tv_test);
//            tvTest.setContent(item.getsContent());
            tvTest.setContent(item.getsContent());
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

    public List<FinalTestBean3> getData() {
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
