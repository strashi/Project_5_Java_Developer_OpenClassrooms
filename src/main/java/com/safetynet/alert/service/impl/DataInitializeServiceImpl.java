package com.safetynet.alert.service.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alert.dto.JsonFileReader;
import com.safetynet.alert.service.DataInitializeService;

public class DataInitializeServiceImpl implements DataInitializeService {

	@Autowired
	private PersonServiceImpl personService;
	@Autowired
	private FireStationServiceImpl fireStationService;
	@Autowired
	private MedicalRecordServiceImpl medicalRecordService;

	@Override
	public void readJsonFile() {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonFileReader jsonFileReader = objectMapper.readValue(new File("src/main/resources/json/data.json"),
					JsonFileReader.class);
			personService.saveListPersons(jsonFileReader.getPersons());
			fireStationService.saveListFireStations(jsonFileReader.getFirestations());
			medicalRecordService.saveListMedicalRecords(jsonFileReader.getMedicalrecords());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
