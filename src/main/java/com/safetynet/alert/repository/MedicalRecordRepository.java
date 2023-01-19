package com.safetynet.alert.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.safetynet.alert.model.MedicalRecord;


public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long>{
	
	public MedicalRecord findMedicalRecordByFirstNameAndLastName(String firstName, String lastName);

	//public void deleteMedicalRecordByFirstNameAndLastName(String firstName, String lastName);
}
