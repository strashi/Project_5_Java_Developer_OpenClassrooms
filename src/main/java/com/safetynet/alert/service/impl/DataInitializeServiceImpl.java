package com.safetynet.alert.service.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alert.dto.JsonFileReader;
import com.safetynet.alert.repository.FireStationRepository;
import com.safetynet.alert.repository.MedicalRecordRepository;
import com.safetynet.alert.repository.PersonRepository;
import com.safetynet.alert.service.DataInitializeService;

@Service
public class DataInitializeServiceImpl implements DataInitializeService {
		
	@Autowired 
	private MedicalRecordRepository medicalRecordRepository;
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private FireStationRepository fireStationRepository;

	@Override
	public void readJsonFile() {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonFileReader jsonFileReader = objectMapper.readValue(new File("src/main/resources/json/data.json"),
					JsonFileReader.class);
			
			personRepository.saveAll(jsonFileReader.getPersons());
			fireStationRepository.saveAll(jsonFileReader.getFirestations());
			medicalRecordRepository.saveAll(jsonFileReader.getMedicalrecords());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
