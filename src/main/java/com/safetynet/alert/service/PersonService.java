package com.safetynet.alert.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alert.model.Person;
import com.safetynet.alert.repository.PersonRepository;

import lombok.Data;

@Data
@Service
public class PersonService {
	
	@Autowired
	private PersonRepository personRepository;
	
	//create or update
	public Person savePerson(Person person) {
		Person savedPerson = personRepository.save(person);
		return savedPerson;
	}
	
	//delete
	public void deletePerson(Person person) {
		personRepository.delete(person);
	}

}
