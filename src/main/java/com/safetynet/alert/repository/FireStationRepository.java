package com.safetynet.alert.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.safetynet.alert.model.FireStation;


public interface FireStationRepository extends JpaRepository<FireStation, Long>{
	
	public FireStation getFireStationByAddress(String Address);

}
