package com.hyj.Test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hyj.personService;

public class personServiceTest {

	@Test
	public void test() {
		ItcastApplicationContext ctx=new ItcastApplicationContext("beans.xml");
		personService person=(personService)ctx.getBean("personService");
		person.save();
	}

}
