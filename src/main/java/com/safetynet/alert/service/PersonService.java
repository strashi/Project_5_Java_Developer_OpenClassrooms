package com.safetynet.alert.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alert.model.Person;
import com.safetynet.alert.repository.PersonRepository;

import jakarta.transaction.Transactional;
import lombok.Data;

@Data
@Service
public class PersonService {
	
	@Autowired
	private PersonRepository personRepository;
	
	//create or update
	public Person add(Person person) {
		return personRepository.save(person);
		
	}
	
	//delete
	public void deletePersonById(Long id) {
		personRepository.deleteById(id);
	}
	@Transactional
	public void deletePersonByFirstNameAndLastName(String firstName,String lastName) {
		personRepository.deletePersonByFirstNameAndLastName(firstName,lastName);
	}
	public Person findPersonByFirstNameAndLastName(String firstName, String lastName) {
		return personRepository.findPersonByFirstNameAndLastName( firstName,lastName);
	}
	
	public Iterable<Person> saveListPerson(Iterable<Person> list){
		return personRepository.saveAll(list);
	}

}
