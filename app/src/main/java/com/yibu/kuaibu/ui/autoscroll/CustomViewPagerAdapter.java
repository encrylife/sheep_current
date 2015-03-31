package com.yibu.kuaibu.ui.autoscroll;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自定义视图幻灯片 适配器
 * 无限循环
 * @author hezb
 */
public class CustomViewPagerAdapter extends PagerAdapter{

	private final String TAG = "hezb";

	private int realCount = 0;

	private ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
			ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

	private List<View> viewList;
	
	/**
	 * 传入 自定义 view 的列表
	 */
	public CustomViewPagerAdapter(List<View> viewList) {
		if (viewList == null) {
			Log.e(TAG, "CustomViewPagerAdapter   viewList is null!");
			return;
		}
		realCount = viewList.size();
		this.viewList = viewList;
		for (int i = 0; i < viewList.size(); i++) {
			viewList.get(i).setLayoutParams(lp);
		}
	}

	@Override
	public int getCount() {
		return realCount == 0 ? 0 : Integer.MAX_VALUE;
	}
	/**
	 * @return 实际的 item 数
	 */
	public int getRealCount(){
		return realCount;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(View container, int position) {
		position = position % realCount;
		try {
			((ViewPager) container).addView(viewList.get(position));
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}

		return viewList.get(position);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		position = position % realCount;
		try {
			((ViewPager) container).addView(viewList.get(position));
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}

		return viewList.get(position);
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewGroup) container).removeView((View) object);
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewGroup) container).removeView((View) object);
	}

}
