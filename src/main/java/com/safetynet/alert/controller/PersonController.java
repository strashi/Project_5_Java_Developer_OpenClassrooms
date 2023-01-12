package com.safetynet.alert.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
		return personService.add(person);
	}
	
	@DeleteMapping("/person/{id}")
	public void deletePersonById(@PathVariable("id") Long id) {
		personService.deletePersonById(id);
	}
	
	@DeleteMapping("/person")
	@ResponseBody
	public void deletePersonByLastName(@RequestParam String firstName, @RequestParam String lastName) { 
	personService.deletePersonByFirstNameAndLastName(firstName, lastName);
	} 
	
	@GetMapping("/person")
	@ResponseBody
	public Person findPersonByFirstNameAndLastName(@RequestParam String firstName,@RequestParam String lastName) { 
	return personService.findPersonByFirstNameAndLastName(firstName, lastName);
	} 
}
