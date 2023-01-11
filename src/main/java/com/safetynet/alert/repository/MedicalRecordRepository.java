package com.safetynet.alert.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.safetynet.alert.model.MedicalRecord;


public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long>{
	
	

}
