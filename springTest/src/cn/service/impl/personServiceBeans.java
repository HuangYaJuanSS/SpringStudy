package cn.service.impl;

import cn.dao.PersonDao;
import cn.service.personService;

public class personServiceBeans implements personService {
	private PersonDao personDao;

	public PersonDao getPersonDao() {
		return personDao;
	}

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}


    @Override
	public void save() {
	this.personDao.add();
}
}
