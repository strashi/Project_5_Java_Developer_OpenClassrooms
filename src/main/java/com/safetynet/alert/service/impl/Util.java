package com.safetynet.alert.service.impl;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alert.model.MedicalRecord;
import com.safetynet.alert.model.Person;
import com.safetynet.alert.repository.MedicalRecordRepository;

@Service
public class Util {
	
	@Autowired
	private MedicalRecordRepository medicalRecordRepository;

	String determineAgeCategory(int age) {
		if(age < 19)
			return "enfant";
		else
			return "adulte";
	}

	int calculateAge(Date birthdate) {
		 LocalDate birthdateFormated = birthdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		 LocalDate now = LocalDate.now();
		 Period period = Period.between(birthdateFormated, now);
		 
		return period.getYears();
	}
	
	int getAge(Person person){
		List<MedicalRecord> medicalRecords = medicalRecordRepository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
		for (MedicalRecord medicalRecord : medicalRecords){
			if(medicalRecord.getBirthdate() != null){
				return calculateAge(medicalRecord.getBirthdate());
			}
		}
		return -1;
	}

}
