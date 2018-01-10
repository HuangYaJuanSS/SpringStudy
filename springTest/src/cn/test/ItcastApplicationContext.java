package cn.test;


import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.crypto.dsig.SignedInfo;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;

public class ItcastApplicationContext {
	private List<Bean> beans=new ArrayList<Bean>();
	private Map<String,Object> sigletons=new HashMap<String,Object>();
public ItcastApplicationContext(String fileName) {
		this.readXML(fileName);
		this.instanceOfBean();
		this.injectObject();
		
	}
//将实例注入
private void injectObject() {
	for(Bean bean:beans) {
		Object ob=sigletons.get(bean.getId());
		if(ob!=null) {
			try {
				//多个属性
				PropertyDescriptor[] ps=Introspector.getBeanInfo(ob.getClass()).getPropertyDescriptors();
			   for(PropertyDefinition pdf:bean.getPropertys()) {
				   for(PropertyDescriptor pd:ps) {
					   //找到这个属性的方法
					   if(pdf.getName().equals(pd.getName())) {
						   Method setter=pd.getWriteMethod();
						   if(setter!=null) {
						   Object value=sigletons.get(pdf.getRef());
						   setter.setAccessible(true);//即使方法是private也是可以使用的
						   setter.invoke(ob, value);
						   }
						   break;
					   }
				   }
			   }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
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
			Bean b=new Bean(id,className);
			
			//加入property
			XPath propertySub=element.createXPath("ns:property");
			propertySub.setNamespaceURIs(nsMap);
			List<Element> propertys=propertySub.selectNodes(element);
			for(Element property:propertys){
				String propertyname=property.attributeValue("name");
				String propertyref=property.attributeValue("ref");
			    PropertyDefinition p=new PropertyDefinition(propertyname, propertyref);
			    b.getPropertys().add(p);
			}
			beans.add(b);
		}
		
	} catch (DocumentException e) {
		e.printStackTrace();
	}
}
private void instanceOfBean() {
	try {
	for(Bean b:beans) {
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
