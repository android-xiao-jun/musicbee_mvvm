package com.youth.banner.listener;

import androidx.annotation.Px;
import androidx.viewpager2.widget.ViewPager2;


public interface OnPageChangeListener {
    /**
     * This method will be invoked when the current page is scrolled, either as part
     * of a programmatically initiated smooth scroll or a user initiated touch scroll.
     *
     * @param position             Position index of the first page currently being displayed.
     *                             Page position+1 will be visible if positionOffset is nonzero.
     * @param positionOffset       Value from [0, 1) indicating the offset from the page at position.
     * @param positionOffsetPixels Value in pixels indicating the offset from position.
     */
    void onPageScrolled(int position, float positionOffset, @Px int positionOffsetPixels);

    /**
     * This method will be invoked when a new page becomes selected. Animation is not
     * necessarily complete.
     *
     * @param position Position index of the new selected page.
     */
    void onPageSelected(int position);

    /**
     * Called when the scroll state changes. Useful for discovering when the user begins
     * dragging, when a fake drag is started, when the pager is automatically settling to the
     * current page, or when it is fully stopped/idle. {@code state} can be one of
     * {@link ViewPager2.SCROLL_STATE_IDLE},
     * {@link ViewPager2.SCROLL_STATE_DRAGGING},
     * {@link ViewPager2.SCROLL_STATE_SETTLING}.
     */
    void onPageScrollStateChanged(@ViewPager2.ScrollState int state);

    /**
     * 和 onPageSelected 区别就是 任何时候都会回调 然后子类判断当前index是否同一个就可以
     * 考虑到 setCurrentItem第二个参数为false的时候，不执行的问题
     * 因为这个banner，无限轮播 ==》6条数据  从 1 开始 1 2 3 4 5 6  第 7 的时候 平移到 0 -》
     * 相反  从 1 开始 到 0 的时候 平移到 6 然后 6 5 4 3 2 1
     */
    void onPageSelectedAnyTme(int position);
}
