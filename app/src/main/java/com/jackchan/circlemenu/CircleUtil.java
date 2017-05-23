package com.jackchan.circlemenu;

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
 * des ：角度计算工具类
 * gitVersion：2.12.0.windows.1
 * updateAuthor：AllenIverson
 * updateDate：2017/5/23 15:14
 * updateDes：${TODO}
 * ============================================================
 */

public class CircleUtil {
	/**
	 * 根据触摸的位置，计算角度
	 *
	 * @param xTouch
	 * @param yTouch
	 * @param d 直径
	 * @return
	 */
	public static float getAngle(float xTouch, float yTouch,int d) {
		double x = xTouch - (d / 2f);
		double y = yTouch - (d / 2f);
		//hypot:通过两条直角边,求斜边
		return (float) (Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
	}

	/**
	 * 根据当前位置计算象限
	 *
	 * @param x
	 * @param y
	 * @param d 直径
	 * @return
	 */
	public static int getQuadrant(float x, float y,int d) {
		int tmpX = (int) (x - d / 2);
		int tmpY = (int) (y - d / 2);
		if (tmpX >= 0) {
			return tmpY >= 0 ? 4 : 1;
		} else {
			return tmpY >= 0 ? 3 : 2;
		}

	}
}
