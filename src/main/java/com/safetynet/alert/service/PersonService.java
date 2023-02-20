package com.safetynet.alert.service;

import java.util.List;

import com.safetynet.alert.dto.ResponseChildAlert;
import com.safetynet.alert.dto.ResponsePersonInfo;
import com.safetynet.alert.model.Person;

public interface PersonService {
	
	public Person addPerson(Person person);
	public List<Person> updatePerson(Person oldPerson);
	public void deletePersonByFirstNameAndLastName(String firstName, String lastName);
	public List<String> listOfEmailByCity(String city);
	public ResponseChildAlert childAlert(String address);
	public ResponsePersonInfo personInfo(String firstName, String lastName);
	
}
