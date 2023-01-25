package com.safetynet.alert.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alert.model.MedicalRecord;
import com.safetynet.alert.service.MedicalRecordService;

@RestController
public class MedicalRecordController {

	@Autowired
	private MedicalRecordService medicalRecordService;

	@PostMapping("/medicalRecord")
	public MedicalRecord addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
		return medicalRecordService.addMedicalRecord(medicalRecord);
	}

	@PutMapping("/medicalRecord")
	public MedicalRecord updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
		return medicalRecordService.updateMedicalRecord(medicalRecord);
	}

	@DeleteMapping("/medicalRecord")
	public void deleteMedicalRecordByFirstNameLastName(@RequestParam String firstName, @RequestParam String lastName) {
		medicalRecordService.deleteMedicalRecordByFirstNameAndLastName(firstName, lastName);
	}

}
