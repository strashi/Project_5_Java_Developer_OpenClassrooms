package com.safetynet.alert.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alert.model.Allergie;
import com.safetynet.alert.model.MedicalRecord;
import com.safetynet.alert.model.Medication;
import com.safetynet.alert.model.Person;
import com.safetynet.alert.repository.MedicalRecordRepository;
import com.safetynet.alert.service.impl.UtilServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UtilServiceTests {

	@InjectMocks
	private UtilServiceImpl util; // = new UtilServiceImpl();

	@Mock
	private MedicalRecordRepository medicalRecordRepository;
	
	@Mock
	private Date birthdate;
	
	@Mock
	private MedicalRecord medicalRecord;
	
	@Test
	public void testGetAge() {
		Person person = new Person(0L, "Jack", "Black", "Blv Av", "Moscou", 112233, "052156", "mail@box.xyz");

		// Creation of MedicalRecord
		List<Medication> adultMedications = new ArrayList<>();
		List<Allergie> adultAllergies = new ArrayList<>();

		Date adultBirthday = null;
		SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
		try {
			adultBirthday = DateFor.parse("01/01/1950");
		} catch (ParseException e) {
			e.printStackTrace();
		}

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

		when(medicalRecordRepository.findByFirstNameAndLastName(person.getFirstName(),
				person.getLastName())).thenReturn(adultMedicalRecordsList);

		int result = util.getAge(person);

		assertTrue(result == 73);
		assertFalse(result == 20);

	}
	
	@Test
	public void testGetAgeWithoutBirthdate() {
		Person person = new Person(0L, "Jack", "Black", "Blv Av", "Moscou", 112233, "052156", "mail@box.xyz");

		// Creation of MedicalRecord
		List<Medication> adultMedications = new ArrayList<>();
		List<Allergie> adultAllergies = new ArrayList<>();

		Date adultBirthday = null;
	
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

		when(medicalRecordRepository.findByFirstNameAndLastName(person.getFirstName(),
				person.getLastName())).thenReturn(adultMedicalRecordsList);

		int result = util.getAge(person);

		assertTrue(result == -1);
		assertFalse(result == 20);

	}
	
	@Test
	public void testGetAgeWithoutMedicalRecord() {
		Person person = new Person(0L, "Jack", "Black", "Blv Av", "Moscou", 112233, "052156", "mail@box.xyz");


		when(medicalRecordRepository.findByFirstNameAndLastName(person.getFirstName(),
				person.getLastName())).thenReturn(null);

		int result = util.getAge(person);

		assertTrue(result == -1);
		assertFalse(result == 20);

	}
	
	@Test
	public void testGetAgeWithException() {
		
		Person person = new Person(0L, "firstName", "lastName", "Blv Av", "Moscou", 112233, "052156", "mail@box.xyz");

		when(medicalRecordRepository.findByFirstNameAndLastName("firstName", "lastName")).thenThrow(NullPointerException.class);
		util.getAge(person);
		assertThrows(Exception.class, () -> {medicalRecordRepository.findByFirstNameAndLastName("firstName", "lastName");});
	}
	
	@Test
	public void testCalulateAgeWithException() {
	
		when(birthdate.toInstant()).thenThrow(NullPointerException.class);
			
		util.calculateAge(birthdate);
		
		assertThrows(Exception.class, () -> {birthdate.toInstant();});

	}

}
