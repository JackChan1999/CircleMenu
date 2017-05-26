## 自定义控件之圆形菜单

<img src="art/circlemenu.jpg" width="300" />

## layout

如图所示，按钮之间的角度差等于360/按钮数，这里的角度差就是60度。根据几何知识可以算出每个按钮的左上右下坐标

![](art/circlemenu2.png)

![](art/circlemenu.png)

![](art/圆形菜单图示.png)

```java
private int d;
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
```

## 让菜单动起来

先通过CircleUtil.getAngle()算出手指滑动的两个点之间的角度差angle，再重新计算出按钮的开始角度startAngle += angle;调用requestLayout()方法，让界面重新布局和绘制，就可以让菜单动起来

```java
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
	return true;
}
```
CircleUtil，计算触摸点的角度和象限的工具类
```java
public class CircleUtil {
  
	public static float getAngle(float xTouch, float yTouch,int d) {
		double x = xTouch - (d / 2f);
		double y = yTouch - (d / 2f);
		//hypot:通过两条直角边,求斜边
		return (float) (Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
	}

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
```

## 摩天轮控件分析

![](art/摩天轮控件分析.png)