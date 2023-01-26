package com.safetynet.alert.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.safetynet.alert.model.Person;
import com.safetynet.alert.repository.PersonRepository;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTests {
	
	@Mock
	private PersonRepository personRepository;
	
	@Autowired
	private PersonService personService;
	
	@Test
	public void testAddPerson() {
		long id=0L;
		Person person = new Person(id,"Jack","Black","Blv Av","Moscou",112233,"052156","mail@box.xyz");
		when(personRepository.save(person)).thenReturn(person);
		
		Person savedPerson = personService.addPerson(person);
		
		assertThat(savedPerson.equals(person));
		
	}

}
