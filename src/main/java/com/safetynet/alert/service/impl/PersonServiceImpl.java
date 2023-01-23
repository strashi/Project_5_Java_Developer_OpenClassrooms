package com.safetynet.alert.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

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

	@Transactional
	public void deletePersonByFirstNameAndLastName(String firstName, String lastName) {
		personRepository.deletePersonByFirstNameAndLastName(firstName, lastName);
	}

	public Iterable<String> listOfEmailByCity(String city) {
		return personRepository.listOfEmailByCity(city);
	}

	@Override
	public List<String> getPersonsFromAddressWithBirthdate(String address) {

		List<List<Object>> metaAL = personRepository.getPersonsFromAddressWithBirthdate(address);
		ArrayList<String> newAL = new ArrayList<>();
		ArrayList<String> bufferedAL = new ArrayList<>();
		int numberOfObject = metaAL.size();

		for (List<Object> listObject : metaAL) {
			Date birthdate = (Date) (listObject.get(2));

			String ageCategory = util.determineAgeCategory(util.calculateAge(birthdate));

			if (ageCategory.equals("adulte")) {

				for (int i = 0; i < 2; i++) {
					String param = (listObject.get(i)).toString();
					bufferedAL.add(param);
				}
			}

			if (ageCategory.equals("enfant")) {
				newAL.add("====Enfant====");
				for (int i = 0; i < 2; i++) {
					String param = (listObject.get(i)).toString();
					newAL.add(param);
				}
				int age = util.calculateAge(birthdate);
				newAL.add("Age : " + age + " ans");
				newAL.add("==Membres du foyer==");
				for (String str : bufferedAL) {
					newAL.add(str);
				}
			}
		}
		return newAL;
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

	/*
	 * public List<Person> saveListPersons(List<Person> list) { return
	 * personRepository.saveAll(list); }
	 */
	/*
	 * public Optional<Person> getPerson(final Long id) { return
	 * personRepository.findById(id); }
	 */
	/*
	 * public Iterable<Person> findAllPerson() { return personRepository.findAll();
	 * }
	 */
	/*
	 * public void deletePersonById(Long id) { personRepository.deleteById(id); }
	 */
	/*
	 * public Person findPersonByFirstNameAndLastName(String firstName, String
	 * lastName) { return
	 * personRepository.findPersonByFirstNameAndLastName(firstName, lastName); }
	 */
}
