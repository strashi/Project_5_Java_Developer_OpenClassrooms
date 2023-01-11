package com.safetynet.alert.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.safetynet.alert.model.Person;


public interface PersonRepository extends JpaRepository<Person, Long>{
	
	public void deletePersonByLastName(String lastName);
	
	public Person findPersonByFirstNameAndLastName(String firstName, String lastName);
}
