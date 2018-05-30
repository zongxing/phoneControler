package com.ms.util;

import java.io.InputStream;
import java.util.Scanner;

public class Param {
	static String xmlFilePath = XmlUtil.getProperty("xmlFilePath",
			"C:\\Users\\Administrator\\AppData\\Local\\MultiPlayerManager\\multiplayer.xml");
	// static String xmlFilePath =
	// "C:\\Users\\zongx\\AppData\\Local\\MultiPlayerManager\\multiplayer.xml";
	static String binPath = XmlUtil.getProperty("binPath", "D:\\Program Files\\Nox\\bin\\");
	// static String binPath = "D:\\soft\\yeshen\\Nox\\bin\\";
	/**
	 * 一次批量启动的个数
	 */
	static int batchStartSize = Integer.parseInt(XmlUtil.getProperty("batchStartSize", "5"));
	static String adbPath = binPath + "adb.exe";
	static String noxPath = binPath + "nox.exe";
	// 分辨率
	// static String resolutionValue = "480x800";
	static String resolutionValue = XmlUtil.getProperty("resolutionValue", "240x400");
	static String startAppName = XmlUtil.getProperty("startAppName",
			"cn.weli.story/cn.etouch.ecalendar.LoadingActivity");
	static int checkSimulatorIsStartNum = Integer.valueOf(XmlUtil.getProperty("checkSimulatorIsStartNum", "20"));;
	static long sleepTime = Long.parseLong(XmlUtil.getProperty("sleepTime", String.valueOf(30000)));
	// public static void main(String[] args) {
	// InputStream is =
	// Param.class.getClassLoader().getResourceAsStream("config.xml");
	// Scanner s = new Scanner(is);
	// while(s.hasNext()) {
	// System.out.println(s.nextLine());
	// }
	// s.close();
	// }
}
