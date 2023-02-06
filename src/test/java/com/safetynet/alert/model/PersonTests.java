package com.safetynet.alert.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class PersonTests {
	
	@Test
	public void testPersons() {
		
		Person adult = new Person();
		Person child = new Person(0L,"Jo","White","Rue","ville",332211,"065288","mail@post.xyz");
		
		adult.setId(0L);
		adult.setFirstName("Jack");
		adult.setLastName("Black");
		adult.setAddress("Blv Av");
		adult.setCity("Moscou");
		adult.setZip(112233);
		adult.setPhone("052156");
		adult.setEmail("mail@box.xyz");
		
		assertFalse(adult.equals(child));
		assertFalse(adult.toString().equals(child.toString()));
		assertThat(adult.hashCode() != (child.hashCode()));
		
		


		
		
	}

}
