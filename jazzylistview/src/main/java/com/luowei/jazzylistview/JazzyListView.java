/*
 * Copyright (C) 2015 Two Toasters
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.luowei.jazzylistview;

import android.content.Context;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ListView;

public class JazzyListView extends ListView {
    private final JazzyHelper mHelper;
    private ViewPropertyAnimator animate;
    private float lastY;
    private int directionY;//标识滑动到底部或顶部

    public JazzyListView(Context context) {
        super(context);
        mHelper = init(context, null);
    }

    public JazzyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHelper = init(context, attrs);
    }

    public JazzyListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mHelper = init(context, attrs);
    }

    private JazzyHelper init(Context context, AttributeSet attrs) {
        JazzyHelper helper = new JazzyHelper(context, attrs);
        super.setOnScrollListener(helper);
        return helper;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        animate = animate().setDuration(250)
                .setInterpolator(new OvershootInterpolator());
//                .setInterpolator(new AccelerateDecelerateInterpolator());
    }

    @Override
    public final void setOnScrollListener(OnScrollListener l) {
        mHelper.setOnScrollListener(l);
    }

    /**
     * Sets the desired transition effect.
     *
     * @param transitionEffect Numeric constant representing a bundled transition effect.
     */
    public void setTransitionEffect(int transitionEffect) {
        mHelper.setTransitionEffect(transitionEffect);
    }

    /**
     * Sets the desired transition effect.
     *
     * @param transitionEffect The non-bundled transition provided by the client.
     */
    public void setTransitionEffect(JazzyEffect transitionEffect) {
        mHelper.setTransitionEffect(transitionEffect);
    }

    /**
     * Sets whether new items or all items should be animated when they become visible.
     *
     * @param onlyAnimateNew True if only new items should be animated; false otherwise.
     */
    public void setShouldOnlyAnimateNewItems(boolean onlyAnimateNew) {
        mHelper.setShouldOnlyAnimateNewItems(onlyAnimateNew);
    }

    /**
     * If true animation will only occur when scrolling without the users finger on the screen.
     *
     * @param onlyWhenFling
     */
    public void setShouldOnlyAnimateFling(boolean onlyWhenFling) {
        mHelper.setShouldOnlyAnimateFling(onlyWhenFling);
    }

    /**
     * Stop animations after the list has reached a certain velocity. When the list slows down
     * it will start animating again. This gives a performance boost as well as preventing
     * the list from animating under the users finger if they suddenly stop it.
     *
     * @param itemsPerSecond, set to JazzyHelper.MAX_VELOCITY_OFF to turn off max velocity.
     *                        While 13 is a good default, it is dependent on the size of your items.
     */
    public void setMaxAnimationVelocity(int itemsPerSecond) {
        mHelper.setMaxAnimationVelocity(itemsPerSecond);
    }

    /**
     * Enable this if you are using a list with items that should act like grid items.
     *
     * @param simulateGridWithList
     */
    public void setSimulateGridWithList(boolean simulateGridWithList) {
        mHelper.setSimulateGridWithList(simulateGridWithList);
        setClipChildren(!simulateGridWithList);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (directionY != 0) {
                    setEnabled(false);
                    if (directionY < 0) setPivotY(0);
                    else setPivotY(getMeasuredHeight());
                    setScaleY(Math.abs(ev.getY() - lastY) / (getMeasuredHeight() * 5) + 1f);
//                        setScaleX(Math.abs(ev.getY()-lastY)/(getMeasuredHeight()*5)+1f);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (directionY != 0) {
                    animate.scaleY(1);
//                    animate.scaleX(1);
                    directionY = 0;
                    setEnabled(true);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX,
                                   int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        this.directionY = deltaY;
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }
}
