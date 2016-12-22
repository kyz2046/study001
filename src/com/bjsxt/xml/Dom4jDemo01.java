package com.bjsxt.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.DOMReader;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xml.sax.SAXException;
/**
 * dom4j支持xpath
 * @author bjsxt-xueer
 *
 */
public class Dom4jDemo01 {
	public static void main(String[] args) throws Exception {
		create();
		updateDept();
		deleteDept();
		parse();
	}
	public static void deleteDept() throws Exception{
		SAXReader saxReader = new SAXReader();
		saxReader.setEncoding("UTF-8");  
        Document doc = saxReader.read(new File("src/dept.xml"));
        //可以使用xpath查找 depts/dept 没有no属性的元素
        List<Element> eleList = doc.selectNodes("depts/dept");  
        for(Element e:eleList){
        	Attribute attr =e.attribute("no"); //没有no属性
        	if(null==attr){
        		e.getParent().remove(e);
        	}
        }
        OutputFormat format = OutputFormat.createPrettyPrint();  
        // 利用格式化类对编码进行设置  
        format.setEncoding("UTF-8");  
        FileOutputStream output = new FileOutputStream(new File("src/dept.xml"));  
        XMLWriter writer = new XMLWriter(output, format);  
        writer.write(doc);  
        writer.flush();  
        writer.close();  
	}
	/** 
	 * 读取并解析xml ,使用xpath 需要加入 jaxen-1.1-beta-6.jar
	 * @throws IOException
	 * @throws DocumentException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public static void updateDept() throws Exception{
		SAXReader saxReader = new SAXReader();
		saxReader.setEncoding("UTF-8");  
        Document doc = saxReader.read(new File("src/dept.xml"));
        //可以使用xpath查找 depts/dept 属性为no
        List<Attribute> attrList = doc.selectNodes("depts/dept/@no");  
        Iterator<Attribute> iter = attrList.iterator();
        while(iter.hasNext()){
        	Attribute attribute = iter.next();  
            if (attribute.getValue().equalsIgnoreCase("sxt001")){  
                attribute.setValue("sxt");  
            }  
        }
        
        OutputFormat format = OutputFormat.createPrettyPrint();  
        // 利用格式化类对编码进行设置  
        format.setEncoding("UTF-8");  
        FileOutputStream output = new FileOutputStream(new File("src/dept.xml"));  
        XMLWriter writer = new XMLWriter(output, format);  
        writer.write(doc);  
        writer.flush();  
        writer.close();  
	}
	
	/**
	 * 读取并解析xml
	 * @throws IOException
	 * @throws DocumentException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public static void parse() throws IOException, DocumentException, SAXException, ParserConfigurationException{
		System.out.println("用SAXReader-----------------------");		
		SAXReader saxReader = new SAXReader();
		saxReader.setEncoding("UTF-8");  
        Document document = saxReader.read(new File("src/dept.xml"));
        // 获取根元素
        Element root = document.getRootElement();
        System.out.println("Root: " + root.getName());

        // 获取所有子元素
        List<Element> childList = root.elements();
        System.out.println("total child count: " + childList.size());

        // 获取特定名称的子元素
        List<Element> childList2 = root.elements("dept");
        System.out.println("dept child: " + childList2.size());

        // 获取名字为指定名称的第一个子元素
        Element firstElement = root.element("dept");
        // 输出其属性
        System.out.println("first Attr: "
                + firstElement.attribute(0).getName() + "="
                + firstElement.attributeValue("no"));

        System.out.println("迭代输出-----------------------");
        // 迭代输出
        Iterator<Element> iter = root.elementIterator();
        while (iter.hasNext()){
            Element e = iter.next();
            System.out.println(e.attributeValue("no"));

        }

        System.out.println("用DOMReader-----------------------");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        // 注意要用完整类名
        org.w3c.dom.Document document2 = db.parse(new File("src/dept.xml"));
        DOMReader domReader = new DOMReader();
        // 将JAXP的Document转换为dom4j的Document
        Document document3 = domReader.read(document2);
        Element rootElement = document3.getRootElement();
        System.out.println("Root: " + rootElement.getName());        
        //剩下的操作与上述一致

	}
	public static void create() throws IOException{
		// 第一种方式：创建文档，并创建根元素
		/*
        // 创建文档:使用了一个Helper类
        Document document = DocumentHelper.createDocument();
        // 创建根节点并添加进文档
        Element root = DocumentHelper.createElement("depts");
        do8cument.setRootElement(root);
        */
        // 第二种方式:创建文档并设置文档的根元素节点
        Element root = DocumentHelper.createElement("depts");
        Document document = DocumentHelper.createDocument(root);
        
       // 根节点添加属性
        root.addAttribute("name", "sxt");
       // 添加子节点:add之后就返回这个元素
       Element e= root.addElement("dept");
       e.addElement("name").addText("研发部");
       e.addAttribute("no", "sxt001");
       
       root.addElement("dept").addElement("name").addText("营销部");
       
       
       // 输出到控制台
       XMLWriter xmlWriter = new XMLWriter();
       xmlWriter.write(document);
       
        //输出
       OutputFormat format = new OutputFormat("    ", true);// 设置缩进为4个空格，并且另起一行为true
       format.setEncoding("utf-8");
       xmlWriter = new XMLWriter(
               new FileOutputStream("src/dept.xml"), format);
       xmlWriter.write(document);
       xmlWriter.flush();
       xmlWriter.close();
	}
}
