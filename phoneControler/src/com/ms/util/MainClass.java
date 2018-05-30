package com.ms.util;

import java.util.List;

public class MainClass {

	public static void main(String[] args) {
		List<String> list = XmlUtil
				.getAllMachineIdList(Param.xmlFilePath);
		SimulatorUtil.startAllSimulator(Param.noxPath+" -clone:", list);
//		try {
//			Thread.sleep(100000000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}

}
