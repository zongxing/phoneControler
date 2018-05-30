package com.ms.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AdbUtilTest {
	static String command = "D:\\soft\\yeshen\\Nox\\bin\\adb.exe -s 127.0.0.1:62001 ";

	@Test
	void test() throws InterruptedException {
		// -clone:Nox_0
		//Æô¶¯Ä£ÄâÆ÷
		
		Thread t = new Thread(new Runnable() {
			public void run() {
				AdbUtil.launch("D:\\soft\\yeshen\\Nox\\bin\\Nox.exe");
			}
		});
		t.start();
		Thread.sleep(17000);
//		//Æô¶¯app
//		AdbUtil.launch(command+"shell am start -n com.sohu.youju/com.sohu.youju.app.ui.activity.HelloActivity");
		AdbUtil.launch(command+"shell am start -n com.lightsky.video/.MainActivity");
		//Thread.sleep(10000);
		//AdbUtil.slide("D:\\soft\\yeshen\\Nox\\bin\\adb.exe ");
	}
	@Test
	void test2() {
			AdbUtil.launchApp(Param.adbPath+" shell am start -n org.wuji/org.wuji.InstallActivity");
	}
	@Test
	void test3() {
			AdbUtil.launchApp(Param.noxPath+" -root -clone:Nox_0");
	}

}
