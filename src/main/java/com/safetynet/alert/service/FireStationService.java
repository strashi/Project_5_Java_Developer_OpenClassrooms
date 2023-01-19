package com.safetynet.alert.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.safetynet.alert.model.FireStation;
import com.safetynet.alert.model.Person;

public interface FireStationService {
	public FireStation addFireStation(FireStation fireStation);
	
	public FireStation updateFireStation(String oldAddress, String newAddress);

	public void deleteFireStationByAddress(String address);

	public List<String> coveredPersonsByFireStationWithChildrenAdultCount(Integer station);

	public List<String> phoneAlert(Integer station);

	// public List<FireStation> saveListFireStations(List<FireStation> list);
	// public FireStation getFireStationByAddress(String Address);
	// public Iterable<String> createListOfPersonsCoveredByOneFireStation(Integer station);
	// public List<List<Object>> getDataAndBirthdate(Integer station);
}
