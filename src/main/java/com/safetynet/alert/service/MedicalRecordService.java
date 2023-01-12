package com.safetynet.alert.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alert.model.MedicalRecord;
import com.safetynet.alert.model.Person;
import com.safetynet.alert.repository.MedicalRecordRepository;

import lombok.Data;

@Data
@Service
public class MedicalRecordService {
	
	@Autowired
	private MedicalRecordRepository medicalRecordRepository;

	public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord) {
		MedicalRecord savedMedicalRecord = medicalRecordRepository.save(medicalRecord);
		return savedMedicalRecord;
	}
	
	public void deleteFireStation(MedicalRecord medicalRecord) {
		medicalRecordRepository.delete(medicalRecord);
	}
	
	public List<MedicalRecord> saveListMedicalRecords(List<MedicalRecord> list){
		return medicalRecordRepository.saveAll(list);
	}
}
