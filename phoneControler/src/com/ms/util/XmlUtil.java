package com.ms.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlUtil {
	/**
	 * 读取所有的模拟器的id,以方便下一步模拟器
	 * 
	 * @param filePath
	 * @return
	 */
	public static List<String> getAllMachineIdList(String filePath) {
		List<String> list = new ArrayList<>();
		InputStream in = null;
		SAXReader reader = null;
		try {
			reader = new SAXReader();
			in = new FileInputStream(new File(filePath));
			Document doc = reader.read(in);
			Element root = doc.getRootElement();
			List<Element> elementList = root.elements("Instance");
			for (Iterator iterator = elementList.iterator(); iterator.hasNext();) {
				Element element = (Element) iterator.next();
				String value = element.attributeValue("id");
				list.add(value);
			}
		} catch (Exception e) {
			return null;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	public static void generateXml() {
		Properties p = new Properties();
		try {
			p.setProperty("test", "test");
			p.storeToXML(new FileOutputStream("src/a.xml"), "配置文件");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getProperty(String key) {

		return getProperty(key, "");
	}

	public static String getProperty(String key, String defaultValue) {
		String value = "";
		InputStream is = Param.class.getClassLoader().getResourceAsStream("config.xml");
		Properties p = new Properties();
		try {
			p.loadFromXML(is);
			value = p.getProperty(key, defaultValue);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}

	public static void main(String[] args) {
		generateXml();
	}
}
