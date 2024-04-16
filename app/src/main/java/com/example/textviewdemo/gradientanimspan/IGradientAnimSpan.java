package com.example.textviewdemo.gradientanimspan;

import com.example.textviewdemo.textview_test.IGradientSpan;

/**
 * 公共的动画接口
 */
public interface IGradientAnimSpan extends IGradientSpan {

    void onAnim(int progress);

}
