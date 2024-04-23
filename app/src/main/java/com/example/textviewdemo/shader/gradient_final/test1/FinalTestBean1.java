package com.example.textviewdemo.shader.gradient_final.test1;

import android.text.SpannableStringBuilder;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class FinalTestBean1 implements MultiItemEntity {

    private String content;
    private int itemType;
    private SpannableStringBuilder sContent;

    public FinalTestBean1() {

    }

    public FinalTestBean1(String content, int itemType) {
        this.content = content;
        this.itemType = itemType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public SpannableStringBuilder getsContent() {
        return sContent;
    }

    public void setsContent(SpannableStringBuilder sContent) {
        this.sContent = sContent;
    }
}
