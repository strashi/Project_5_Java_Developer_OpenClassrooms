package com.safetynet.alert.service.impl;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alert.model.MedicalRecord;
import com.safetynet.alert.model.Person;
import com.safetynet.alert.repository.MedicalRecordRepository;
import com.safetynet.alert.service.UtilService;

@Service
public class UtilServiceImpl implements UtilService{

	private static final Logger logger = LoggerFactory.getLogger(UtilServiceImpl.class);

	@Autowired
	private MedicalRecordRepository medicalRecordRepository;

	public int calculateAge(Date birthdate) {
		logger.debug("traitement calculateAge en cours chez UtilServiceImpl");
		try {

			LocalDate birthdateFormated = birthdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate now = LocalDate.now();
			Period period = Period.between(birthdateFormated, now);

			logger.debug("traitement calculateAge réussi chez UtilServiceImpl");
			return period.getYears();
		} catch (Exception e) {
			logger.error("traitement calculateAge échoué chez UtilServiceImpl", e);

			return -1;
		}
	}

	public int getAge(Person person) {
		logger.debug("traitement getAge en cours chez UtilServiceImpl");
		try {

			List<MedicalRecord> medicalRecords = medicalRecordRepository
					.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
			for (MedicalRecord medicalRecord : medicalRecords) {
				if (medicalRecord.getBirthdate() != null) {
					int response = calculateAge(medicalRecord.getBirthdate());
					logger.debug("traitement getAge réussi chez UtilServiceImpl");

					return response;
				}
			}
			return -1;
		} catch (Exception e) {
			logger.error("traitement getAge échoué chez UtilServiceImpl", e);

			return -1;
		}
	}

}
