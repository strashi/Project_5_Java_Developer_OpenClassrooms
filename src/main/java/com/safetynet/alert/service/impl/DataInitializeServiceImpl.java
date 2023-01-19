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
	/*
	@Autowired
	private PersonServiceImpl personService;
	@Autowired
	private FireStationServiceImpl fireStationService;
	@Autowired
	private MedicalRecordServiceImpl medicalRecordService;*/
	
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
			//personService.saveListPersons(jsonFileReader.getPersons());
			personRepository.saveAll(jsonFileReader.getPersons());
			//fireStationService.saveListFireStations(jsonFileReader.getFirestations());
			fireStationRepository.saveAll(jsonFileReader.getFirestations());
			//medicalRecordService.saveListMedicalRecords(jsonFileReader.getMedicalrecords());
			medicalRecordRepository.saveAll(jsonFileReader.getMedicalrecords());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
