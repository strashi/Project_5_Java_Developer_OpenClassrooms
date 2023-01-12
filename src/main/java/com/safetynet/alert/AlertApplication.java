package com.safetynet.alert;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alert.service.FireStationService;
import com.safetynet.alert.service.MedicalRecordService;
import com.safetynet.alert.service.PersonService;

import jakarta.transaction.Transactional;


@SpringBootApplication
public class AlertApplication implements CommandLineRunner{
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private FireStationService fireStationService;
	@Autowired
	private MedicalRecordService medicalRecordService;
	
	
	public static void main(String[] args) {
		SpringApplication.run(AlertApplication.class, args);
	}
	
	@Transactional
	@Override
	public void run(String... args) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		//objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		JsonFileReader jsonFileReader = objectMapper.readValue(new File("src/main/resources/json/data.json"), JsonFileReader.class);
		personService.saveListPersons(jsonFileReader.persons);
		fireStationService.saveListFireStations(jsonFileReader.firestations);
		medicalRecordService.saveListMedicalRecords(jsonFileReader.medicalrecords);
	

	}

	

}
