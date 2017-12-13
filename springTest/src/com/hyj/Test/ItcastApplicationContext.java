package com.hyj.Test;


import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;

public class ItcastApplicationContext {
	private List<Beans> beans=new ArrayList<Beans>();
	private Map<String,Object> sigletons=new HashMap<String,Object>();
public ItcastApplicationContext(String fileName) {
		this.readXML(fileName);
		this.instanceOfBean();
		
	}
private void readXML(String fileName) {
	SAXReader saxReader=new SAXReader();
	org.dom4j.Document doc=null;
	URL xmlPath=this.getClass().getClassLoader().getResource(fileName);
	try {
		doc=saxReader.read(xmlPath);
		Map<String,String> nsMap=new HashMap<String,String>();
		nsMap.put("ns", "http://www.springframework.org/schema/beans");
		XPath xsub=doc.createXPath("//ns:beans/ns:bean");
		xsub.setNamespaceURIs(nsMap);
		List<Element> elements=xsub.selectNodes(doc);
		for(Element element:elements) {
			String id=element.attributeValue("id");
			String className=element.attributeValue("class");
			Beans b=new Beans(id,className);
			beans.add(b);
		}
		
	} catch (DocumentException e) {
		e.printStackTrace();
	}
}
private void instanceOfBean() {
	try {
	for(Beans b:beans) {
			if(b.getClassName()!=null && !("".equals(b.getClassName().trim())))
			sigletons.put(b.getId(),Class.forName(b.getClassName()).newInstance());
	 }	
	    } catch (Exception e) { 
			e.printStackTrace();
		}
}

public Object getBean(String name) {
	return this.sigletons.get(name);
}
}
