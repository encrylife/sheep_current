package com.yibu.kuaibu.ui.autoscroll;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * 自动滚动 ViewPager
 * 
 * @author hezb
 */
public class CustomViewPager extends ViewPager {

    private final String TAG = "hezb";

    private Handler mHandler = new Handler();
    private GestureDetector mGestureDetector;
    private SingleTapUpListener mSingleTapUpListener;
    private ViewPagerTouch mViewPagerTouch;
    private int AUTO_SCROLL_TIME = 3000; // ms
    private int realCount = 0;

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mGestureDetector = new GestureDetector(getContext(),
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        if (mSingleTapUpListener != null) {
                            return mSingleTapUpListener.onSingleTapUp(e);
                        }
                        return false;
                    }
                });
    }

    /**
     * 本ViewPager的touch事件已经被处理 需要ViewPager的单击事件 要实现此接口
     */
    public interface SingleTapUpListener {
        public boolean onSingleTapUp(MotionEvent e);
    }

    /**
     * 设置 单击 接口
     */
    public void setOnSingleTapUpListener(SingleTapUpListener l) {
        mSingleTapUpListener = l;
    }

    /**
     * 本ViewPager的touch事件已经被处理 需要ViewPager的touch事件 要实现此接口
     */
    public interface ViewPagerTouch {
        public void onTouchEvent(MotionEvent e);
    }

    /**
     * 设置 触摸 接口
     */
    public void setViewPagerTouchListener(ViewPagerTouch mViewPagerTouch) {
        this.mViewPagerTouch = mViewPagerTouch;
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        mGestureDetector.onTouchEvent(arg0);
        if (mViewPagerTouch != null) {
            mViewPagerTouch.onTouchEvent(arg0);
        }
        if (arg0.getAction() == MotionEvent.ACTION_DOWN) {
            stopAutoScroll();
        } else if (arg0.getAction() == MotionEvent.ACTION_UP) {
            startAutoScroll();
        }
        return super.onTouchEvent(arg0);
    }

    /**
     * 实现自动滚动 必须使用此方法设置适配器 Adapter 的 getCount() 值为 Integer.MAX_VALUE(足够大)
     * realCount 为实际的 item 数
     */
    public void setAdapter(android.support.v4.view.PagerAdapter arg0,
            int realCount) {
        setAdapter(arg0);
        this.realCount = realCount;
        if (getAdapter().getCount() > (realCount * 10000)) {
            setCurrentItem(realCount * 10000);
            startAutoScroll();
        }

    };

    /**
     * @return 实际的 item 位置
     */
    public int getRealCurrentItem() {
        return getCurrentItem() % realCount;
    }

    /**
     * 自动滚动 线程
     */
    private Runnable autoScroll = new Runnable() {

        @Override
        public void run() {
            if ((getCurrentItem() + 1) < getAdapter().getCount()) {
                setCurrentItem(getCurrentItem() + 1, true);
                mHandler.postDelayed(autoScroll, AUTO_SCROLL_TIME);
            } else {
                setCurrentItem(0, false);
                Log.d(TAG, "currentItem > itemCount");
            }
        }
    };

    /**
     * 停止滚动
     */
    public void stopAutoScroll() {
        mHandler.removeCallbacks(autoScroll);
    }

    /**
     * 开始滚动 3秒延迟
     */
    public void startAutoScroll() {
        if (getAdapter() == null || getAdapter().getCount() <= 0) {
        } else {
            mHandler.postDelayed(autoScroll, AUTO_SCROLL_TIME);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        mHandler.removeCallbacks(autoScroll);
        super.onDetachedFromWindow();
    }

}
