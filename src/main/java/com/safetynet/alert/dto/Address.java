package com.safetynet.alert.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Address {
	
	private String address;
	private List<PersonWithMedicalRecordDTO> listOfPersonsWithMedicalRecordDTO = new ArrayList<>();
}
