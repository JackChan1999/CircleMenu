package com.jackchan.circlemenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

public class MainActivity extends AppCompatActivity {
	private String[] texts = new String[]{"安全中心 ", "特色服务", "投资理财",
			"转账汇款", "我的账户", "信用卡"};
	private int[] imgs = new int[]{R.drawable.home_mbank_1_normal,
			R.drawable.home_mbank_2_normal, R.drawable.home_mbank_3_normal,
			R.drawable.home_mbank_4_normal, R.drawable.home_mbank_5_normal,
			R.drawable.home_mbank_6_normal};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		CircleMenu cm= (CircleMenu) findViewById(R.id.cm);
		cm.setDatas(imgs,texts);
	}
}
