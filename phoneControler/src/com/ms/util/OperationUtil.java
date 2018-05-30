package com.ms.util;

import java.io.IOException;

public class OperationUtil {

	static String libPath = Param.adbPath + " -s ";

	// 返回事件
	public static void backtrackEvent(String portId) {
		String pPort = "127.0.0.1:" + portId + " ";
		String command = "shell input keyevent 4";
		String exce1 = libPath + pPort + command;
		try {
			Process p2 = Runtime.getRuntime().exec(exce1);
			System.out.println("返回:" + p2.hashCode());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 点击事件
	public static void clickEvent(String portId) {

		String pPort = "127.0.0.1:" + portId + " ";
//		String command = "shell input tap 50 240";
		//240*400
				String command = "shell input tap 50 150";
		String exce1 = libPath + pPort + command;
		try {
			Process p2 = Runtime.getRuntime().exec(exce1);
			System.out.println("点击新闻事件:" + p2.hashCode());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 首页滑动事件
	public static void swipeMainPageEvent(String portId) {

		String pPort = "127.0.0.1:" + portId + " ";
		// 滑动间距为349
//		String command = "shell input swipe 11 509 11 240";
		//240*400
				String command = "shell input swipe 11 273 11 150";
		String exce1 = libPath + pPort + command;
		try {
			Process p2 = Runtime.getRuntime().exec(exce1);
			System.out.println("首页滑动:" + p2.hashCode());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 内容页滑动事件
	public static void swipeContentPageEvent(String portId) {

		String pPort = "127.0.0.1:" + portId + " ";
		// 滑动间距为349
//		String command = "shell input swipe 0 509 0 240";
		//240*400
				String command = "shell input swipe 0 300 0 150";
		String exce1 = libPath + pPort + command;
		try {
			Process p2 = Runtime.getRuntime().exec(exce1);
			System.out.println("内容页面滑动:" + p2.hashCode());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void fun(String port) {
		// 内容页返回首页
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		backtrackEvent(port);
		// 操作事件
		for (int i = 0; i < 10; i++) {
			// 先滑动，再点击
			// 点击进去后，滑动
			try {
				// 首页"推荐"菜单内滑动
				swipeMainPageEvent(port);
				Thread.sleep(1000);
				// 推荐页点击
				clickEvent(port);
				// 猜测内容加载时间
				Thread.sleep(3000);
				for (int j = 0; j < 10; j++) {
					// 内容页滑动
					swipeContentPageEvent(port);
					Thread.sleep(2000);
				}
				Thread.sleep(1000);
				// 内容页返回首页
				backtrackEvent(port);
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		fun("62001");
	}

}
