package com.safetynet.alert.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alert.model.Person;
import com.safetynet.alert.service.PersonService;


@RestController
public class PersonController {
	
	@Autowired
	private PersonService personService;
	
	@PostMapping("/person")
	public Person addPerson(@RequestBody Person person) {
		return personService.addPerson(person);
	}
	
	@DeleteMapping("/person/{id}")
	public void deletePersonById(@PathVariable("id") Long id) {
		personService.deletePersonById(id);
	}
	
	@DeleteMapping("/person")
	@ResponseBody
	public void deletePersonByFirstNameLastName(@RequestParam String firstName, @RequestParam String lastName) { 
	personService.deletePersonByFirstNameAndLastName(firstName, lastName);
	} 
	
	@GetMapping("/person")
	@ResponseBody
	public Person findPersonByFirstNameAndLastName(@RequestParam String firstName,@RequestParam String lastName) { 
	return personService.findPersonByFirstNameAndLastName(firstName, lastName);
	} 
	
	@GetMapping("/person/{id}")
	public Person getPerson(@PathVariable("id") final Long id) {
		Optional<Person> person = personService.getPerson(id);
		if(person.isPresent()) {
			return person.get();
		}else {
			return null;
			}
		
	}
	
	@PutMapping("/person")
	public Person updatePerson(@RequestBody Person person) {
		return personService.updatePerson(person);
	}
}
