package com.safetynet.alert.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alert.model.FireStation;
import com.safetynet.alert.model.Person;
import com.safetynet.alert.service.impl.FireStationServiceImpl;

@RestController
public class FireStationController {
	
	@Autowired
	private FireStationServiceImpl fireStationService;
	
	@PostMapping("/firestation")
	public FireStation addFireStation(@RequestBody FireStation fireStation) {
		return fireStationService.addFireStation(fireStation);
	}
	
	@PutMapping("/firestation")
	@ResponseBody
	public FireStation updateFireStation(@RequestParam String oldAddress, @RequestParam String newAddress) {
		return fireStationService.updateFireStation(oldAddress, newAddress);
	}
		
	@DeleteMapping("/firestation")
	@ResponseBody
	public void deleteFireStationByAddress(@RequestParam String address) {
		fireStationService.deleteFireStationByAddress(address);
	}
	
	@GetMapping("/firestation")
	@ResponseBody
	public Iterable<String> createListOfPersonsCoveredByOneFireStation(@RequestParam Integer stationNumber){
		return fireStationService.createListOfPersonsCoveredByOneFireStation(stationNumber);
	}
}
