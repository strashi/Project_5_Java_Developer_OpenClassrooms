package com.safetynet.alert.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alert.model.Person;
import com.safetynet.alert.repository.PersonRepository;

import lombok.Data;

@Data
@Service
@DynamicUpdate
public class PersonServiceImpl {

	@Autowired
	private PersonRepository personRepository;

	public Person addPerson(Person person) {
		return personRepository.save(person);
	}

	public void deletePersonById(Long id) {
		personRepository.deleteById(id);
	}

	@Transactional
	public void deletePersonByFirstNameAndLastName(String firstName, String lastName) {
		personRepository.deletePersonByFirstNameAndLastName(firstName, lastName);
	}

	public Person findPersonByFirstNameAndLastName(String firstName, String lastName) {
		return personRepository.findPersonByFirstNameAndLastName(firstName, lastName);
	}

	public List<Person> saveListPersons(List<Person> list) {
		return personRepository.saveAll(list);
	}

	public Optional<Person> getPerson(final Long id) {
		return personRepository.findById(id);
	}

	public Person updatePerson(Person oldPerson) {
		Person newPerson = this.findPersonByFirstNameAndLastName(oldPerson.getFirstName(), oldPerson.getLastName());
		newPerson.setAddress(oldPerson.getAddress());
		newPerson.setCity(oldPerson.getCity());
		newPerson.setZip(oldPerson.getZip());
		newPerson.setPhone(oldPerson.getPhone());
		newPerson.setEmail(oldPerson.getEmail());

		return personRepository.save(newPerson);
	}
	
	public Iterable<String> listOfEmailByCity(String city) {
		return personRepository.listOfEmailByCity(city);
	
		
		
	}
	
	public Iterable<Person> findAllPerson(){
		return personRepository.findAll();
	}

}
