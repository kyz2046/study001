package com.bjsxt.xml.sax;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Administrator
 *
 */
public class PersonHandler2 extends DefaultHandler{
	private String parseTag;
	private Person person;
	private List<Person> list;
	
	@Override
	public void startDocument() throws SAXException {
		//System.out.println("进入文档");
		list = new ArrayList<Person>();
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		parseTag = qName; //记录当前标记
		if(qName.equals("person")){
			person = new Person();
		}
		//System.out.println("解析"+qName+"开始");
	
	}
	
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if(null!=parseTag){
			String data = new String(ch,start,length);
			if(parseTag.equals("name")){
				person.setName(data);
			}else if(parseTag.equals("age")){
				person.setAge(Integer.parseInt(data));
			}
		}
	}
	
	
	
	
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		
		//System.out.println("解析"+qName+"结束");
		if(qName.equals("person")){
			list.add(person);
		}
		parseTag = null; //注意将parseTag置为null,否则会存在大量的空内容
		
		
		
	}
	
	
	
	
	public void endDocument() throws SAXException {
		//System.out.println("文档解析完成");
	}

	public List<Person> getList() {
		return list;
	}

	public void setList(List<Person> list) {
		this.list = list;
	}
	
}
