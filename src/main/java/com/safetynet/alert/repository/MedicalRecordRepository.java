package com.safetynet.alert.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.safetynet.alert.model.MedicalRecord;
import com.safetynet.alert.model.Person;


public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long>{
	
	public MedicalRecord findMedicalRecordByFirstNameAndLastName(String firstName, String lastName);

	

	public List<MedicalRecord> findByFirstNameAndLastName(String firstName, String lastName);

	//public List<MedicalRecord> findByFirstNameAndLastName(Person person);

	//public void deleteMedicalRecordByFirstNameAndLastName(String firstName, String lastName);
}
