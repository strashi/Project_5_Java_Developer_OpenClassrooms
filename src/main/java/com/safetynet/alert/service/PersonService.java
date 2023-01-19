package com.safetynet.alert.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import com.safetynet.alert.model.Person;

public interface PersonService {
	public Person addPerson(Person person);

	//public void deletePersonById(Long id);

	public void deletePersonByFirstNameAndLastName(String firstName, String lastName);

	//public Person findPersonByFirstNameAndLastName(String firstName, String lastName);

	//public List<Person> saveListPersons(List<Person> list);

	//public Optional<Person> getPerson(final Long id);

	public Person updatePerson(Person oldPerson);
	
	//public Iterable<String> listOfEmailByCity(String city);
	
	//public Iterable<Person> findAllPerson();
	
	public List<String> getPersonsFromAddressWithBirthdate(String address);
}
