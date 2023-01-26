package com.safetynet.alert.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.safetynet.alert.model.Person;

//@ExtendWith(SpringExtension.class)
@DataJpaTest
public class PersonRepositoryTests {
	
	@Autowired
	private PersonRepository personRepository;
	
	private Person person;
	
	@Test
	public void testSavePerson() {
		long id=0L;
		Person person = new Person(id,"Jack","Black","Blv Av","Moscou",112233,"052156","mail@box.xyz");
		
		personRepository.save(person);
		Person fetchedPerson = personRepository.findPersonByFirstNameAndLastName("Jack", "Black");
		
		assertTrue(fetchedPerson != null);
		
	}
}
