package com.safetynet.alert.repository;

import java.util.Date;
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
	
	@Query(value="SELECT p.first_name, p.last_name, m.birthdate FROM persons p "
			+ "JOIN medicalrecords m "
			+ "ON p.first_name = m.first_name "
			+ "AND p.last_name = m.last_name "
			+ "WHERE p.address =:address", nativeQuery = true)
	public List<List<Object>> getPersonsFromAddressWithBirthdate(@Param("address") String address);

	public List<Person> findByAddress(String address);

	//public List<Person> findByAdress(String address);

	public Date findByFirstNameAndLastName(String firstName, String lastName);

	public List<Person> findAllByFirstNameAndLastName(String firstName, String lastName);
}
