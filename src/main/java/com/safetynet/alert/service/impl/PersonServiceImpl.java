package com.safetynet.alert.service.impl;

import java.util.ArrayList;
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
import com.safetynet.alert.service.UtilService;

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
	private UtilService util;

	public Person addPerson(Person person) {
		logger.debug("traitement addPerson en cours chez PersonServiceImpl");
		try {
			Person response = personRepository.save(person);
			logger.info("traitement addPerson réussi chez PersonServiceImpl!");
			return response;
		} catch (Exception e) {
			logger.error("marche pas :(", e);
			return null;
		}
	}

	public List<Person> updatePerson(Person updatedPerson) {
		logger.debug("traitement updatePerson en cours chez PersonServiceImpl");
		try {
			List<Person> personList = personRepository.findPersonByFirstNameAndLastName(updatedPerson.getFirstName(),
					updatedPerson.getLastName());
			List<Person> newPersonList = new ArrayList<Person>();
			for (Person newPerson : personList) {
				newPerson.setAddress(updatedPerson.getAddress());
				newPerson.setCity(updatedPerson.getCity());
				newPerson.setZip(updatedPerson.getZip());
				newPerson.setPhone(updatedPerson.getPhone());
				newPerson.setEmail(updatedPerson.getEmail());
				newPersonList.add(newPerson);
			}
			List<Person> response = new ArrayList<>();

			if (personRepository.saveAll(newPersonList) != null)
				response = personRepository.saveAll(newPersonList);
			else
				response = newPersonList;

			logger.info("traitement updatePerson réussi chez PersonServiceImpl!");
			return response;

		} catch (Exception e) {
			logger.error("marche pas :(", e);
			return null;
		}

	}

	public void deletePersonByFirstNameAndLastName(String firstName, String lastName) {
		logger.debug("traitement deletePerson en cours chez PersonServiceImpl");
		try {

			List<Person> personToDeleteList = personRepository.findPersonByFirstNameAndLastName(firstName, lastName);

			for (Person person : personToDeleteList) {
				personRepository.delete(person);
			}
			logger.info("traitement deletePerson réussi chez PersonServiceImpl!");

		} catch (Exception e) {
			logger.error("marche pas :(", e);

		}

	}

	public List<String> listOfEmailByCity(String city) {
		logger.debug("traitement list of email en cours chez PersonServiceImpl");
		try {
			List<String> response = new ArrayList<>();
			response = personRepository.listOfEmailByCity(city);
			logger.info("traitement list of email réussi chez PersonServiceImpl!");
			return response;
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
				List<MedicalRecord> medicalRecordsList = medicalRecordRepository
						.findMedicalRecordByFirstNameAndLastName(firstName, lastName);
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
