package com.example.textviewdemo.shader.gradient_final.test5;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.textviewdemo.R;
import com.example.textviewdemo.shader.gradient_final.utils.Constants;
import com.example.textviewdemo.shader.gradient_final.view.RainbowScrollTextViewV2;

import java.util.List;

/**
 * 测试
 */
public class FinalTestAdapter5 extends BaseMultiItemQuickAdapter<FinalTestBean5, BaseViewHolder> {

    public static final int TYPE_TEXT = 0; // 普通类型
    public static final int TYPE_GRADIENT = 1; // 渐变类型

    private Context mContext;

    public FinalTestAdapter5(Context context, List<FinalTestBean5> list) {
        super(list);
        this.mContext = context;
        addItemType(TYPE_TEXT, R.layout.item_final_test5_text);
        addItemType(TYPE_GRADIENT, R.layout.item_final_test5_gradient);
    }

    @Override
    protected void convert(BaseViewHolder helper, FinalTestBean5 item) {
        if (item == null) {
            return;
        }
//        log("=============================> item: " + item.getContent());
        if (item.getItemType() == TYPE_GRADIENT) {
            RainbowScrollTextViewV2 tvTest = helper.getView(R.id.tv_test);
//            tvTest.setContent(item.getsContent());
            tvTest.setViewTag(Constants.TAG4);
            tvTest.setContent(item.getContent(), item.isRainbow(), item.getColors());
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

    public List<FinalTestBean5> getData() {
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
