package com.ms.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SimulatorUtil {
	volatile static int currentStartAppSize = 0;
	volatile static int currentStartAppPort = 62001;
	static ThreadLocal<Integer> portTL = new ThreadLocal<Integer>();
	/**
	 * 启动一个模拟器
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
	 * 指定
	 * 
	 * @param command
	 * @param idList
	 */
	public static void startAllSimulator(String command, List<String> idList) {
		// idList.subList(0, Param.batchStartSize);
		/**
		 * 1：剩余的大小大于等于batchStartSize时 2：小于batchStartSize时
		 */
		int totalSize = idList.size();
		if (totalSize % Param.batchStartSize == 0) {

		}
		int i = 0;//
		do {
			// 满足此条件，就代表可以执行一次批处理
			i += 1;
			/**
			 * 1：最后一次取，如果不够整数时
			 */
			List<String> onceList = new ArrayList<>();
			// 剩余的数
			int n = totalSize - i * Param.batchStartSize;
			// 剩余的数==0
			// 剩余的数小于等于一次可读取的数，取

			// if (n == 0) {
			// // onceList = idList.subList((i - 1) * Param.batchStartSize,
			// // Param.batchStartSize-1);
			// } else
			if (n >= 0) {
				onceList = idList.subList((i - 1) * Param.batchStartSize, Param.batchStartSize * i);
			} else if (n < 0) {
				onceList = idList.subList((i - 1) * Param.batchStartSize, totalSize);
			}
			System.out.println("第" + i + "次启动：" + (onceList.size()) + "个");
			startOnceBatchSimulator(command, onceList);
			
			try {
				Thread.sleep(Param.sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			while(true) {
				try {
					Thread.sleep(1000);
					System.out.println("休眠");
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
	 * 启动所有的模拟器
	 * 
	 * @param command
	 * @param idList
	 */
	public static void startOnceBatchSimulator(String command, List<String> idList) {
		System.out.println("进入启动方法");
		for (Iterator iterator = idList.iterator(); iterator.hasNext();) {
			String id = (String) iterator.next();
			System.out.println(id + ":启动中");
			// startOneSimulator(command, id);
			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {
					//启动一个模拟器
					startOneSimulator(command, id);
					//监测是否启动成功，
					if(checkSimulatorIsStartSuccess(portTL.get(), Param.checkSimulatorIsStartNum)) {
						startApp(portTL.get());
						//让app自动运行
						OperationUtil.fun(String.valueOf(portTL.get()));
						//运行完成，关闭app
						System.out.println("关闭方法,"+Param.noxPath+" -clone:"+id+ " -quit");
						closeOneSimulator(Param.noxPath+" -clone:", id);
					}
					//如果启动成功，则启动app
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
		System.out.println("离开启动方法");
	}

	public static void closeOneSimulator(String command, String id) {
		// D:\soft\yeshen\Nox\bin\Nox.exe -clone:Nox_1 -quit
		System.out.println("关闭方法");
		if(currentStartAppSize>0) {
			currentStartAppSize-=1;
		}
		AdbUtil.close(command + id + " -quit");
	}

	/**
	 * 关闭所有的模拟器
	 * 
	 * @param command
	 * @param idList
	 */
	public static void closeAllSimulator(String command, List<String> idList) {
		System.out.println("进入关闭方法");
		for (Iterator iterator = idList.iterator(); iterator.hasNext();) {
			String id = (String) iterator.next();
			System.out.println(id + ":关闭中");
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
		System.out.println("离开关闭方法");
	}

	public static void closeOnceSimulator(String command, List<String> idList) {
		System.out.println("进入关闭方法");
		for (Iterator iterator = idList.iterator(); iterator.hasNext();) {
			String id = (String) iterator.next();
			System.out.println(id + ":关闭中");
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
		System.out.println("离开关闭方法");
	}
	/**
	 * 检测某个模拟器是否启动成功
	 * @param port
	 * @return true:启动成功；false:未启动成功
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
	 * 检测某个模拟器是否启动成功,30秒内，不停的检测，如果检测出来就返回并退出程序
	 * @param port
	 * @num 次数
	 * @return true:启动成功；false:未启动成功
	 */
	public static boolean checkSimulatorIsStartSuccess(int port,int num) {
		String threadName = Thread.currentThread().getName();
		System.out.println("线程"+threadName+"启动app");
		boolean sign = false;
		for (int i = 0; i < num; i++) {
			
			try {
				Thread.sleep(1000);
				System.out.println("线程:"+threadName+","+port+",第"+i+"次");
				if(checkSimulatorIsStartSuccess(port)) {
					System.out.println("线程:"+threadName+","+port+",第"+i+"次,已成功");
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
