package com.ms.util;

import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;


class SimulatorUtilTest {

	@Test
	void test() {
		List<String> list = XmlUtil
				.getAllMachineIdList("C:\\Users\\zongx\\AppData\\Local\\MultiPlayerManager\\multiplayer.xml");
		SimulatorUtil.startAllSimulator("D:\\soft\\yeshen\\Nox\\bin\\Nox.exe -clone:", list);
		try {
			Thread.sleep(100000000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���Թر����е�ģ����
	 */
	@Test
	void test2() {
		// ���������еĳ���
		List list = XmlUtil
				.getAllMachineIdList("C:\\Users\\zongx\\AppData\\Local\\MultiPlayerManager\\multiplayer.xml");

		SimulatorUtil.startAllSimulator("D:\\soft\\yeshen\\Nox\\bin\\Nox.exe -clone:", list);
		// �ȴ�ָ����ʱ��֮���ٹر����еĳ���
		// ������еĳ�����û����������
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SimulatorUtil.closeAllSimulator("D:\\soft\\yeshen\\Nox\\bin\\Nox.exe -clone:", list);
	}

	@Test
	void test3() {
		// System.out.println(5%2);
		int i = 6;
		int n = 2;
		// int b = i-n;
		int m = 0;
		while (true) {
			m += 1;
			System.out.println(m);
			if ((i - n * m) <= 0) {
				break;
			}
		}

		// 5 2
		/**
		 * 5 3 1 ȫ��
		 */
	}

	@Test
	void test4() {
		int i = 2;
		int n = 2;
		int m = 0;
		do {
			m += 1;
			System.out.println(m);
		} while ((i - n * m) > 0);
	}
	@Test
	void test5() {
		int i = 2;
		int n = 2;
		int m = 0;
		do {
			m += 1;
			System.out.println(m);
		} while ((i - n * m) > 0);
	}
	@Test
	void test6() {
		Assert.assertEquals(SimulatorUtil.checkSimulatorIsStartSuccess(62001),true);
	}
	@Test
	void test7() {
		Assert.assertEquals(SimulatorUtil.checkSimulatorIsStartSuccess(62003,10),true);
	}

}
