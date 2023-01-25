package com.safetynet.alert.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alert.model.MedicalRecord;
import com.safetynet.alert.repository.MedicalRecordRepository;
import com.safetynet.alert.service.MedicalRecordService;

import lombok.Data;

@Data
@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

	private static final Logger logger = LoggerFactory.getLogger(MedicalRecordServiceImpl.class);

	@Autowired
	private MedicalRecordRepository medicalRecordRepository;

	public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
		logger.debug("traitement addMedicalRecord en cours chez MedicalRecordServiceImpl");
		try {
			logger.info("traitement addMedicalRecord réussi chez MedicalRecordServiceImpl!");
			return medicalRecordRepository.save(medicalRecord);
		} catch (Exception e) {
			logger.error("marche pas :(", e);
			return null;
		}
	}

	public MedicalRecord updateMedicalRecord(MedicalRecord updatedMedicalRecord) {
		logger.debug("traitement addMedicalRecord en cours chez MedicalRecordServiceImpl");
		try {
			MedicalRecord newMedicalRecord = medicalRecordRepository.findMedicalRecordByFirstNameAndLastName(
					updatedMedicalRecord.getFirstName(), updatedMedicalRecord.getLastName());
			newMedicalRecord.setBirthdate(updatedMedicalRecord.getBirthdate());
			newMedicalRecord.setMedications(updatedMedicalRecord.getMedications());
			newMedicalRecord.setAllergies(updatedMedicalRecord.getAllergies());

			logger.info("traitement addMedicalRecord réussi chez MedicalRecordServiceImpl!");
			return medicalRecordRepository.save(newMedicalRecord);
			
		} catch (Exception e) {
			logger.error("marche pas :(", e);
			return null;
		}

	}

	public void deleteMedicalRecordByFirstNameAndLastName(String firstName, String lastName) {
		logger.debug("traitement deleteMedicalRecord en cours chez MedicalRecordServiceImpl");
		try {
			MedicalRecord medicalRecordToDelete = medicalRecordRepository.findMedicalRecordByFirstNameAndLastName(firstName,
					lastName);
			medicalRecordRepository.delete(medicalRecordToDelete);
		
			logger.info("traitement deleteMedicalRecord réussi chez MedicalRecordServiceImpl!");
		} catch (Exception e) {
			logger.error("marche pas :(", e);

		}
		

	}

}
