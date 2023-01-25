package com.safetynet.alert.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.safetynet.alert.model.Person;


public interface PersonRepository extends JpaRepository<Person, Long>{
	
	public void deletePersonByFirstNameAndLastName(String firstName,String lastName);
	
	public Person findPersonByFirstNameAndLastName(String firstName, String lastName);
	
	@Query(value="SELECT email FROM persons WHERE city = :city", nativeQuery = true)
	public Iterable<String> listOfEmailByCity(@Param("city") String city);
	
	public List<Person> findByAddress(String address);

	public List<Person> findAllByFirstNameAndLastName(String firstName, String lastName);
	
}
