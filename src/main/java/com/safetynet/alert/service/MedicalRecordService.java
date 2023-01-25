package com.safetynet.alert.service;


import com.safetynet.alert.model.MedicalRecord;

public interface MedicalRecordService {
	public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord);
	
	public MedicalRecord updateMedicalRecord(MedicalRecord updatedMedicalRecord);
	
	public void deleteMedicalRecordByFirstNameAndLastName(String firstName, String lastName);
		
}
