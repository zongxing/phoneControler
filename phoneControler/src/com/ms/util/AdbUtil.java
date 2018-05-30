package com.ms.util;

import java.io.IOException;
import java.util.Scanner;

public class AdbUtil {
	public static void watchRedio(String commandStr) {
		for (int i = 0; i < 10; i++) {
			
		}
		doCommand(commandStr);
	}
	public static void launchApp(String commandStr) {
		doCommand(commandStr);
	}
	public static void slide(String commandStr) {
		doCommand(commandStr);
	}
	public static void close(String commandStr) {
		doCommand(commandStr);
	}
	public static void launch(String commandStr) {
		System.out.println("begin");
		try {
			Process p = Runtime.getRuntime().exec(commandStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("::::::::::::::::::::command:"+commandStr+":::::::::::::::::::::>>>>>>");
	}
	public static void click(String commandStr) {
		doCommand(commandStr);
	}
	public static String doCommand(String commandStr) {
		String returnMsg="";
		try {
			System.out.println("begin");
			Process p = Runtime.getRuntime().exec(commandStr);
			System.out.println("::::::::::::::::::::command:"+commandStr+":::::::::::::::::::::>>>>>>");
			p.waitFor();
			Scanner s = new Scanner(p.getInputStream());
			while (s.hasNextLine()) {
				returnMsg += s.nextLine();

			}
			s.close();

			Scanner s2 = new Scanner(p.getErrorStream());
			String str2 = "";
			while (s2.hasNextLine()) {
				str2 += s.nextLine();

			}
			s2.close();
		}catch(Exception e) {
				e.printStackTrace();
			}
		return returnMsg;
	}
}
