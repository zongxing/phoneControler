package com.ms.util;

import java.io.IOException;

public class OperationUtil {

	static String libPath = Param.adbPath + " -s ";

	// �����¼�
	public static void backtrackEvent(String portId) {
		String pPort = "127.0.0.1:" + portId + " ";
		String command = "shell input keyevent 4";
		String exce1 = libPath + pPort + command;
		try {
			Process p2 = Runtime.getRuntime().exec(exce1);
			System.out.println("����:" + p2.hashCode());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ����¼�
	public static void clickEvent(String portId) {

		String pPort = "127.0.0.1:" + portId + " ";
//		String command = "shell input tap 50 240";
		//240*400
				String command = "shell input tap 50 150";
		String exce1 = libPath + pPort + command;
		try {
			Process p2 = Runtime.getRuntime().exec(exce1);
			System.out.println("��������¼�:" + p2.hashCode());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ��ҳ�����¼�
	public static void swipeMainPageEvent(String portId) {

		String pPort = "127.0.0.1:" + portId + " ";
		// �������Ϊ349
//		String command = "shell input swipe 11 509 11 240";
		//240*400
				String command = "shell input swipe 11 273 11 150";
		String exce1 = libPath + pPort + command;
		try {
			Process p2 = Runtime.getRuntime().exec(exce1);
			System.out.println("��ҳ����:" + p2.hashCode());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ����ҳ�����¼�
	public static void swipeContentPageEvent(String portId) {

		String pPort = "127.0.0.1:" + portId + " ";
		// �������Ϊ349
//		String command = "shell input swipe 0 509 0 240";
		//240*400
				String command = "shell input swipe 0 300 0 150";
		String exce1 = libPath + pPort + command;
		try {
			Process p2 = Runtime.getRuntime().exec(exce1);
			System.out.println("����ҳ�滬��:" + p2.hashCode());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void fun(String port) {
		// ����ҳ������ҳ
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		backtrackEvent(port);
		// �����¼�
		for (int i = 0; i < 10; i++) {
			// �Ȼ������ٵ��
			// �����ȥ�󣬻���
			try {
				// ��ҳ"�Ƽ�"�˵��ڻ���
				swipeMainPageEvent(port);
				Thread.sleep(1000);
				// �Ƽ�ҳ���
				clickEvent(port);
				// �²����ݼ���ʱ��
				Thread.sleep(3000);
				for (int j = 0; j < 10; j++) {
					// ����ҳ����
					swipeContentPageEvent(port);
					Thread.sleep(2000);
				}
				Thread.sleep(1000);
				// ����ҳ������ҳ
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
