package com.safetynet.alert.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ResponseFlood {
	
	private List<ResidentsByStation> residentsByStation = new ArrayList<>();
}
