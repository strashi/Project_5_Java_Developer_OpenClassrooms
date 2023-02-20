package com.safetynet.alert.service.impl;

import java.util.ArrayList;
import java.util.List;

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
			MedicalRecord response = medicalRecordRepository.save(medicalRecord);
			logger.info("traitement addMedicalRecord réussi chez MedicalRecordServiceImpl!");
			return response;
		} catch (Exception e) {
			logger.error("marche pas :(", e);
			return null;
		}
	}

	public List<MedicalRecord> updateMedicalRecord(MedicalRecord updatedMedicalRecord) {
		logger.debug("traitement addMedicalRecord en cours chez MedicalRecordServiceImpl");
		List<MedicalRecord> updatedMedicalRecordList = new ArrayList<>();
		try {
			List<MedicalRecord> newMedicalRecordList = medicalRecordRepository.findByFirstNameAndLastName(
					updatedMedicalRecord.getFirstName(), updatedMedicalRecord.getLastName());
			for(MedicalRecord newMedicalRecord : newMedicalRecordList) {
				newMedicalRecord.setBirthdate(updatedMedicalRecord.getBirthdate());
				newMedicalRecord.setMedications(updatedMedicalRecord.getMedications());
				newMedicalRecord.setAllergies(updatedMedicalRecord.getAllergies());
				updatedMedicalRecordList.add(newMedicalRecord);
			}
			 List<MedicalRecord> response = medicalRecordRepository.saveAll(updatedMedicalRecordList);
			 response = (response != null) ? response : updatedMedicalRecordList;
			logger.info("traitement addMedicalRecord réussi chez MedicalRecordServiceImpl!");
			return response;
			
		} catch (Exception e) {
			logger.error("marche pas :(", e);
			return null;
		}

	}

	public void deleteMedicalRecordByFirstNameAndLastName(String firstName, String lastName) {
		logger.debug("traitement deleteMedicalRecord en cours chez MedicalRecordServiceImpl");
		try {
			List<MedicalRecord> medicalRecordToDeleteList = medicalRecordRepository.findByFirstNameAndLastName(firstName,
					lastName);
			medicalRecordRepository.deleteAll(medicalRecordToDeleteList);
		
			logger.info("traitement deleteMedicalRecord réussi chez MedicalRecordServiceImpl!");
		} catch (Exception e) {
			logger.error("marche pas :(", e);

		}
		

	}

}
