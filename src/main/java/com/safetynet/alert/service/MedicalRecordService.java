package com.safetynet.alert.service;

import java.util.List;
import java.util.Optional;

import com.safetynet.alert.model.MedicalRecord;

public interface MedicalRecordService {
	public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord);
	
	public List<MedicalRecord> saveListMedicalRecords(List<MedicalRecord> list);
	
	public Optional<MedicalRecord> getMedicalRecord(final Long id) ;

	public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord); 

	public MedicalRecord updateMedicalRecord(MedicalRecord updatedMedicalRecord);
	
	public MedicalRecord findMedicalRecordByFirstNameAndLastName(String firstName, String lastName);;

	public void deleteMedicalRecordByFirstNameAndLastName(String firstName, String lastName);
}
