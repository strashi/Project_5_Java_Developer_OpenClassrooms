package com.safetynet.alert.dto;

import java.util.ArrayList;
import java.util.List;

import com.safetynet.alert.model.Allergie;
import com.safetynet.alert.model.Medication;

import lombok.Data;

@Data
public class MedicalRecordDTO {
	List<Medication> medications = new ArrayList<>();
		
	List<Allergie> allergies = new ArrayList<>();
}
