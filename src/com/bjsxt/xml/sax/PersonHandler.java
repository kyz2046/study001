package com.bjsxt.xml.sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PersonHandler extends DefaultHandler{
	private String parseTag;
	@Override
	public void startDocument() throws SAXException {
		System.out.println("进入文档");
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		parseTag = qName; //记录当前标记
		System.out.println("解析"+qName+"开始");
	
	}
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if(null!=parseTag){
			System.out.println(new String(ch,start,length));			
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		
		System.out.println("解析"+qName+"结束");
		parseTag = null;
		
		
		
	}
	
	
	
	
	public void endDocument() throws SAXException {
		System.out.println("文档解析完成");
	}
}
