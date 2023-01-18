package com.safetynet.alert.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.safetynet.alert.model.FireStation;
import com.safetynet.alert.model.Person;


public interface FireStationRepository extends JpaRepository<FireStation, Long>{
	
	public FireStation getFireStationByAddress(String Address);
	
	@Query(value="SELECT persons.first_name, persons.last_name, persons.address,persons.zip, persons.city, persons.phone"
			+ " FROM firestations, persons "
			+ "WHERE firestations.station = :station "
			+ "AND firestations.address = persons.address", nativeQuery = true)
	public Iterable<String> createListOfPersonsCoveredByOneFireStation(@Param("station") Integer station);
	//(value="SELECT firestations.address, firestations.station, persons.* FROM firestations, persons WHERE firestations.station = :station AND firestations.address = persons.address", nativeQuery = true)
}
