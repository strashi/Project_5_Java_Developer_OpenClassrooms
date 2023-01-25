package com.safetynet.alert.service.impl;

import java.util.List;


import org.hibernate.annotations.DynamicUpdate;
import org.modelmapper.ModelMapper;
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

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private MedicalRecordRepository medicalRecordRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private Util util;

	public Person addPerson(Person person) {
		return personRepository.save(person);
	}

	public Person updatePerson(Person oldPerson) {
		Person newPerson = personRepository.findPersonByFirstNameAndLastName(oldPerson.getFirstName(),
				oldPerson.getLastName());
		newPerson.setAddress(oldPerson.getAddress());
		newPerson.setCity(oldPerson.getCity());
		newPerson.setZip(oldPerson.getZip());
		newPerson.setPhone(oldPerson.getPhone());
		newPerson.setEmail(oldPerson.getEmail());

		return personRepository.save(newPerson);
	}

	public void deletePersonByFirstNameAndLastName(String firstName, String lastName) {
		Person personToDelete = personRepository.findPersonByFirstNameAndLastName(firstName, lastName);
		personRepository.delete(personToDelete);
	}

	public Iterable<String> listOfEmailByCity(String city) {
		return personRepository.listOfEmailByCity(city);
	}

	public ResponseChildAlert childAlert(String address) {
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

		return response;
	}

	public ResponsePersonInfo personInfo(String firstName, String lastName) {
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
		return response;
	}

}
