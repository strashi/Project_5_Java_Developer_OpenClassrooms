package com.safetynet.alert.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.safetynet.alert.model.MedicalRecord;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

	public List<MedicalRecord> findByFirstNameAndLastName(String firstName, String lastName);

}
