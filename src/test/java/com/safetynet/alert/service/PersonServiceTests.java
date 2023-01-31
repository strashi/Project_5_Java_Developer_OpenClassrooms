package com.safetynet.alert.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

import com.safetynet.alert.dto.InfoPerson;
import com.safetynet.alert.dto.PersonDTO;
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
	//private PersonRepository personRepository;
	
	@InjectMocks
	private PersonService personService = new PersonServiceImpl();
	
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
		long id=0L;
		Person person = new Person(id,"Jack","Black","Blv Av","Moscou",112233,"052156","mail@box.xyz");
		Person falsePerson = new Person(id,"Jo","White","Blv Av","Moscou",112233,"052156","mail@box.xyz");

		when(personRepository.save(person)).thenReturn(person);
		
		Person savedPerson = personService.addPerson(person);
		
		assertThat(savedPerson.equals(person));
		assertFalse(savedPerson.equals(falsePerson));

		
	}
	
	@Test
	public void testUpdatePerson() {
		List<Person> personList = new ArrayList<>();
			
		long id=0L;
		Person personTest = new Person(id,"Jack","Black","Blv Av","Moscou",112233,"052156","mail@box.xyz");
		String personTestAddress = personTest.getAddress();
		String personTestCity = personTest.getCity();
		personList.add(personTest);
		
		Person updatedPerson = new Person(id,"Jack","Black","rue","ville",112233,"052156","mail@box.xyz");
	
				
		when(personRepository.findPersonByFirstNameAndLastName(any(String.class), any(String.class))).thenReturn(personList);
		when(personRepository.saveAll(any(List.class))).thenReturn(null);
		
		List<Person> personListResponse = personService.updatePerson(updatedPerson);
			
		Person person = personListResponse.get(0);
		
		assertTrue(person.getFirstName().equals(personTest.getFirstName()));
		assertTrue(person.getLastName().equals(personTest.getLastName()));
		assertFalse(person.getAddress().equals(personTestAddress));
		assertFalse(person.getCity().equals(personTestCity));
			
		verify(personRepository, times (1)).findPersonByFirstNameAndLastName(any(String.class), any(String.class));
		verify(personRepository, times(1)).saveAll(any(List.class));
	}
	
	@Test
	public void testDeletePerson() {
		String firstName = "Jack";
		String lastName = "Black";
		Person person = new Person(0L,"Jack","Black","Blv Av","Moscou",112233,"052156","mail@box.xyz");
		List<Person> personList = new ArrayList<>();
		personList.add(person);
		when(personRepository.findPersonByFirstNameAndLastName(firstName, lastName)).thenReturn(personList);
		doNothing().when(personRepository).delete(person);
		
		personService.deletePersonByFirstNameAndLastName(firstName, lastName);
		
		verify(personRepository, times(1)).delete(person);
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
	public void testChildAlert() {
		String address = "address";
		Person adult = new Person(0L,"Jack","Black","Blv Av","Moscou",112233,"052156","mail@box.xyz");
		Person child = new Person(0L,"Jo","White","Blv Av","Moscou",112233,"052156","mail@box.xyz");
		
		List<Person> listOfPersons = new ArrayList<>();
		listOfPersons.add(adult);
		listOfPersons.add(child);
		
		int age =-1;
		PersonDTO childDTO = new PersonDTO("Jo","White","Blv Av","Moscou",112233,"052156","mail@box.xyz",age);
		PersonDTO adultDTO = new PersonDTO("Jack","Black","Blv Av","Moscou",112233,"052156","mail@box.xyz",age);
		
		when(personRepository.findByAddress(address)).thenReturn(listOfPersons);
		when(modelMapper.map(child, PersonDTO.class)).thenReturn(childDTO);
		when(modelMapper.map(adult, PersonDTO.class)).thenReturn(adultDTO);

		when(util.getAge(adult)).thenReturn(35);
		when(util.getAge(child)).thenReturn(15);

		
		ResponseChildAlert response = personService.childAlert(address);
		
		assertThat(response.getAdultList() != null);
		assertThat(response.getChildrenList() != null);
		
		verify(util, times(1)).getAge(child);
		verify(util, times(1)).getAge(adult);
		
	}
	
	@Test
	public void testPersonInfo() {
		String firstName = "Jack";
		String lastName = "Black";
		Person person = new Person(0L,"Jack","Black","Blv Av","Moscou",112233,"052156","mail@box.xyz");
		List<Person> listOfPersons = new ArrayList<>();
		listOfPersons.add(person);
		
		List<Medication> medicationsInfoPerson= new ArrayList<>();
		List<Allergie> allergiesInfoPerson = new ArrayList<>();
		InfoPerson infoPerson = new InfoPerson("Jack","Black","mail@box.xyz",-1, medicationsInfoPerson, allergiesInfoPerson);
		
		
		
		List<Medication> medications = new ArrayList<>();
		Medication medication = new Medication("aznol 350mg");
		medications.add(medication);
		List<Allergie> allergies = new ArrayList<>();
		Allergie allergie = new Allergie("cacahouète");
		allergies.add(allergie);
		Date date = new Date(01/01/1950);
		MedicalRecord medicalRecord = new MedicalRecord(0L,"Jack","Black", date , medications, allergies);
		
		List<MedicalRecord> medicalRecordsList = new ArrayList<>();
		medicalRecordsList.add(medicalRecord);
		
		when(personRepository.findAllByFirstNameAndLastName(firstName, lastName)).thenReturn(listOfPersons);
		when(modelMapper.map(person, InfoPerson.class)).thenReturn(infoPerson);
		when(util.getAge(person)).thenReturn(83);
		when(medicalRecordRepository
						.findMedicalRecordByFirstNameAndLastName(firstName, lastName)).thenReturn(medicalRecordsList);

		
		ResponsePersonInfo response  = personService.personInfo(firstName, lastName);
		
		assertThat(response.getInfosPersons().get(0).getFirstName().equals(firstName));
		assertThat(response.getInfosPersons().get(0).getMedications().equals(medications));
		
		verify(medicalRecordRepository, times(1)).findMedicalRecordByFirstNameAndLastName(firstName, lastName);

	}
}
