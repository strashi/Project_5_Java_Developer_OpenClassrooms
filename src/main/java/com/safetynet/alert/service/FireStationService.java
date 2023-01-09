package com.safetynet.alert.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alert.model.FireStation;
import com.safetynet.alert.repository.FireStationRepository;

import lombok.Data;

@Data
@Service
public class FireStationService {
	
	@Autowired
	private FireStationRepository fireStationRepository;
	
	public FireStation saveFireStation(FireStation fireStation) {
		FireStation savedFireStation = fireStationRepository.save(fireStation);
		return savedFireStation;
	}
	
	public void deleteFireStation(FireStation fireStation) {
		fireStationRepository.delete(fireStation);
	}

}
