package com.safetynet.alert.service;

import java.util.Date;

import com.safetynet.alert.model.Person;

public interface UtilService {
	
	int calculateAge(Date birthdate);
	int getAge(Person person);
}
