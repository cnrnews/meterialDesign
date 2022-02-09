package com.netease.materialdesign.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

/**
 * @Author lihl
 * @Date 2022/2/9 17:51
 * @Email 1601796593@qq.com
 */
public class ScaleBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {

    public ScaleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL; // 垂直滚动
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, @NonNull int[] consumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed);
    
        if (dyConsumed>0 && !isRunning && child.getVisibility() == View.VISIBLE){
            // 向上滑动，缩放隐藏控件
            scaleHide(child);
        }else if (dyConsumed>0 && !isRunning && child.getVisibility() == View.INVISIBLE){
            // 向上滑动，缩放显示控件
            scaleShow(child);
        }
    }

    // 动画执行状态
    private boolean isRunning;
    FastOutLinearInInterpolator fastOutLinearInInterpolator = new FastOutLinearInInterpolator();
    LinearOutSlowInInterpolator linearOutSlowInInterpolator = new LinearOutSlowInInterpolator();
    private void scaleHide(final V child) {

        ViewCompat.animate(child)
                .scaleX(0)
                .scaleY(0)
                .setDuration(500)
                .setInterpolator(fastOutLinearInInterpolator)
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {
                        isRunning = true;
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        isRunning = false;
                        child.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(View view) {
                        isRunning = true;
                    }
                });

    }
    private void scaleShow(final V child) {
        ViewCompat.animate(child)
                .scaleX(1)
                .scaleY(1)
                .setDuration(500)
                .setInterpolator(linearOutSlowInInterpolator)
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {
                        isRunning = true;
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        isRunning = false;
                        child.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(View view) {
                        isRunning = true;
                    }
                });
    }
}
