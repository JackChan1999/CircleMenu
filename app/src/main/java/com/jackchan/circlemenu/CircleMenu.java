package com.jackchan.circlemenu;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ============================================================
 * Copyright：JackChan和他的朋友们有限公司版权所有 (c) 2017
 * Author：   JackChan
 * Email：    815712739@qq.com
 * GitHub：   https://github.com/JackChan1999
 * GitBook：  https://www.gitbook.com/@alleniverson
 * CSDN博客： http://blog.csdn.net/axi295309066
 * 个人博客： https://jackchan1999.github.io/
 * 微博：     AndroidDeveloper
 * <p>
 * Project_Name：CircleMenu
 * Package_Name：com.jackchan.circlemenu
 * Version：1.0
 * time：2017/5/23 15:14
 * des ：圆形菜单
 * gitVersion：2.12.0.windows.1
 * updateAuthor：AllenIverson
 * updateDate：2017/5/23 15:14
 * updateDes：${TODO}
 * ============================================================
 */

public class CircleMenu extends ViewGroup {
	public CircleMenu(Context context) {
		this(context, null);
	}

	public CircleMenu(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CircleMenu(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {

	}

	private int d = 480;

	//onMeasure:处理控件及其子控件的测量
	//在自定义ViewGroup中,系统默认只处理当前控件的测量,不对子视图进行测量,我们要重写onMeasure,实现对子视图的测量
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		super.onMeasure(widthMeasureSpec,heightMeasureSpec);
		//setMeasuredDimension:设置控件最终的测量宽高
		//需求:不管用户传多大,都要完全显示当前自定义控件
		int measureWidth;
		int measureHeight;
		//判断模式:
		//判断模式主要处理两种:1,确切的 2,至多
		int mode = MeasureSpec.getMode(widthMeasureSpec);
		int size = MeasureSpec.getSize(widthMeasureSpec);
		if (mode != MeasureSpec.EXACTLY) {
			//WRAP_CONTENT
			//未指定
			//指定包裹内容是包裹的背景
			//背景有多大,控件就有多大
			//获取背景的宽度
			int suggestedMinimumWidth = getSuggestedMinimumWidth();
			//没有背景,判断将默认宽度作为控件宽度
			//如果suggestedMinimumWidth值为0,则无背景
			if (suggestedMinimumWidth == 0) {
				measureWidth = measureHeight = getDefaultWidth();
			} else {
				measureWidth = measureHeight = Math.min(suggestedMinimumWidth, getDefaultWidth());
			}

		} else {
			//判断传入的宽度和屏幕宽度取较小值
			measureWidth = measureHeight = Math.min(size, getDefaultWidth());
		}

		d = measureWidth;

		setMeasuredDimension(measureWidth, measureHeight);
		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);
			//默认系统是可以通过onMeasure给予MeasureSpec参数的,而对于inflate进来的子视图是没有MeasureSpec参数的
			//故:需要我们自己设计MeasureSpec
			int makeMeasureSpec = MeasureSpec.makeMeasureSpec(d / 3, MeasureSpec.EXACTLY);
			child.measure(makeMeasureSpec, makeMeasureSpec);
		}
	}

	private int getDefaultWidth() {
		//获取屏幕宽高
		DisplayMetrics outMetrics = getResources().getDisplayMetrics();
		int width = outMetrics.widthPixels;
		int height = outMetrics.heightPixels;
		//获取宽度和高度的较小值
		int result = Math.min(width, height);
		return result;
	}


	private int startAngle;

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);
			//获取孩子视图的测量宽度
			int childWidth = child.getMeasuredWidth();
			//temp:相当于自定义控件所在圆的圆心到子视图所在矩形的几何中心的距离
			float temp = d / 3.0f;
			int left = (int) (d / 2 + Math.round(temp * Math.cos(Math.toRadians(startAngle))) - childWidth / 2);
			int right = left + childWidth;
			int top = (int) (d / 2 + Math.round(temp * Math.sin(Math.toRadians(startAngle))) - childWidth / 2);
			int bottom = top + childWidth;
			child.layout(left, top, right, bottom);
			startAngle += 360 / getChildCount();
		}
	}

	public void setDatas(int[] resIds, String[] texts) {
		for (int i = 0; i < resIds.length; i++) {
			View view = View.inflate(getContext(), R.layout.circle_item, null);
			ImageView iv = (ImageView) view.findViewById(R.id.iv);
			TextView tv = (TextView) view.findViewById(R.id.tv);
			iv.setImageResource(resIds[i]);
			tv.setText(texts[i]);

			addView(view);
		}
	}

	private float lastX;
	private float lastY;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				lastX = x;
				lastY = y;
				break;
			case MotionEvent.ACTION_MOVE:
				float start = CircleUtil.getAngle(lastX, lastY, d);
				float end = CircleUtil.getAngle(x, y, d);
				float angle;
				//判断点击的点所处的象限,如果是1,4象限,角度值是正数,否则是负数
				if (CircleUtil.getQuadrant(x, y, d) == 1 || CircleUtil.getQuadrant(x, y, d) == 4) {
					angle = end - start;
				} else {
					angle = start - end;
				}
				startAngle += angle;
				//让界面重新布局和绘制
				requestLayout();
				lastX = x;
				lastY = y;

				break;
			case MotionEvent.ACTION_UP:
				break;
		}
		//return true 表示当前控件想要处理事件,如果没有其他控件想要处理,则所有的MotionEvent事件都会交给自己处理
		return true;
	}
}
