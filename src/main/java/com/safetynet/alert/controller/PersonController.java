package com.safetynet.alert.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alert.dto.ResponseChildAlert;
import com.safetynet.alert.dto.ResponsePersonInfo;
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
	
	@PutMapping("/person")
	public Person updatePerson(@RequestBody Person person) {
		return personService.updatePerson(person);
	}
		
	@DeleteMapping("/person")
	@ResponseBody
	public void deletePersonByFirstNameAndLastName(@RequestParam String firstName, @RequestParam String lastName) { 
	personService.deletePersonByFirstNameAndLastName(firstName, lastName);
	} 
		
	@GetMapping("/communityEmail")
	public Iterable<String> listOfEmailByCity(String city) {
		return personService.listOfEmailByCity(city);
	}
		
	@GetMapping("/childAlert")
	public ResponseChildAlert childAlert(String address){
		return  personService.childAlert(address);
	}
	
	@GetMapping("/personInfo")
	public ResponsePersonInfo personInfo(@RequestParam String firstName, @RequestParam String lastName){
		return  personService.personInfo(firstName, lastName);
	}
	/*
	@GetMapping("/childAlert")
	public List<String> childAlert(String address){
		re*/
	
	/*
	@DeleteMapping("/person/{id}")
	public void deletePersonById(@PathVariable("id") Long id) {
		personService.deletePersonById(id);
	}*/
	/*
	@GetMapping("/persons")
	public Iterable<Person> findAllPerson(){
		return  personService.findAllPerson();
	}*/
	/*
	@GetMapping("/person")
	@ResponseBody
	public Person findPersonByFirstNameAndLastName(@RequestParam String firstName,@RequestParam String lastName) { 
	return personService.findPersonByFirstNameAndLastName(firstName, lastName);
	} */
	/*
	@GetMapping("/person/{id}")
	public Person getPerson(@PathVariable("id") final Long id) {
		Optional<Person> person = personService.getPerson(id);
		if(person.isPresent()) {
			return person.get();
		}else {
			return null;
			}
		
	}*/
}
