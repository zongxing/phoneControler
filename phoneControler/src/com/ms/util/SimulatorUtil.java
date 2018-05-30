package com.ms.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SimulatorUtil {
	volatile static int currentStartAppSize = 0;
	volatile static int currentStartAppPort = 62001;
	static ThreadLocal<Integer> portTL = new ThreadLocal<Integer>();
	/**
	 * ����һ��ģ����
	 * 
	 * @param command
	 * @param id
	 */
	public static synchronized void startOneSimulator(String command, String id) {
		portTL.set(currentStartAppPort);
		currentStartAppSize +=1;
		if(currentStartAppPort==62001) {
			currentStartAppPort = 62025;
			
		}else {
			currentStartAppPort+=1;

		}
		
		AdbUtil.launch(command + id + " -resolution:"+Param.resolutionValue+" -performance:high -root:false");
		//AdbUtil.launch(command + id + " -resolution:480x800 -dpi:270 -performance:high -root:false");
	}

	/**
	 * ָ��
	 * 
	 * @param command
	 * @param idList
	 */
	public static void startAllSimulator(String command, List<String> idList) {
		// idList.subList(0, Param.batchStartSize);
		/**
		 * 1��ʣ��Ĵ�С���ڵ���batchStartSizeʱ 2��С��batchStartSizeʱ
		 */
		int totalSize = idList.size();
		if (totalSize % Param.batchStartSize == 0) {

		}
		int i = 0;//
		do {
			// ������������ʹ������ִ��һ��������
			i += 1;
			/**
			 * 1�����һ��ȡ�������������ʱ
			 */
			List<String> onceList = new ArrayList<>();
			// ʣ�����
			int n = totalSize - i * Param.batchStartSize;
			// ʣ�����==0
			// ʣ�����С�ڵ���һ�οɶ�ȡ������ȡ

			// if (n == 0) {
			// // onceList = idList.subList((i - 1) * Param.batchStartSize,
			// // Param.batchStartSize-1);
			// } else
			if (n >= 0) {
				onceList = idList.subList((i - 1) * Param.batchStartSize, Param.batchStartSize * i);
			} else if (n < 0) {
				onceList = idList.subList((i - 1) * Param.batchStartSize, totalSize);
			}
			System.out.println("��" + i + "��������" + (onceList.size()) + "��");
			startOnceBatchSimulator(command, onceList);
			
			try {
				Thread.sleep(Param.sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			while(true) {
				try {
					Thread.sleep(1000);
					System.out.println("����");
					if(currentStartAppSize==0) {
						break;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			//closeOnceSimulator("D:\\soft\\yeshen\\Nox\\bin\\Nox.exe -clone:", onceList);
		} while ((totalSize - Param.batchStartSize * i) > 0);
	}

	/**
	 * �������е�ģ����
	 * 
	 * @param command
	 * @param idList
	 */
	public static void startOnceBatchSimulator(String command, List<String> idList) {
		System.out.println("������������");
		for (Iterator iterator = idList.iterator(); iterator.hasNext();) {
			String id = (String) iterator.next();
			System.out.println(id + ":������");
			// startOneSimulator(command, id);
			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {
					//����һ��ģ����
					startOneSimulator(command, id);
					//����Ƿ������ɹ���
					if(checkSimulatorIsStartSuccess(portTL.get(), Param.checkSimulatorIsStartNum)) {
						startApp(portTL.get());
						//��app�Զ�����
						OperationUtil.fun(String.valueOf(portTL.get()));
						//������ɣ��ر�app
						System.out.println("�رշ���,"+Param.noxPath+" -clone:"+id+ " -quit");
						closeOneSimulator(Param.noxPath+" -clone:", id);
					}
					//��������ɹ���������app
//					currentStartAppSize +=1;
//					if(currentStartAppPort==62001) {
//						currentStartAppPort = 62025;
//					}else {
//						currentStartAppPort+=1;
//					}
				}
			});
			t.start();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		System.out.println("�뿪��������");
	}

	public static void closeOneSimulator(String command, String id) {
		// D:\soft\yeshen\Nox\bin\Nox.exe -clone:Nox_1 -quit
		System.out.println("�رշ���");
		if(currentStartAppSize>0) {
			currentStartAppSize-=1;
		}
		AdbUtil.close(command + id + " -quit");
	}

	/**
	 * �ر����е�ģ����
	 * 
	 * @param command
	 * @param idList
	 */
	public static void closeAllSimulator(String command, List<String> idList) {
		System.out.println("����رշ���");
		for (Iterator iterator = idList.iterator(); iterator.hasNext();) {
			String id = (String) iterator.next();
			System.out.println(id + ":�ر���");
			// startOneSimulator(command, id);
			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {
					closeOneSimulator(command, id);
				}
			});
			t.start();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		System.out.println("�뿪�رշ���");
	}

	public static void closeOnceSimulator(String command, List<String> idList) {
		System.out.println("����رշ���");
		for (Iterator iterator = idList.iterator(); iterator.hasNext();) {
			String id = (String) iterator.next();
			System.out.println(id + ":�ر���");
			// startOneSimulator(command, id);
			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {
					closeOneSimulator(command, id);
				}
			});
			t.start();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		System.out.println("�뿪�رշ���");
	}
	/**
	 * ���ĳ��ģ�����Ƿ������ɹ�
	 * @param port
	 * @return true:�����ɹ���false:δ�����ɹ�
	 */
	public static boolean checkSimulatorIsStartSuccess(int port) {
		boolean sign = false;
		String content = AdbUtil.doCommand(Param.adbPath+" devices");
		if(content.contains(String.valueOf(port))) {
			sign = true;
		}
		return sign;
	}
	/**
	 * ���ĳ��ģ�����Ƿ������ɹ�,30���ڣ���ͣ�ļ�⣬����������ͷ��ز��˳�����
	 * @param port
	 * @num ����
	 * @return true:�����ɹ���false:δ�����ɹ�
	 */
	public static boolean checkSimulatorIsStartSuccess(int port,int num) {
		String threadName = Thread.currentThread().getName();
		System.out.println("�߳�"+threadName+"����app");
		boolean sign = false;
		for (int i = 0; i < num; i++) {
			
			try {
				Thread.sleep(1000);
				System.out.println("�߳�:"+threadName+","+port+",��"+i+"��");
				if(checkSimulatorIsStartSuccess(port)) {
					System.out.println("�߳�:"+threadName+","+port+",��"+i+"��,�ѳɹ�");
					return true;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		return sign;
	}
	
	public static void startApp(int port) {
		//adb -s 127.0.0.1:62001 shell am start -n  cn.weli.story/cn.etouch.ecalendar.LoadingActivity
		String command = Param.adbPath+" -s 127.0.0.1:"+String.valueOf(port)+" shell am start -n  "+Param.startAppName;
		AdbUtil.launchApp(command);
	}
	
//	public static void closeOne(String command, String id) {
//		//adb -s 127.0.0.1:62001 shell am start -n  cn.weli.story/cn.etouch.ecalendar.LoadingActivity
//		AdbUtil.close(command + id + " -quit");
//	}
	
}
