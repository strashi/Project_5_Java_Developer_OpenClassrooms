package com.safetynet.alert.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alert.repository.FireStationRepository;
import com.safetynet.alert.repository.MedicalRecordRepository;
import com.safetynet.alert.repository.PersonRepository;
import com.safetynet.alert.service.impl.DataInitializeServiceImpl;

@ExtendWith(MockitoExtension.class)
public class DataInitializeServiceTests {
	
	@InjectMocks
	private DataInitializeServiceImpl dataInitializeService;
	
	@Mock
	private MedicalRecordRepository medicalRecordRepository;
	@Mock
	private PersonRepository personRepository;
	@Mock
	private FireStationRepository fireStationRepository;
	
	
	@Test
	public void testReadJsonFile() {
		when(personRepository.saveAll(any(Iterable.class))).thenReturn(null);
		when(fireStationRepository.saveAll(any(Iterable.class))).thenReturn(null);
		when(medicalRecordRepository.saveAll(any(Iterable.class))).thenReturn(null);
				
		dataInitializeService.readJsonFile();
		
		verify(personRepository, times(1)).saveAll(any(Iterable.class));
		verify(fireStationRepository, times(1)).saveAll(any(Iterable.class));
		verify(medicalRecordRepository, times(1)).saveAll(any(Iterable.class));
	}
	
	@Test
	public void testReadJsonFileWithException() {
		when(personRepository.saveAll(any(Iterable.class))).thenThrow(NullPointerException.class);
					
		dataInitializeService.readJsonFile();
		
		verify(personRepository, times(1)).saveAll(any(Iterable.class));
	
	}

}
