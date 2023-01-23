package com.safetynet.alert.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ResponsePersonByFireStation {
	private List<PersonDTO> persons = new ArrayList<>();
	private int numberOfChildren;
	private int numberOfAdults;
}
