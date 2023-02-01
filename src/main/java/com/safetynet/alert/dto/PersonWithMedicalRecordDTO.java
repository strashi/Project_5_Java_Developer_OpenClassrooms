package com.safetynet.alert.dto;

import java.util.ArrayList;
import java.util.List;

import com.safetynet.alert.model.Allergie;
import com.safetynet.alert.model.Medication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
//@NoArgsConstructor
public class PersonWithMedicalRecordDTO {
	
	private String firstName;
	private String lastName;
	private String phone;
	private int age;
	
	List<Medication> medications = new ArrayList<>();
	
	List<Allergie> allergies = new ArrayList<>();

}
