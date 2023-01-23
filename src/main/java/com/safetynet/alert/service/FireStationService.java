package com.safetynet.alert.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.safetynet.alert.dto.ResponseFire;
import com.safetynet.alert.dto.ResponseFlood;
import com.safetynet.alert.dto.ResponsePersonByFireStation;
import com.safetynet.alert.model.FireStation;

public interface FireStationService {
	public FireStation addFireStation(FireStation fireStation);
	
	public FireStation updateFireStation(String address, int station);

	public void deleteFireStationByAddress(String address);

	//public List<String> coveredPersonsByFireStationWithChildrenAdultCount(Integer station);
	
	public ResponsePersonByFireStation coveredPersonsByFireStationWithChildrenAdultCount(Integer station);

	public List<String> phoneAlert(Integer station);
	
	public ResponseFire fire(@RequestParam String address);
	
	public int numeroCaserne(String address);
	
	public ResponseFlood flood(List<Integer> numbersOfStations);
	

	// public List<FireStation> saveListFireStations(List<FireStation> list);
	// public FireStation getFireStationByAddress(String Address);
	// public Iterable<String> createListOfPersonsCoveredByOneFireStation(Integer station);
	// public List<List<Object>> getDataAndBirthdate(Integer station);
}
