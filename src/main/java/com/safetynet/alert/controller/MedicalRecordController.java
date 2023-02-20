package com.safetynet.alert.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger logger = LoggerFactory.getLogger(MedicalRecordController.class);

	@Autowired
	private MedicalRecordService medicalRecordService;

	@PostMapping("/medicalRecord")
	public MedicalRecord addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
		logger.debug("requête addMedicalRecord envoyée de MedicalRecordController");
		try {
			MedicalRecord response = medicalRecordService.addMedicalRecord(medicalRecord);
			logger.info("requête addMedicalRecord réussie chez MedicalRecordController!");
			return response;
		} catch (Exception e) {
			logger.error("marche pas :(", e);
			return null;
		}
	}

	@PutMapping("/medicalRecord")
	public List<MedicalRecord> updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
		logger.debug("requête updateMedicalRecord envoyée de MedicalRecordController");
		try {
			List<MedicalRecord> response = medicalRecordService.updateMedicalRecord(medicalRecord);
			logger.info("requête updateMedicalRecord réussie chez MedicalRecordController!");
			return response;
		} catch (Exception e) {
			logger.error("marche pas :(", e);
			return null;
		}
	}

	@DeleteMapping("/medicalRecord")
	public void deleteMedicalRecordByFirstNameLastName(@RequestParam String firstName, @RequestParam String lastName) {
		logger.debug("requête deleteMedicalRecord envoyée de MedicalRecordController");
		try {
			medicalRecordService.deleteMedicalRecordByFirstNameAndLastName(firstName, lastName);
			logger.info("requête deleteMedicalRecord réussie chez MedicalRecordController!");
		} catch (Exception e) {
			logger.error("marche pas :(", e);
		}
	}

}
