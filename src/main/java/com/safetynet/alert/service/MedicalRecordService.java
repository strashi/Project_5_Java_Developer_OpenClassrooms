package com.safetynet.alert.service;


import java.util.List;

import com.safetynet.alert.model.MedicalRecord;

public interface MedicalRecordService {
	public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord);
	
	public List<MedicalRecord> updateMedicalRecord(MedicalRecord updatedMedicalRecord);
	
	public void deleteMedicalRecordByFirstNameAndLastName(String firstName, String lastName);
		
}
