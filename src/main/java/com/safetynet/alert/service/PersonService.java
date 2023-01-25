package com.safetynet.alert.service;

import com.safetynet.alert.dto.ResponseChildAlert;
import com.safetynet.alert.dto.ResponsePersonInfo;
import com.safetynet.alert.model.Person;

public interface PersonService {
	public Person addPerson(Person person);

	public Person updatePerson(Person oldPerson);
	
	public void deletePersonByFirstNameAndLastName(String firstName, String lastName);
	
	public Iterable<String> listOfEmailByCity(String city);
	
	public ResponseChildAlert childAlert(String address);
	
	public ResponsePersonInfo personInfo(String firstName, String lastName);
	
}
