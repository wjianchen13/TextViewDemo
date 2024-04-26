package com.example.textviewdemo.shader.gradient_final.test5;

import android.text.SpannableStringBuilder;

import androidx.annotation.ColorInt;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class FinalTestBean5 implements MultiItemEntity {

    private String content;
    private int itemType;
    private SpannableStringBuilder sContent;
    private boolean isRainbow;
    private @ColorInt int[] colors;

    public FinalTestBean5() {

    }

    public FinalTestBean5(String content, int itemType) {
        this.content = content;
        this.itemType = itemType;
    }

    public boolean isRainbow() {
        return isRainbow;
    }

    public void setRainbow(boolean rainbow) {
        isRainbow = rainbow;
    }

    public int[] getColors() {
        return colors;
    }

    public void setColors(int[] colors) {
        this.colors = colors;
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
