package com.example.textviewdemo.shader.gradient_final.test4;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.textviewdemo.R;
import com.example.textviewdemo.shader.gradient_final.rainbow_view.GradientAnimTextViewV2;

import java.util.List;

/**
 * 测试
 */
public class FinalTestAdapter4 extends BaseMultiItemQuickAdapter<FinalTestBean4, FinalTestAdapter4.MyViewHolder> {

    public static final int TYPE_TEXT = 0; // 普通类型
    public static final int TYPE_GRADIENT = 1; // 渐变类型

    public FinalTestAdapter4(Context context, List<FinalTestBean4> list) {
        super(list);
        addItemType(TYPE_TEXT, R.layout.item_final_test4_text);
        addItemType(TYPE_GRADIENT, R.layout.item_final_test4_gradient);
    }

    @Override
    protected void convert(FinalTestAdapter4.MyViewHolder helper, FinalTestBean4 item) {
        if (item == null) {
            return;
        }
        helper.mData = item;
        log("convert item: " + item.getContent());
        if (item.getItemType() == TYPE_GRADIENT) {
            GradientAnimTextViewV2 tvTest = helper.getView(R.id.tv_test);
//            tvTest.setContent(item.getsContent());
            tvTest.setContent(item.getsContent());
        } else if (item.getItemType() == TYPE_TEXT) {
            helper.setText(R.id.tv_name, item.getContent());
        }
    }

    /**
     * 获取显示类型，返回值可在onCreateViewHolder中拿到，以决定加载哪种ViewHolder
     * @param position position to query
     * @return
     */
    @Override
    public int getItemViewType(int position) {
//        log("getItemViewType position: " + position);
        return super.getItemViewType(position);
    }

    /**
     * 加载ViewHolder的布局
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return
     */
    @Override
    public FinalTestAdapter4.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        log("onCreateViewHolder viewType: " + viewType);
        return super.onCreateViewHolder(parent, viewType);
    }

    /**
     * 将数据绑定到布局上，以及一些逻辑的控制就写这啦
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(FinalTestAdapter4.MyViewHolder holder, int position) {
        log("onBindViewHolder position: " + position +  " type: " + holder.getType());
        super.onBindViewHolder(holder, position);
    }

    /**
     * 当Item进入这个页面的时候调用
     * @param holder Holder of the view being attached
     */
    @Override
    public void onViewAttachedToWindow(FinalTestAdapter4.MyViewHolder holder) {
        log("onViewAttachedToWindow " +  " type: " + holder.getType());
        super.onViewAttachedToWindow(holder);
    }

    /**
     * 当Item离开这个页面的时候调用
     * @param holder Holder of the view being detached
     */
    @Override
    public void onViewDetachedFromWindow(@NonNull FinalTestAdapter4.MyViewHolder holder) {
        log("onViewDetachedFromWindow " +  " type: " + holder.getType());
        super.onViewDetachedFromWindow(holder);
    }

    /**
     * 当Item被回收的时候调用
     * @param holder The ViewHolder for the view being recycled
     */
    @Override
    public void onViewRecycled(@NonNull FinalTestAdapter4.MyViewHolder holder) {
        log("onViewRecycled " +  " type: " + holder.getType());
        super.onViewRecycled(holder);
    }

    /**
     * 指定RecyclerView有多少个Item
     * @return
     */
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public List<FinalTestBean4> getData() {
        return mData;
    }
    
    public static void log(String str) {
        System.out.println("=================================> " + str);
    }

    public class MyViewHolder extends BaseViewHolder {

        public FinalTestBean4 mData;

        public MyViewHolder(View view) {
            super(view);
        }

        public int getType() {
            return mData != null ? mData.getItemType() : 0;
        }

        public FinalTestBean4 getData() {
            return mData;
        }

        public void setData(FinalTestBean4 data) {
            this.mData = data;
        }
    }
}
