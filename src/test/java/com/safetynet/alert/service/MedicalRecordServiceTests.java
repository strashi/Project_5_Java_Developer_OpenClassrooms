package com.safetynet.alert.service;

import static org.assertj.core.api.Assertions.assertThat;
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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alert.model.Allergie;
import com.safetynet.alert.model.MedicalRecord;
import com.safetynet.alert.model.Medication;
import com.safetynet.alert.repository.MedicalRecordRepository;
import com.safetynet.alert.service.impl.MedicalRecordServiceImpl;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTests {
	
	@InjectMocks
	private MedicalRecordServiceImpl medicalRecordService; // = new MedicalRecordServiceImpl();
	
	@Mock
	private MedicalRecordRepository medicalRecordRepository;
	
	private MedicalRecord medicalRecord;
	
	@BeforeEach
	public void initEach() {
		Date birthday = new Date(01/01/1960);
		List<Medication> medications= new ArrayList<>();
		List<Allergie> allergies= new ArrayList<>();

		medicalRecord = new MedicalRecord(0L,"firstName","lastName",birthday,medications,allergies);
	}
	
	@Test 
	public void testAddMedicalRecord(){
		
		when( medicalRecordRepository.save(medicalRecord)).thenReturn(medicalRecord);
		MedicalRecord medicalRecordResponse = medicalRecordService.addMedicalRecord(medicalRecord);
				
		assertThat(medicalRecordResponse.equals(medicalRecord));
		
		verify(medicalRecordRepository, times(1)).save(medicalRecord);
	}
	
	@Test 
	public void testAddMedicalRecordWithException(){
		
		when( medicalRecordRepository.save(medicalRecord)).thenThrow(NullPointerException.class);
		MedicalRecord medicalRecordResponse = medicalRecordService.addMedicalRecord(medicalRecord);
				
		assertThrows(Exception.class, () -> {medicalRecordRepository.save(medicalRecord);});
		
		//verify(medicalRecordRepository, times(1)).save(medicalRecord);
	}
	
	@Test 
	public void testUpdateMedicalRecord(){
		List<MedicalRecord> medicalRecordList = new ArrayList<>();
		medicalRecordList.add(medicalRecord);
		
		//Creation of updated medicalrecord
		Date updatedBirthday = new Date(01/01/1980);
		List<Medication> updatedMedications= new ArrayList<>();
		
		List<Allergie> updatedAllergies= new ArrayList<>();
		
		Medication medication1 = new Medication("aznol 350mg");
		Medication medication2 = new Medication("jus d'ail 3x par jour");
		updatedMedications.add(medication1);
		updatedMedications.add(medication2);

		Allergie allergie = new Allergie("glutène");
		updatedAllergies.add(allergie);

		MedicalRecord updatedMedicalRecord = new MedicalRecord(0L,"firstName","lastName",updatedBirthday,updatedMedications,updatedAllergies);
				
		when(medicalRecordRepository.findMedicalRecordByFirstNameAndLastName(
				any(String.class), any(String.class))).thenReturn(medicalRecordList);
		when(medicalRecordRepository.saveAll(any(List.class))).thenReturn(null);
		
		List<MedicalRecord> medicalRecordResponse = medicalRecordService.updateMedicalRecord(updatedMedicalRecord);
				
		assertTrue(medicalRecordResponse.get(0).getMedications().get(0).getMedication().toString() == "aznol 350mg");
		assertTrue(medicalRecordResponse.get(0).getMedications().size() ==2);
		assertTrue(medicalRecordResponse.get(0).getFirstName().toString() == medicalRecord.getFirstName());
		assertTrue(medicalRecordResponse.get(0).getAllergies() != null);
		
		verify(medicalRecordRepository, times(1)).saveAll(any(List.class));
	}
	
	@Test 
	public void testUpdateMedicalRecordWithException(){
		List<MedicalRecord> medicalRecordList = new ArrayList<>();
		medicalRecordList.add(medicalRecord);
		
		String firstName = "firstName";
		String lastName = "lastName";
		
		//Creation of updated medicalrecord
		Date updatedBirthday = new Date(01/01/1980);
		List<Medication> updatedMedications= new ArrayList<>();
		
		List<Allergie> updatedAllergies= new ArrayList<>();
		
		Medication medication1 = new Medication("aznol 350mg");
		Medication medication2 = new Medication("jus d'ail 3x par jour");
		updatedMedications.add(medication1);
		updatedMedications.add(medication2);

		Allergie allergie = new Allergie("glutène");
		updatedAllergies.add(allergie);

		MedicalRecord updatedMedicalRecord = new MedicalRecord(0L,"firstName","lastName",updatedBirthday,updatedMedications,updatedAllergies);
				
		when(medicalRecordRepository.findMedicalRecordByFirstNameAndLastName(
				firstName, lastName)).thenThrow(NullPointerException.class);
		//when(medicalRecordRepository.saveAll(any(List.class))).thenReturn(null);
		
		List<MedicalRecord> medicalRecordResponse = medicalRecordService.updateMedicalRecord(updatedMedicalRecord);
		/*		
		assertTrue(medicalRecordResponse.get(0).getMedications().get(0).getMedication().toString() == "aznol 350mg");
		assertTrue(medicalRecordResponse.get(0).getMedications().size() ==2);
		assertTrue(medicalRecordResponse.get(0).getFirstName().toString() == medicalRecord.getFirstName());
		assertTrue(medicalRecordResponse.get(0).getAllergies() != null);
		
		verify(medicalRecordRepository, times(1)).saveAll(any(List.class));*/
		assertThrows(Exception.class, () -> {medicalRecordRepository.findMedicalRecordByFirstNameAndLastName(
				firstName, lastName);});
	}
	@Test
	public void testDeleteMedicalRecord() {
		
		String firstName = "firstName";
		String lastName = "lastName";
		List<MedicalRecord> medicalRecordToDeleteList = new ArrayList<>();
		
		when(medicalRecordRepository.findMedicalRecordByFirstNameAndLastName(firstName,lastName)).thenReturn(medicalRecordToDeleteList);
		doNothing().when(medicalRecordRepository).deleteAll(medicalRecordToDeleteList);
		
		medicalRecordService.deleteMedicalRecordByFirstNameAndLastName(firstName, lastName);
		
		
		
		verify(medicalRecordRepository, times(1)).deleteAll(medicalRecordToDeleteList);
	}
	
	@Test
	public void testDeleteMedicalRecordWithException() {
		
		String firstName = "firstName";
		String lastName = "lastName";
		List<MedicalRecord> medicalRecordToDeleteList = new ArrayList<>();
		
		when(medicalRecordRepository.findMedicalRecordByFirstNameAndLastName(firstName,lastName)).thenThrow(NullPointerException.class);
		//doNothing().when(medicalRecordRepository).deleteAll(medicalRecordToDeleteList);
		
		medicalRecordService.deleteMedicalRecordByFirstNameAndLastName(firstName, lastName);
		
		
		assertThrows(Exception.class, () -> {medicalRecordRepository.findMedicalRecordByFirstNameAndLastName(
				firstName, lastName);});
	}
	
	@Test
	public void testEquals() {
		
		final MedicalRecordServiceImpl service = new MedicalRecordServiceImpl();
			
				assertFalse(medicalRecordService.equals(service));
				assertFalse(medicalRecordService.toString().equals(service.toString()));
	}
	

}
