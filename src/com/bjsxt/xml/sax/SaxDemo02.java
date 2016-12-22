package com.bjsxt.xml.sax;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

/**
 * 解析二种方式 dom sax 
 * sax 步骤
 * 1、获得解析工厂  SAXParserFactory
 * 2、工厂获取解析器  SAXParser
 * 3、加载文档 Document 注册处理器
 * @author Administrator
 *
 */
public class SaxDemo02 {

	/**
	 * @param args
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		//1、获得解析工厂  SAXParserFactory
		SAXParserFactory factory = SAXParserFactory.newInstance();
		//2、获取默认解析器
		SAXParser parse =factory.newSAXParser();
		//3、加载文档 Document 注册处理器
		PersonHandler2 handler= new PersonHandler2();
		parse.parse(Thread.currentThread().getContextClassLoader().
				 getResourceAsStream("person.xml"),handler);		 
		List<Person> list =handler.getList();
		for(Person per:list){
			System.out.println(per);
		}
	}

}
