package com.safetynet.alert.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ResponseChildAlert {

	private List<PersonDTO> childrenList = new ArrayList<>();
	
	private List<PersonDTO> adultList = new ArrayList<>();
}
