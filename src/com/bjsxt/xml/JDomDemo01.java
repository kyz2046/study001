package com.bjsxt.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Comment;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * 使用jdom创建、读取
 * @author bjsxt-xueer
 *
 */
public class JDomDemo01 {
	public static void main(String[] args) throws Exception{
		create();		
		//updateTeacher("sxt001");
		//deleteTeacher("sxt002");
		parse();
	}
	public static void create() throws FileNotFoundException, IOException {
		// 文档
        Document document = new Document();
        // 生成一个元素
        Element root = new Element("teachers");
        // 将生成的元素加入文档：根元素
        document.addContent(root);
        // 给结点加上注释
        Comment comment = new Comment("使用jdom操作xml");
        root.addContent(comment);
        
        // 加上子元素1
        Element e = new Element("teacher");
        // 加属性
        e.setAttribute("no", "sxt001");
        root.addContent(e);
        //加子元素
        e.addContent(new Element("name").setAttribute("age", "20")
                .setAttribute("gender", "保密")
                .setText("xueer"));
        
        e = new Element("teacher");
        // 加属性
        e.setAttribute("no", "sxt002");
        root.addContent(e);
        e.addContent(new Element("name").setText("cola"));
        // 格式化
        Format format = Format.getPrettyFormat();
        // Format.getRawFormat()方法，通常用于XML数据的网络传输，因为这种格式会去掉所有不必要的空白，因此能够减少数据量
        // 可以自己设定一些format的属性
        format.setIndent("    ");// 把缩进设为四个空格（默认为两个空格）

        // 输出
        XMLOutputter out = new XMLOutputter(format);
        out.output(document, new FileOutputStream("src/jdom.xml"));// 可在当前项目路径下找到
              
	}
	public static void updateTeacher(String no) throws JDOMException, IOException {
		  SAXBuilder builder = new SAXBuilder();
		  InputStream file = new FileInputStream("src/jdom.xml");
		  Document doc = builder.build(file);//获得文档对象
		  Element root = doc.getRootElement();//获得根节点
		  List<Element> list = root.getChildren();
		  for(Element e:list) {
		   //获取no值
		   if(e.getAttributeValue("no").equals(no)) {
			   e.getChild("name").setText("pretty girl");
		   }
		  }
		  
		  //文件处理
		  XMLOutputter out = new XMLOutputter();
		  out.output(doc, new FileOutputStream("src/jdom.xml"));
	 }
	//根据no值删除一个节点
	 public static void deleteTeacher(String no) throws JDOMException, IOException {
		  SAXBuilder builder = new SAXBuilder();
		  InputStream file = new FileInputStream("src/jdom.xml");
		  Document doc = builder.build(file);//获得文档对象
		  Element root = doc.getRootElement();//获得根节点
		  List<Element> list = root.getChildren();
		  for(Element e:list) {
		   //获取no值
		   if(e.getAttributeValue("no").equals(no)) {
		    root.removeContent(e);
		    break;
		   }
		  }
		  
		  //文件处理
		  XMLOutputter out = new XMLOutputter();
		  out.output(doc, new FileOutputStream("src/jdom.xml"));
	 }
	public static void parse() throws JDOMException, IOException{
		// 构造器
        SAXBuilder saxBuilder = new SAXBuilder();
        // 获取文档
        Document document = saxBuilder.build(new File("src/jdom.xml"));

        // 得到根元素
        Element root = document.getRootElement();
        System.out.println("Root: " + root.getName());

        // 获取子元素
        Element teacher = root.getChild("teacher");
        System.out.println("child: " + teacher.getName());

        // 获取属性
        List<Attribute> list = teacher.getAttributes();

        for (int i = 0; i < list.size(); ++i){
            Attribute attr = (Attribute) list.get(i);
            String attrName = attr.getName();
            String attrValue = attr.getValue();

            System.out.println("teacher的属性： " + attrName + " = " + attrValue);
        }     

	}
}
