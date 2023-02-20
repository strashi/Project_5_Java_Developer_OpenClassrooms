package com.safetynet.alert.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alert.dto.ResponseFire;
import com.safetynet.alert.dto.ResponseFlood;
import com.safetynet.alert.dto.ResponsePersonByFireStation;
import com.safetynet.alert.model.FireStation;
import com.safetynet.alert.service.FireStationService;

@RestController
public class FireStationController {

	private static final Logger logger = LoggerFactory.getLogger(FireStationController.class);

	@Autowired
	private FireStationService fireStationService;

	@PostMapping("/firestation")
	public FireStation addFireStation(@RequestBody FireStation fireStation) {
		logger.debug("requête addFireStation envoyée de FireStationController");
		try {
			FireStation response = fireStationService.addFireStation(fireStation);
			logger.info("requête addFireStation réussie chez FireStationController!");
			return response;
		} catch (Exception e) {
			logger.error("marche pas :(", e);
			return null;
		}
	}

	@PutMapping("/firestation")
	public List<FireStation> updateFireStation(@RequestParam String address, @RequestParam List<Integer> stations) {
		logger.debug("requête updateFireStation envoyée de FireStationController");
		try {
			List<FireStation> response = fireStationService.updateFireStation(address, stations);
			logger.info("requête updateFireStation réussie chez FireStationController!");
			return response;
		} catch (Exception e) {
			logger.error("marche pas :(", e);
			return null;
		}
	}

	@DeleteMapping("/firestation")
	public void deleteFireStation(@RequestBody FireStation firestation) {
		logger.debug("requête deleteFireStation envoyée de FireStationController");
		try {
			fireStationService.deleteFireStation(firestation);
			logger.info("requête deleteFireStation réussie chez FireStationController!");
		} catch (Exception e) {
			logger.error("marche pas :(", e);
		}

	}

	@GetMapping("/firestation")
	public ResponsePersonByFireStation coveredPersonsByFireStationWithChildrenAdultCount(
			@RequestParam Integer stationNumber) {
		logger.debug("requête coveredPersonsByFireStation envoyée de FireStationController");
		try {
			ResponsePersonByFireStation response = fireStationService
					.coveredPersonsByFireStationWithChildrenAdultCount(stationNumber);
			logger.info("requête coveredPersonsByFireStation réussie chez FireStationController!");
			return response;
		} catch (Exception e) {
			logger.error("marche pas :(", e);
			return null;
		}
	}

	@GetMapping("/phoneAlert")
	public List<String> phoneAlert(@RequestParam Integer firestation) {
		logger.debug("requête phoneAlert envoyée de FireStationController");
		try {
			List<String> response = fireStationService.phoneAlert(firestation);
			logger.info("requête phoneAlert réussie chez FireStationController!");
			return response;
		} catch (Exception e) {
			logger.error("marche pas :(", e);
			return null;
		}
	}

	@GetMapping("/fire")
	public ResponseFire fire(@RequestParam String address) {
		logger.debug("requête fire envoyée de FireStationController");
		try {
			ResponseFire response = fireStationService.fire(address);
			logger.info("requête fire réussie chez FireStationController!");
			return response;
		} catch (Exception e) {
			logger.error("marche pas :(", e);
			return null;
		}
	}

	@GetMapping("/flood")
	public ResponseFlood flood(@RequestParam List<Integer> list_of_station_number) {
		logger.debug("requête flood envoyée de FireStationController");
		try {
			ResponseFlood response = fireStationService.flood(list_of_station_number);
			logger.info("requête flood réussie chez FireStationController!");
			return response;
		} catch (Exception e) {
			logger.error("marche pas :(", e);
			return null;
		}

	}

}
