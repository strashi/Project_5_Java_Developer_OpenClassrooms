package com.safetynet.alert.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alert.dto.ResponseChildAlert;
import com.safetynet.alert.dto.ResponsePersonInfo;
import com.safetynet.alert.model.Person;
import com.safetynet.alert.service.PersonService;

@RestController
public class PersonController {
	
	private static final Logger logger =  LoggerFactory.getLogger(PersonController.class);

	@Autowired
	private PersonService personService;

	@PostMapping("/person")
	public Person addPerson(@RequestBody Person person) {
		logger.debug("requête ajouter une personne envoyée de PersonController");
		try {
			logger.info("requête ajouter une personne réussie chez PersonController!");
			return personService.addPerson(person);
		}catch(Exception e) {
			logger.error("marche pas :(",e);
			return null;
		}
		
	}

	@PutMapping("/person")
	public List<Person> updatePerson(@RequestBody Person person) {
		logger.debug("requête updatePerson envoyée de PersonController");
		try {
			logger.info("requête updatePerson réussie chez PersonController!");
			return personService.updatePerson(person);
		}catch(Exception e) {
			logger.error("marche pas :(",e);
			return null;
		}
	}

	@DeleteMapping("/person")
	public void deletePersonByFirstNameAndLastName(@RequestParam String firstName, @RequestParam String lastName) {
		logger.debug("requête deletePerson envoyée de PersonController");
		try {
			logger.info("requête deletePerson réussie chez PersonController!");
			personService.deletePersonByFirstNameAndLastName(firstName, lastName);
		}catch(Exception e) {
			logger.error("marche pas :(",e);
			
		}
	}

	@GetMapping("/communityEmail")
	public List<String> listOfEmailByCity(@RequestParam String city) {
		logger.debug("requête list of email envoyée de PersonController");
		try {
			logger.info("requête list of email réussie chez PersonController!");
			return personService.listOfEmailByCity(city);
		}catch(Exception e) {
			logger.error("marche pas :(",e);
			return null;
		}
	}

	@GetMapping("/childAlert")
	public ResponseChildAlert childAlert(@RequestParam String address) {
		logger.debug("requête childAlert envoyée de PersonController");
		try {
			logger.info("requête childAlert réussie chez PersonController!");
			return personService.childAlert(address);
		}catch(Exception e) {
			logger.error("marche pas :(",e);
			return null;
		}
		
	}

	@GetMapping("/personInfo")
	public ResponsePersonInfo personInfo(@RequestParam String firstName, @RequestParam String lastName) {
		logger.debug("requête personInfo envoyée de PersonController");
		try {
			logger.info("requête personInfo réussie chez PersonController!");
			return personService.personInfo(firstName, lastName);
		}catch(Exception e) {
			logger.error("marche pas :(",e);
			return null;
		}
	}

}
