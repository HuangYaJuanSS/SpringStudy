package cn.dao.impl;

import cn.dao.PersonDao;

public class PersonDaoBean implements PersonDao {
@Override
public void add() {
	System.out.println("执行了PersonDaoBean的add方法 ");
}
}
