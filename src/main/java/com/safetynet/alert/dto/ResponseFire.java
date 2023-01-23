package com.safetynet.alert.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ResponseFire {
	//private List<Integer> station = new ArrayList<>();
	private List<Integer> station = new ArrayList<>();
	private List<PersonWithMedicalRecordDTO> residents = new ArrayList<>();
	
	

}
