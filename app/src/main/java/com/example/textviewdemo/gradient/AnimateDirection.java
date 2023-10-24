package com.example.textviewdemo.gradient;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({GradientTextView.leftToRight, GradientTextView.topToBottom})
@Retention(RetentionPolicy.SOURCE)
public @interface AnimateDirection {
}
