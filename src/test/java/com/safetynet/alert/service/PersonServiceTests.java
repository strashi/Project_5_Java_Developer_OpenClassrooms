package com.safetynet.alert.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.safetynet.alert.dto.PersonDTO;
import com.safetynet.alert.dto.PersonInfo;
import com.safetynet.alert.dto.ResponseChildAlert;
import com.safetynet.alert.dto.ResponsePersonInfo;
import com.safetynet.alert.model.Allergie;
import com.safetynet.alert.model.MedicalRecord;
import com.safetynet.alert.model.Medication;
import com.safetynet.alert.model.Person;
import com.safetynet.alert.repository.MedicalRecordRepository;
import com.safetynet.alert.repository.PersonRepository;
import com.safetynet.alert.service.impl.PersonServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTests {

	@InjectMocks
	private PersonServiceImpl personService;

	@Mock
	private PersonRepository personRepository;

	@Mock
	private MedicalRecordRepository medicalRecordRepository;

	@Mock
	private UtilService util;

	@Mock
	private ModelMapper modelMapper;

	@Test
	public void testAddPerson() {
		Person person = new Person(0L, "Jack", "Black", "Blv Av", "Moscou", 112233, "052156", "mail@box.xyz");
		when(personRepository.save(person)).thenReturn(person);

		Person savedPerson = personService.addPerson(person);

		assertThat(savedPerson.equals(person));
		verify(personRepository, times(1)).save(person);
	}
	@Test
	public void testAddPersonWithException() {
		Person person = new Person(0L, "Jack", "Black", "Blv Av", "Moscou", 112233, "052156", "mail@box.xyz");
		when(personRepository.save(person)).thenThrow(NullPointerException.class);

		Person savedPerson = personService.addPerson(person);

		assertThrows(Exception.class, () -> {
			personRepository.save(person);
		});
	}

	@Test
	public void testUpdatePerson() {
		List<Person> personList = new ArrayList<>();

		Person personTest = new Person(0L, "Jack", "Black", "Blv Av", "Moscou", 112233, "052156", "mail@box.xyz");
		String personTestAddress = personTest.getAddress();
		String personTestCity = personTest.getCity();
		personList.add(personTest);

		Person updatedPerson = new Person(0L, "Jack", "Black", "rue", "ville", 112233, "052156", "mail@box.xyz");

		when(personRepository.findByFirstNameAndLastName(any(String.class), any(String.class)))
				.thenReturn(personList);
		when(personRepository.saveAll(any(List.class))).thenReturn(null);

		List<Person> personListResponse = personService.updatePerson(updatedPerson);

		Person person = personListResponse.get(0);

		assertTrue(person.getFirstName().equals(personTest.getFirstName()));
		assertTrue(person.getLastName().equals(personTest.getLastName()));
		assertFalse(person.getAddress().equals(personTestAddress));
		assertFalse(person.getCity().equals(personTestCity));

		verify(personRepository, times(1)).findByFirstNameAndLastName(any(String.class), any(String.class));
		verify(personRepository, times(1)).saveAll(any(List.class));
	}
	
	@Test
	public void testUpdatePersonWithNoNullResponse() {
		List<Person> personList = new ArrayList<>();

		Person personTest = new Person(0L, "Jack", "Black", "Blv Av", "Moscou", 112233, "052156", "mail@box.xyz");
		String personTestAddress = personTest.getAddress();
		String personTestCity = personTest.getCity();
		personList.add(personTest);

		Person updatedPerson = new Person(0L, "Jack", "Black", "rue", "ville", 112233, "052156", "mail@box.xyz");
		List<Person> retourPersonList = new ArrayList<>();
		retourPersonList.add(updatedPerson);

		when(personRepository.findByFirstNameAndLastName(any(String.class), any(String.class)))
				.thenReturn(personList);
		when(personRepository.saveAll(any(List.class))).thenReturn(retourPersonList);

		List<Person> personListResponse = personService.updatePerson(updatedPerson);

		Person person = personListResponse.get(0);

		assertTrue(person.getFirstName().equals(personTest.getFirstName()));
		assertTrue(person.getLastName().equals(personTest.getLastName()));
		assertFalse(person.getAddress().equals(personTestAddress));
		assertFalse(person.getCity().equals(personTestCity));

		verify(personRepository, times(1)).findByFirstNameAndLastName(any(String.class), any(String.class));
		verify(personRepository, times(1)).saveAll(any(List.class));
	}


	@Test
	public void testUpdatePersonWithException() {
	
		Person updatedPerson = new Person(0L, "Jack", "Black", "rue", "ville", 112233, "052156", "mail@box.xyz");

		when(personRepository.findByFirstNameAndLastName("firstName", "lastName"))
				.thenThrow(NullPointerException.class);

		List<Person> personListResponse = personService.updatePerson(updatedPerson);

		assertThrows(Exception.class, () -> {
			personRepository.findByFirstNameAndLastName("firstName", "lastName");
		});
	}

	@Test
	public void testDeletePerson() {
		
		Person person = new Person(0L, "Jack", "Black", "Blv Av", "Moscou", 112233, "052156", "mail@box.xyz");
		List<Person> personList = new ArrayList<>();
		personList.add(person);
		when(personRepository.findByFirstNameAndLastName("Jack", "Black")).thenReturn(personList);
		doNothing().when(personRepository).deleteAll(personList);

		personService.deletePersonByFirstNameAndLastName("Jack", "Black");

		verify(personRepository, times(1)).deleteAll(personList);
	}

	@Test
	public void testDeletePersonWithException() {
	
		when(personRepository.findByFirstNameAndLastName("Jack", "Black"))
				.thenThrow(NullPointerException.class);
		
		personService.deletePersonByFirstNameAndLastName("Jack", "Black");

		assertThrows(Exception.class, () -> {
			personRepository.findByFirstNameAndLastName("Jack", "Black");
		});
	}

	@Test
	public void testListOfEmailByCity() {
		List<String> listOfEmail = new ArrayList<>();

		listOfEmail.add("email1");
		listOfEmail.add("email2");
		listOfEmail.add("email3");

		String city = "city";
		when(personRepository.listOfEmailByCity(city)).thenReturn(listOfEmail);

		List<String> response = personService.listOfEmailByCity(city);

		assertThat(response != null);
		verify(personRepository, times(1)).listOfEmailByCity(city);

	}

	@Test
	public void testListOfEmailByCitywithException() {
		
		when(personRepository.listOfEmailByCity("city")).thenThrow(NullPointerException.class);

		List<String> response = personService.listOfEmailByCity("city");

		assertThrows(Exception.class, () -> {
			personRepository.listOfEmailByCity("city");
		});

	}

	@Test
	public void testChildAlert() {
		
		Person adult = new Person(0L, "Jack", "Black", "Blv Av", "Moscou", 112233, "052156", "mail@box.xyz");
		Person child = new Person(0L, "Jo", "White", "Blv Av", "Moscou", 112233, "052156", "mail@box.xyz");

		List<Person> listOfPersons = new ArrayList<>();
		listOfPersons.add(adult);
		listOfPersons.add(child);

		int age = -1;
		PersonDTO childDTO = new PersonDTO("Jo", "White", "Blv Av", "Moscou", 112233, "052156", "mail@box.xyz", age);
		PersonDTO adultDTO = new PersonDTO("Jack", "Black", "Blv Av", "Moscou", 112233, "052156", "mail@box.xyz", age);

		when(personRepository.findByAddress("address")).thenReturn(listOfPersons);
		when(modelMapper.map(child, PersonDTO.class)).thenReturn(childDTO);
		when(modelMapper.map(adult, PersonDTO.class)).thenReturn(adultDTO);

		when(util.getAge(adult)).thenReturn(35);
		when(util.getAge(child)).thenReturn(15);

		ResponseChildAlert response = personService.childAlert("address");

		assertThat(response.getAdultsList() != null);
		assertThat(response.getChildrenList() != null);

		verify(util, times(1)).getAge(child);
		verify(util, times(1)).getAge(adult);

	}

	@Test
	public void testChildAlertWithException() {
		when(personRepository.findByAddress("address")).thenThrow(NullPointerException.class);

		ResponseChildAlert response = personService.childAlert("address");

		assertThrows(Exception.class, () -> {
			personRepository.findByAddress("address");
		});

	}

	@Test
	public void testPersonInfo() {
	
		Person person = new Person(0L, "Jack", "Black", "Blv Av", "Moscou", 112233, "052156", "mail@box.xyz");
		List<Person> listOfPersons = new ArrayList<>();
		listOfPersons.add(person);

		List<Medication> medicationsInfoPerson = new ArrayList<>();
		List<Allergie> allergiesInfoPerson = new ArrayList<>();
		PersonInfo infoPerson = new PersonInfo("Jack", "Black", "mail@box.xyz", -1, medicationsInfoPerson,
				allergiesInfoPerson);

		List<Medication> medications = new ArrayList<>();
		Medication medication = new Medication("aznol 350mg");
		medications.add(medication);
		List<Allergie> allergies = new ArrayList<>();
		Allergie allergie = new Allergie("cacahouète");
		allergies.add(allergie);
		Date date = new Date(01 / 01 / 1950);
		MedicalRecord medicalRecord = new MedicalRecord(0L, "Jack", "Black", date, medications, allergies);

		List<MedicalRecord> medicalRecordsList = new ArrayList<>();
		medicalRecordsList.add(medicalRecord);

		when(personRepository.findByFirstNameAndLastName("Jack", "Black")).thenReturn(listOfPersons);
		when(modelMapper.map(person, PersonInfo.class)).thenReturn(infoPerson);
		when(util.getAge(person)).thenReturn(83);
		when(medicalRecordRepository.findByFirstNameAndLastName("Jack", "Black"))
				.thenReturn(medicalRecordsList);

		ResponsePersonInfo response = personService.personInfo("Jack", "Black");

		assertThat(response.getPersonInfosList().get(0).getFirstName().equals("Jack"));
		assertThat(response.getPersonInfosList().get(0).getMedications().equals(medications));

		verify(medicalRecordRepository, times(1)).findByFirstNameAndLastName("Jack", "Black");

	}

	@Test
	public void testPersonInfoWithException() {
		when(personRepository.findByFirstNameAndLastName("Jack", "Black")).thenThrow(NullPointerException.class);

		ResponsePersonInfo response = personService.personInfo("Jack", "Black");

		assertThrows(Exception.class, () -> {
			personRepository.findByFirstNameAndLastName("Jack", "Black");
		});

	}

	@Test
	public void testEquals() {

		final PersonServiceImpl service = new PersonServiceImpl();

		assertFalse(personService.equals(service));
		assertFalse(personService.toString().equals(service.toString()));
	}
	

}
