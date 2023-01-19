package com.safetynet.alert.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.safetynet.alert.model.FireStation;


public interface FireStationRepository extends JpaRepository<FireStation, Long>{
	
	public FireStation getFireStationByAddress(String Address);
	/*
	@Query(value="SELECT p.first_name, p.last_name, p.address,p.zip, p.city, p.phone, m.birthdate"
			+ " FROM persons p "
			+ "JOIN firestations f "
			+ "JOIN medicalrecords m "
			+ "WHERE f.station = :station "
			+ "AND f.address = p.address "
			+ "AND p.first_name = m.first_name "
			+ "AND p.last_name = m.last_name", nativeQuery = true)
	public Iterable<String> createListOfPersonsCoveredByOneFireStation(@Param("station") Integer station);
	*/
	@Query(value="SELECT p.first_name, p.last_name, p.address,p.zip, p.city, p.phone, m.birthdate"
			+ " FROM persons p "
			+ "JOIN firestations f "
			+ "JOIN medicalrecords m "
			+ "WHERE f.station = :station "
			+ "AND f.address = p.address "
			+ "AND p.first_name = m.first_name "
			+ "AND p.last_name = m.last_name", nativeQuery = true)
	public List<List<Object>> getDataAndBirthdate(@Param("station") Integer station);
	

}

