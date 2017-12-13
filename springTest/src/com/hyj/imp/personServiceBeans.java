package com.hyj.imp;

import com.hyj.personService;

public class personServiceBeans implements personService {

@Override
public void save() {
	System.out.print("i'm save");
}
}
