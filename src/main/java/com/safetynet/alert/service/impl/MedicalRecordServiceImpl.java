package com.safetynet.alert.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alert.model.MedicalRecord;
import com.safetynet.alert.repository.MedicalRecordRepository;
import com.safetynet.alert.service.MedicalRecordService;

import lombok.Data;

@Data
@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

	@Autowired
	private MedicalRecordRepository medicalRecordRepository;

	public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
		return medicalRecordRepository.save(medicalRecord);
	}

	public MedicalRecord updateMedicalRecord(MedicalRecord updatedMedicalRecord) {
		MedicalRecord newMedicalRecord = medicalRecordRepository.findMedicalRecordByFirstNameAndLastName(
				updatedMedicalRecord.getFirstName(), updatedMedicalRecord.getLastName());
		newMedicalRecord.setBirthdate(updatedMedicalRecord.getBirthdate());
		newMedicalRecord.setMedications(updatedMedicalRecord.getMedications());
		newMedicalRecord.setAllergies(updatedMedicalRecord.getAllergies());
		return medicalRecordRepository.save(newMedicalRecord);
	}

	public void deleteMedicalRecordByFirstNameAndLastName(String firstName, String lastName) {
		MedicalRecord medicalRecordToDelete = medicalRecordRepository.findMedicalRecordByFirstNameAndLastName(firstName,
				lastName);
		medicalRecordRepository.delete(medicalRecordToDelete);

	}

}
