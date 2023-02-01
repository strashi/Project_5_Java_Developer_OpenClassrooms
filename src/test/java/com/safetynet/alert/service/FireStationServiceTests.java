package com.safetynet.alert.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.safetynet.alert.dto.PersonDTO;
import com.safetynet.alert.dto.PersonWithMedicalRecordDTO;
import com.safetynet.alert.dto.ResponseFire;
import com.safetynet.alert.dto.ResponseFlood;
import com.safetynet.alert.dto.ResponsePersonByFireStation;
import com.safetynet.alert.model.Allergie;
import com.safetynet.alert.model.FireStation;
import com.safetynet.alert.model.MedicalRecord;
import com.safetynet.alert.model.Medication;
import com.safetynet.alert.model.Person;
import com.safetynet.alert.repository.FireStationRepository;
import com.safetynet.alert.repository.MedicalRecordRepository;
import com.safetynet.alert.repository.PersonRepository;
import com.safetynet.alert.service.impl.FireStationServiceImpl;

@ExtendWith(MockitoExtension.class)
public class FireStationServiceTests {

	@InjectMocks
	private FireStationService fireStationService = new FireStationServiceImpl();

	@Mock
	private FireStationRepository fireStationRepository;

	@Mock
	private PersonRepository personRepository;

	@Mock
	private MedicalRecordRepository medicalRecordRepository;

	@Mock
	private ModelMapper modelMapper;

	@Mock
	private UtilService util;

	@Test
	public void testAddStation() {
		FireStation newFireStation = new FireStation(0L, "address", 13);
		FireStation falseFireStation = new FireStation(0L, "address", 14);

		when(fireStationRepository.save(newFireStation)).thenReturn(newFireStation);

		FireStation fireStation = fireStationService.addFireStation(newFireStation);

		assertThat(fireStation.equals(newFireStation));
		assertFalse(fireStation.equals(falseFireStation));

		verify(fireStationRepository, times(1)).save(newFireStation);
	}

	@Test
	public void testUpdateFireStation() {

		String address = "address";
		FireStation fireStation1 = new FireStation(0L, address, 1);
		FireStation fireStation2 = new FireStation(0L, address, 2);

		List<FireStation> listOfOldFireStations = new ArrayList<>();
		listOfOldFireStations.add(fireStation1);
		listOfOldFireStations.add(fireStation2);

		List<Integer> newStations = new ArrayList<>();
		newStations.add(6);
		newStations.add(7);

		when(fireStationRepository.findByAddress(address)).thenReturn(listOfOldFireStations);
		when(fireStationRepository.saveAll(any(List.class))).thenReturn(null);

		List<FireStation> fireStationResponse = fireStationService.updateFireStation(address, newStations);

		assertEquals(fireStationResponse.get(0).getStation(), 6);
		assertEquals(fireStationResponse.get(1).getStation(), 7);
		assertFalse(fireStationResponse.get(0).getStation() == 1);
		assertFalse(fireStationResponse.get(1).getStation() == 2);

		verify(fireStationRepository, times(1)).findByAddress(address);
		verify(fireStationRepository, times(1)).saveAll(any(List.class));

	}

	@Test
	public void testDeleteFireStation() {

		FireStation fireStation = new FireStation(0L, "address", 2);

		List<FireStation> fireStationToDeleteList = new ArrayList<>();

		when(fireStationRepository.findByAddress(fireStation.getAddress())).thenReturn(fireStationToDeleteList);
		doNothing().when(fireStationRepository).deleteAll(fireStationToDeleteList);

		fireStationService.deleteFireStation(fireStation);

		verify(fireStationRepository, times(1)).deleteAll(fireStationToDeleteList);
	}

	@Test
	public void testPhoneAlert() {
		FireStation fireStation = new FireStation();
		List<String> listOfTelephonNumber = new ArrayList<>();
		//listOfTelephonNumber.add("123456");

		when(fireStationRepository.phoneAlert(fireStation.getStation())).thenReturn(listOfTelephonNumber);

		fireStationService.phoneAlert(fireStation.getStation());

		verify(fireStationRepository, times(1)).phoneAlert(fireStation.getStation());
	}

	@Test
	public void testCoveredPersonsByFireStationWithChildrenAdultCount() {

		FireStation fireStation = new FireStation(0L, "address", 2);
		List<FireStation> listOfFireStations = new ArrayList<>();
		listOfFireStations.add(fireStation);

		Person adult = new Person(0L, "Jack", "Black", "Blv Av", "Moscou", 112233, "052156", "mail@box.xyz");
		Person child = new Person(0L, "Jo", "White", "Blv Av", "Moscou", 112233, "052156", "mail@box.xyz");

		List<Person> listOfPersons = new ArrayList<>();
		listOfPersons.add(adult);
		listOfPersons.add(child);

		int age = -1;
		PersonDTO childDTO = new PersonDTO("Jo", "White", "Blv Av", "Moscou", 112233, "052156", "mail@box.xyz", age);
		PersonDTO adultDTO = new PersonDTO("Jack", "Black", "Blv Av", "Moscou", 112233, "052156", "mail@box.xyz", age);

		when(personRepository.findByAddress(fireStation.getAddress())).thenReturn(listOfPersons);
		when(modelMapper.map(child, PersonDTO.class)).thenReturn(childDTO);
		when(modelMapper.map(adult, PersonDTO.class)).thenReturn(adultDTO);

		when(util.getAge(adult)).thenReturn(35);
		when(util.getAge(child)).thenReturn(15);

		when(fireStationRepository.findByStation(any(Integer.class))).thenReturn(listOfFireStations);
		when(personRepository.findByAddress(fireStation.getAddress())).thenReturn(listOfPersons);

		ResponsePersonByFireStation response = fireStationService
				.coveredPersonsByFireStationWithChildrenAdultCount(fireStation.getStation());

		assertTrue(response.getPersons().size() == 2);
		assertTrue(response.getNumberOfAdults() == 1);
		assertTrue(response.getNumberOfChildren() == 1);

		verify(util, times(1)).getAge(child);

	}

	@Test
	public void testFire() {

		String address = "address";

		// Creation of FireStation
		FireStation fireStation = new FireStation(0L, "address", 2);

		List<FireStation> listOfFireStations = new ArrayList<>();
		listOfFireStations.add(fireStation);

		// Creation ot Persons
		List<Person> residentsList = new ArrayList<>();
		Person adult = new Person(0L, "Jack", "Black", "Blv Av", "Moscou", 112233, "052156", "mail@box.xyz");
		Person child = new Person(0L, "Jo", "White", "Blv Av", "Moscou", 112233, "052156", "mail@box.xyz");

		residentsList.add(adult);
		residentsList.add(child);

		// Creation of Persons With empties MedicalRecords
		List<Medication> childMedicationsEmpty = new ArrayList<>();

		List<Allergie> childAllergiesEmpty = new ArrayList<>();

		List<Medication> adultMedicationsEmpty = new ArrayList<>();

		List<Allergie> adultAllergiesEmpty = new ArrayList<>();

		PersonWithMedicalRecordDTO childWithMedicalRecord = new PersonWithMedicalRecordDTO("Jo", "White", "052156", -1,
				childMedicationsEmpty, childAllergiesEmpty);
		PersonWithMedicalRecordDTO adultWithMedicalRecord = new PersonWithMedicalRecordDTO("Jack", "Black", "052156",
				-1, adultMedicationsEmpty, adultAllergiesEmpty);

		// Creation of MedicalRecords
		List<Medication> adultMedications = new ArrayList<>();
		List<Allergie> adultAllergies = new ArrayList<>();
		List<Medication> childMedications = new ArrayList<>();

		List<Allergie> childAllergies = new ArrayList<>();

		Date childBirthday = new Date(01 / 01 / 2010);
		Date adultBirthday = new Date(01 / 01 / 1950);

		Medication medication1 = new Medication("aznol 350mg");
		Medication medication2 = new Medication("jus d'ail 3x par jour");
		adultMedications.add(medication1);
		adultMedications.add(medication2);

		Allergie allergie = new Allergie("glutène");
		adultAllergies.add(allergie);

		List<MedicalRecord> adultMedicalRecordsList = new ArrayList<>();
		MedicalRecord adultMedicalRecord = new MedicalRecord(0L, "Jack", "Black", adultBirthday, adultMedications,
				adultAllergies);
		adultMedicalRecordsList.add(adultMedicalRecord);

		List<MedicalRecord> childMedicalRecordsList = new ArrayList<>();
		MedicalRecord childMedicalRecord = new MedicalRecord(0L, "Jo", "White", childBirthday, childMedications,
				childAllergies);
		childMedicalRecordsList.add(childMedicalRecord);

		when(fireStationRepository.findByAddress(address)).thenReturn(listOfFireStations);
		when(personRepository.findByAddress(address)).thenReturn(residentsList);
		when(modelMapper.map(child, PersonWithMedicalRecordDTO.class)).thenReturn(childWithMedicalRecord);
		when(modelMapper.map(adult, PersonWithMedicalRecordDTO.class)).thenReturn(adultWithMedicalRecord);

		when(util.getAge(adult)).thenReturn(73);
		when(util.getAge(child)).thenReturn(13);
		when(medicalRecordRepository.findMedicalRecordByFirstNameAndLastName(adult.getFirstName(), adult.getLastName()))
				.thenReturn(adultMedicalRecordsList);
		when(medicalRecordRepository.findMedicalRecordByFirstNameAndLastName(child.getFirstName(), child.getLastName()))
				.thenReturn(childMedicalRecordsList);
		
		ResponseFire response = fireStationService.fire(address);

		assertTrue(response.getStation().size() == 1);
		assertTrue(response.getResidents().size() == 2);
		assertTrue(response.getResidents().get(0).getAllergies().size() == 1);
		assertTrue(response.getResidents().get(0).getMedications().size() == 2);
		assertTrue(response.getResidents().get(1).getAllergies().size() == 0);
		assertTrue(response.getResidents().get(1).getMedications().size() == 0);

		verify(medicalRecordRepository, times(2)).findMedicalRecordByFirstNameAndLastName(any(String.class),
				any(String.class));

	}

	@Test
	public void testFlood() {
		// Creation of list of integer
		List<Integer> listOfInteger = new ArrayList<>();
		listOfInteger.add(1);
		listOfInteger.add(2);
		// Creation of 2 list of firestations
		List<FireStation> listOfStations1 = new ArrayList<>();
		FireStation fireStation1 = new FireStation(0L, "address1", 1);
		listOfStations1.add(fireStation1);
		FireStation fireStation2 = new FireStation(0L, "address2", 1);
		listOfStations1.add(fireStation2);

		List<FireStation> listOfStations2 = new ArrayList<>();
		FireStation fireStation3 = new FireStation(0L, "address3", 2);
		listOfStations2.add(fireStation3);
		FireStation fireStation4 = new FireStation(0L, "address4", 2);
		listOfStations2.add(fireStation4);

		// Creation of list of residents
		List<Person> residentsList = new ArrayList<>();
		Person adult = new Person(0L, "Jack", "Black", "address1", "Moscou", 112233, "052156", "mail@box.xyz");
		Person child = new Person(0L, "Jo", "White", "address1", "Moscou", 112233, "052156", "mail@box.xyz");

		residentsList.add(adult);
		residentsList.add(child);

		// Creation of Persons With empties MedicalRecords
		List<Medication> childMedicationsEmpty = new ArrayList<>();

		List<Allergie> childAllergiesEmpty = new ArrayList<>();

		List<Medication> adultMedicationsEmpty = new ArrayList<>();

		List<Allergie> adultAllergiesEmpty = new ArrayList<>();

		PersonWithMedicalRecordDTO childWithMedicalRecord = new PersonWithMedicalRecordDTO("Jo", "White", "052156", -1,
				childMedicationsEmpty, childAllergiesEmpty);
		PersonWithMedicalRecordDTO adultWithMedicalRecord = new PersonWithMedicalRecordDTO("Jack", "Black", "052156",
				-1, adultMedicationsEmpty, adultAllergiesEmpty);

		// Creation of MedicalRecords
		List<Medication> adultMedications = new ArrayList<>();
		List<Allergie> adultAllergies = new ArrayList<>();
		List<Medication> childMedications = new ArrayList<>();

		List<Allergie> childAllergies = new ArrayList<>();

		Date childBirthday = new Date(01 / 01 / 2010);
		Date adultBirthday = new Date(01 / 01 / 1950);

		Medication medication1 = new Medication("aznol 350mg");
		Medication medication2 = new Medication("jus d'ail 3x par jour");
		adultMedications.add(medication1);
		adultMedications.add(medication2);

		Allergie allergie = new Allergie("glutène");
		adultAllergies.add(allergie);

		List<MedicalRecord> adultMedicalRecordsList = new ArrayList<>();
		MedicalRecord adultMedicalRecord = new MedicalRecord(0L, "Jack", "Black", adultBirthday, adultMedications,
				adultAllergies);
		adultMedicalRecordsList.add(adultMedicalRecord);

		List<MedicalRecord> childMedicalRecordsList = new ArrayList<>();
		MedicalRecord childMedicalRecord = new MedicalRecord(0L, "Jo", "White", childBirthday, childMedications,
				childAllergies);
		childMedicalRecordsList.add(childMedicalRecord);

		when(fireStationRepository.findByStation(1)).thenReturn(listOfStations1);
		when(fireStationRepository.findByStation(2)).thenReturn(listOfStations2);
		when(personRepository.findByAddress(any(String.class))).thenReturn(residentsList);
		when(modelMapper.map(child, PersonWithMedicalRecordDTO.class)).thenReturn(childWithMedicalRecord);
		when(modelMapper.map(adult, PersonWithMedicalRecordDTO.class)).thenReturn(adultWithMedicalRecord);

		when(util.getAge(adult)).thenReturn(73);
		when(util.getAge(child)).thenReturn(13);
		when(medicalRecordRepository.findMedicalRecordByFirstNameAndLastName(adult.getFirstName(), adult.getLastName())).thenReturn(adultMedicalRecordsList);
		when(medicalRecordRepository.findMedicalRecordByFirstNameAndLastName(child.getFirstName(), child.getLastName())).thenReturn(childMedicalRecordsList);

		ResponseFlood response = fireStationService.flood(listOfInteger);
		
		assertTrue(response.getResidentsByStation().get(0).getAddressesServedByFireStation().get(0).getListOfPersonsWithMedicalRecordDTO().get(1).getMedications().size() == 0);
		assertTrue(response.getResidentsByStation().get(0).getAddressesServedByFireStation().get(0).getListOfPersonsWithMedicalRecordDTO().get(1).getAllergies().size() == 0);
		//assertTrue(response.getResidentsByStation().get(0).getAddressesServedByFireStation().get(0).getListOfPersonsWithMedicalRecordDTO().get(0).getMedications().size() == 2);
		assertTrue(response.getResidentsByStation().get(0).getAddressesServedByFireStation().get(0).getListOfPersonsWithMedicalRecordDTO().get(0).getMedications().get(1).getMedication().toString() =="jus d'ail 3x par jour" );
 
	}
	
}
