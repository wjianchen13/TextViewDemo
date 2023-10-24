package com.example.textviewdemo.gradient;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({GradientTextView.slow, GradientTextView.normal, GradientTextView.fast})
@Retention(RetentionPolicy.SOURCE)
public @interface TranslateSpeed {
}
