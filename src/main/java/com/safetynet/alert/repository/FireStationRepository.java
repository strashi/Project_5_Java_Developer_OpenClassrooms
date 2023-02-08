package com.safetynet.alert.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.safetynet.alert.model.FireStation;


public interface FireStationRepository extends JpaRepository<FireStation, Long>{
	
	@Query(value="SELECT p.phone "
			+ "FROM persons p "
			+ "JOIN firestations f "
			+ "WHERE f.station= :station "
			+ "AND p.address = f.address", nativeQuery = true)
	public List<String> phoneAlert(@Param("station") Integer station);

	public List<FireStation> findByStation(Integer station);

	public List<FireStation> findByAddress(String address);

}

