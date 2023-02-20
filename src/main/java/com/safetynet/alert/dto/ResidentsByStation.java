package com.safetynet.alert.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ResidentsByStation {
	
	private String stationNumber;
	private List<Address> addressesServedByFireStation = new ArrayList<>();
		
}
