package cn.service.impl;

import cn.dao.PersonDao;
import cn.service.personService;

public class personServiceBeans implements personService {
	private PersonDao personDao;
    private String name;
    private int id;
    
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PersonDao getPersonDao() {
		return personDao;
	}

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}


    @Override
	public void save() {
	this.personDao.add();
	System.out.println(this.name+this.id);
}
}
