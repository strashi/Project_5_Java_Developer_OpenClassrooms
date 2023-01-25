package com.safetynet.alert.service.impl;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static final Logger logger = LoggerFactory.getLogger(DataInitializeServiceImpl.class);

	@Autowired
	private MedicalRecordRepository medicalRecordRepository;
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private FireStationRepository fireStationRepository;

	@Override
	public void readJsonFile() {
		logger.debug("traitement readJsonFile en cours chez DataInitializeServiceImpl");

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonFileReader jsonFileReader = objectMapper.readValue(new File("src/main/resources/json/data.json"),
					JsonFileReader.class);

			personRepository.saveAll(jsonFileReader.getPersons());
			fireStationRepository.saveAll(jsonFileReader.getFirestations());
			medicalRecordRepository.saveAll(jsonFileReader.getMedicalrecords());
			logger.info("traitement readJsonFile réussi chez DataInitializeServiceImpl!");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
