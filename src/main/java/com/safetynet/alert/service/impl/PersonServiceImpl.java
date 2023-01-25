package com.safetynet.alert.service.impl;

import java.util.List;

import org.hibernate.annotations.DynamicUpdate;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alert.dto.InfoPerson;
import com.safetynet.alert.dto.PersonDTO;
import com.safetynet.alert.dto.ResponseChildAlert;
import com.safetynet.alert.dto.ResponsePersonInfo;
import com.safetynet.alert.model.MedicalRecord;
import com.safetynet.alert.model.Person;
import com.safetynet.alert.repository.MedicalRecordRepository;
import com.safetynet.alert.repository.PersonRepository;
import com.safetynet.alert.service.PersonService;

import lombok.Data;

@Data
@Service
@DynamicUpdate
public class PersonServiceImpl implements PersonService {

	private static final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private MedicalRecordRepository medicalRecordRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UtilServiceImpl util;

	public Person addPerson(Person person) {
		logger.debug("traitement addPerson en cours chez PersonServiceImpl");
		try {
			logger.info("traitement addPerson réussi chez PersonServiceImpl!");
			return personRepository.save(person);
		} catch (Exception e) {
			logger.error("marche pas :(", e);
			return null;
		}
	}

	public Person updatePerson(Person oldPerson) {
		logger.debug("traitement addPerson en cours chez PersonServiceImpl");
		try {
			Person newPerson = personRepository.findPersonByFirstNameAndLastName(oldPerson.getFirstName(),
					oldPerson.getLastName());
			newPerson.setAddress(oldPerson.getAddress());
			newPerson.setCity(oldPerson.getCity());
			newPerson.setZip(oldPerson.getZip());
			newPerson.setPhone(oldPerson.getPhone());
			newPerson.setEmail(oldPerson.getEmail());

			logger.info("traitement addPerson réussi chez PersonServiceImpl!");
			return personRepository.save(newPerson);

		} catch (Exception e) {
			logger.error("marche pas :(", e);
			return null;
		}

	}

	public void deletePersonByFirstNameAndLastName(String firstName, String lastName) {
		logger.debug("traitement deletePerson en cours chez PersonServiceImpl");
		try {

			Person personToDelete = personRepository.findPersonByFirstNameAndLastName(firstName, lastName);
			logger.info("traitement deletePerson réussi chez PersonServiceImpl!");
			personRepository.delete(personToDelete);
		} catch (Exception e) {
			logger.error("marche pas :(", e);

		}

	}

	public Iterable<String> listOfEmailByCity(String city) {
		logger.debug("traitement list of email en cours chez PersonServiceImpl");
		try {
			logger.info("traitement list of email réussi chez PersonServiceImpl!");
			return personRepository.listOfEmailByCity(city);
		} catch (Exception e) {
			logger.error("marche pas :(", e);
			return null;
		}

	}

	public ResponseChildAlert childAlert(String address) {
		logger.debug("traitement childAlert en cours chez PersonServiceImpl");
		try {
			ResponseChildAlert response = new ResponseChildAlert();

			List<Person> personList = personRepository.findByAddress(address);
			for (Person person : personList) {
				PersonDTO personDTO = modelMapper.map(person, PersonDTO.class);
				personDTO.setAge(util.getAge(person));
				if (personDTO.getAge() < 19)
					response.getChildrenList().add(personDTO);
				else
					response.getAdultList().add(personDTO);
			}
			logger.info("traitement childAlert réussi chez PersonServiceImpl!");
			return response;
		} catch (Exception e) {
			logger.error("marche pas :(", e);
			return null;
		}

	}

	public ResponsePersonInfo personInfo(String firstName, String lastName) {
		logger.debug("traitement personInfo en cours chez PersonServiceImpl");
		try {
			ResponsePersonInfo response = new ResponsePersonInfo();

			List<Person> personList = personRepository.findAllByFirstNameAndLastName(firstName, lastName);
			for (Person person : personList) {
				InfoPerson infoPerson = modelMapper.map(person, InfoPerson.class);
				infoPerson.setAge(util.getAge(person));
				List<MedicalRecord> medicalRecordsList = medicalRecordRepository.findByFirstNameAndLastName(firstName,
						lastName);
				for (MedicalRecord medicalRecord : medicalRecordsList) {
					infoPerson.getMedications().addAll(medicalRecord.getMedications());
					infoPerson.getAllergies().addAll(medicalRecord.getAllergies());
				}

				response.getInfosPersons().add(infoPerson);
			}
			logger.info("traitement personInfo réussi chez PersonServiceImpl!");
			return response;

		} catch (Exception e) {
			logger.error("marche pas :(", e);
			return null;
		}

	}

}
