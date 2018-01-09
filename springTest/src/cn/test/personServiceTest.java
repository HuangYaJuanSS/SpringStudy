package cn.test;

import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.service.personService;

public class personServiceTest {

	@Test
	public void test() {
		//jar直接调用
		//AbstractApplicationContext ctx=new ClassPathXmlApplicationContext("beans.xml");
		ItcastApplicationContext ctx=new ItcastApplicationContext("beans.xml");
		personService personService=(personService)ctx.getBean("personService");
		personService.save();
	}

}
