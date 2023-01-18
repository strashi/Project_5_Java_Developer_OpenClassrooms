package com.safetynet.alert.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.safetynet.alert.model.FireStation;
import com.safetynet.alert.model.Person;
import com.safetynet.alert.repository.FireStationRepository;
import com.safetynet.alert.service.FireStationService;

import lombok.Data;

@Data
@Service
public class FireStationServiceImpl implements FireStationService{

	@Autowired
	private FireStationRepository fireStationRepository;

	public FireStation addFireStation(FireStation fireStation) {
		return fireStationRepository.save(fireStation);
	}

	public void deleteFireStationByAddress(String address) {
		FireStation fireStation = this.getFireStationByAddress(address);
		fireStationRepository.delete(fireStation);
	}

	public List<FireStation> saveListFireStations(List<FireStation> list) {
		return fireStationRepository.saveAll(list);
	}

	public FireStation getFireStationByAddress(String Address) {
		return fireStationRepository.getFireStationByAddress(Address);
	}

	public FireStation updateFireStation(String oldAddress, String newAddress) {
		FireStation newFireStation = this.getFireStationByAddress(oldAddress);
		newFireStation.setAddress(newAddress);
		return fireStationRepository.save(newFireStation);
	}
	
	public Iterable<String> createListOfPersonsCoveredByOneFireStation(Integer station){
		return fireStationRepository.createListOfPersonsCoveredByOneFireStation(station);
	}
}
