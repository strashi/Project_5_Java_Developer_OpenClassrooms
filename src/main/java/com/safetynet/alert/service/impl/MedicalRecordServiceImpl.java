package com.safetynet.alert.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alert.model.MedicalRecord;
import com.safetynet.alert.model.Person;
import com.safetynet.alert.repository.MedicalRecordRepository;

import lombok.Data;

@Data
@Service
public class MedicalRecordServiceImpl {
	
	@Autowired
	private MedicalRecordRepository medicalRecordRepository;

	public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord) {
		return medicalRecordRepository.save(medicalRecord);
	}
	
	public List<MedicalRecord> saveListMedicalRecords(List<MedicalRecord> list){
		return medicalRecordRepository.saveAll(list);
	}
	
	public Optional<MedicalRecord> getMedicalRecord(final Long id) {
		return medicalRecordRepository.findById(id);
	}

	public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
		return medicalRecordRepository.save(medicalRecord);
	}

	public MedicalRecord updateMedicalRecord(MedicalRecord updatedMedicalRecord) {
		MedicalRecord newMedicalRecord = this.findMedicalRecordByFirstNameAndLastName(updatedMedicalRecord.getFirstName(), updatedMedicalRecord.getLastName());
		newMedicalRecord.setBirthdate(updatedMedicalRecord.getBirthdate());
		newMedicalRecord.setMedications(updatedMedicalRecord.getMedications());
		newMedicalRecord.setAllergies(updatedMedicalRecord.getAllergies());
		return medicalRecordRepository.save(newMedicalRecord);
	}
	
	public MedicalRecord findMedicalRecordByFirstNameAndLastName(String firstName, String lastName) {
		return medicalRecordRepository.findMedicalRecordByFirstNameAndLastName(firstName, lastName);
	}

	public void deleteMedicalRecordByFirstNameAndLastName(String firstName, String lastName) {
		MedicalRecord medicalRecordToDelete = this.findMedicalRecordByFirstNameAndLastName(firstName, lastName);
		medicalRecordRepository.delete(medicalRecordToDelete);
	}
}
