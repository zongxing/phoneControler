package com.ms.util;

import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;


class XmlUtilTest {

	@Test
	void test() {
		List list = XmlUtil.getAllMachineIdList("C:\\Users\\zongx\\AppData\\Local\\MultiPlayerManager\\multiplayer.xml");
		list.forEach(System.out::println);
		Assert.assertEquals(list.size(), 5);
	}

}
