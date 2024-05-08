package com.example.textviewdemo.shader.gradient_final.manager;

import com.example.textviewdemo.shader.gradient_final.rainbow_view.interfaces.IGradientView;
import com.example.textviewdemo.thumb.Utils;

import java.util.HashSet;
import java.util.Set;

/**
 * 保存所有可能存在的GradientView，用来检查是否内存泄漏
 */
public class AnimManager {

    private static AnimManager INSTANCE = null;

    private boolean isDebug = true;

    private Set<IGradientView>  mViewSet;

    public static AnimManager getInstance() {
        if (INSTANCE == null) {
            synchronized (AnimManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AnimManager();
                }
            }
        }
        return INSTANCE;
    }

    /**
     *
     * @param type 0 GradientAnimTextViewV2 1 RainbowScrollTextViewV2
     * @param gradientView
     */
    public void addView(int type, IGradientView gradientView) {
        if(isDebug) {
            if (gradientView != null) {
                getViewSet().add(gradientView);
                Utils.log("addView type: " + type + "  viewTag: " + gradientView.getViewTag());
            }
        }
    }

    /**
     *
     * @param type 0 GradientAnimTextViewV2 1 RainbowScrollTextViewV2
     * @param gradientView
     */
    public void removeView(int type, IGradientView gradientView) {
        if(isDebug) {
            if (gradientView != null) {
                getViewSet().remove(gradientView);
                Utils.log("removeView type: " + type + "  viewTag: " + gradientView.getViewTag());
            }
        }
    }

    public void logAllView() {
        if(isDebug) {
            if(getViewSet().size() == 0) {
                Utils.log("getViewSet() empty");
            }
            for (IGradientView gradientView : getViewSet()) {
                if (gradientView != null) {
                    Utils.log("gradientView: " + gradientView + "  tag: " + gradientView.getViewTag());
                } else {
                    Utils.log("gradientView == null");
                }
            }
        }
    }

    private Set<IGradientView> getViewSet() {
        if(mViewSet == null)
            mViewSet = new HashSet<>();
        return mViewSet;
    }








}




