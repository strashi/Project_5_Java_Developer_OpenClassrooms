package com.safetynet.alert.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alert.model.FireStation;
import com.safetynet.alert.repository.FireStationRepository;
import com.safetynet.alert.service.FireStationService;

import lombok.Data;

@Data
@Service
public class FireStationServiceImpl implements FireStationService{

	@Autowired
	private FireStationRepository fireStationRepository;
	
	@Autowired
	private Util util;

	public FireStation addFireStation(FireStation fireStation) {
		return fireStationRepository.save(fireStation);
	}
	
	public FireStation updateFireStation(String oldAddress, String newAddress) {
		FireStation newFireStation = fireStationRepository.getFireStationByAddress(oldAddress);
		newFireStation.setAddress(newAddress);
		return fireStationRepository.save(newFireStation);
	}

	public void deleteFireStationByAddress(String address) {
		FireStation fireStation = fireStationRepository.getFireStationByAddress(address);
		fireStationRepository.delete(fireStation);
	}
	
	@Override
	public List<String> coveredPersonsByFireStationWithChildrenAdultCount(Integer station) {
		
		int childrenNumber = 0, adultsNumber = 0;
		String separation = "=====================";
		
		List<List<Object>> metaAL = fireStationRepository.getDataFromPersonsAndBirthdate(station);
		ArrayList<String> newAL = new ArrayList<>();
		for( List<Object> listObject : metaAL) {
			for(int i = 0; i<6 ; i++) {
				String param = (listObject.get(i)).toString();
				newAL.add(param);
			}
			newAL.add(separation);
			
			Date birthdate = (Date) (listObject.get(6));
			
			String ageCategory = util.determineAgeCategory(util.calculateAge(birthdate));
			if(ageCategory.equals("enfant"))
				childrenNumber ++;
			if(ageCategory.equals("adulte"))
				adultsNumber ++;
		}
		
		String counterChildren = "Nombre d'enfants (<= 18 ans) : " + childrenNumber; 
		newAL.add(counterChildren);
		String counterAdults =   "Nombre d'adultes             : "+ adultsNumber;
		newAL.add(counterAdults);
		
		return newAL;
	
	}

	@Override
	public List<String> phoneAlert(Integer station) {
		return fireStationRepository.phoneAlert(station);
	}
	/*
	public FireStation getFireStationByAddress(String address) {
		return fireStationRepository.getFireStationByAddress(address);
	}*/
	/*
	public Iterable<String> createListOfPersonsCoveredByOneFireStation(Integer station){
		return fireStationRepository.createListOfPersonsCoveredByOneFireStation(station);
	}
	*//*
	public List<List<Object>> getDataAndBirthdate(Integer station){
		return fireStationRepository.getDataAndBirthdate(station);
	}*/
	/*
	public List<FireStation> saveListFireStations(List<FireStation> list) {
		return fireStationRepository.saveAll(list);
	}*/
}
