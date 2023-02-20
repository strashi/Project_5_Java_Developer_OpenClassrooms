package com.safetynet.alert.service;

import java.util.List;

import com.safetynet.alert.dto.ResponseFire;
import com.safetynet.alert.dto.ResponseFlood;
import com.safetynet.alert.dto.ResponsePersonByFireStation;
import com.safetynet.alert.model.FireStation;

public interface FireStationService {
	
	public FireStation addFireStation(FireStation fireStation);
	public List<FireStation> updateFireStation(String address, List<Integer> stations);
	public ResponsePersonByFireStation coveredPersonsByFireStationWithChildrenAdultCount(Integer station);
	public List<String> phoneAlert(Integer station);
	public ResponseFire fire(String address);
	public ResponseFlood flood(List<Integer> numbersOfStations);
	public void deleteFireStation(FireStation firestation);

}
