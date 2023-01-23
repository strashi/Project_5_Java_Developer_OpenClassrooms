package com.safetynet.alert.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alert.dto.ResponseFire;
import com.safetynet.alert.dto.ResponseFlood;
import com.safetynet.alert.dto.ResponsePersonByFireStation;
import com.safetynet.alert.model.FireStation;
import com.safetynet.alert.service.FireStationService;

@RestController
public class FireStationController {
	
	@Autowired
	private FireStationService fireStationService;
	
	@PostMapping("/firestation")
	public FireStation addFireStation(@RequestBody FireStation fireStation) {
		return fireStationService.addFireStation(fireStation);
	}
	
	@PutMapping("/firestation")
	@ResponseBody
	public FireStation updateFireStation(@RequestParam String address, @RequestParam int station) {
		return fireStationService.updateFireStation(address, station);
	}
	
	@DeleteMapping("/firestation")
	@ResponseBody
	public void deleteFireStationByAddress(@RequestParam String address) {
		fireStationService.deleteFireStationByAddress(address);
	}
		
	@GetMapping("/firestation")
	@ResponseBody
	public ResponsePersonByFireStation coveredPersonsByFireStationWithChildrenAdultCount(@RequestParam Integer stationNumber){
		return fireStationService.coveredPersonsByFireStationWithChildrenAdultCount(stationNumber);
	}
	
	@GetMapping("/phoneAlert")
	@ResponseBody
	public List<String> phoneAlert(@RequestParam Integer firestation){
		return fireStationService.phoneAlert(firestation);
	}
	
	@GetMapping("/fire")
	@ResponseBody
	public ResponseFire fire(@RequestParam String address){
		return fireStationService.fire(address);
	}
	
	@GetMapping("/caserne")
	@ResponseBody
	public int numeroCaserne(String address){
		return fireStationService.numeroCaserne(address);
	}
	
	@GetMapping("/flood")
	@ResponseBody
	public ResponseFlood flood(@RequestParam List<Integer> list_of_station_number) {
		return fireStationService.flood(list_of_station_number);
	
	}
	
	/*
	@GetMapping("/firestation")
	@ResponseBody
	public Iterable<String> createListOfPersonsCoveredByOneFireStation(@RequestParam Integer stationNumber){
		return fireStationService.createListOfPersonsCoveredByOneFireStation(stationNumber);
	}*/
}
