package com.safetynet.alert.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "medicalrecords")
public class MedicalRecord {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstName;
	private String lastName;
	private String birthdate;
	

	@OneToMany(
			   cascade = CascadeType.ALL, 
			   orphanRemoval = true, 
			   fetch = FetchType.EAGER)
				@JoinColumn(name = "medicalRecord_id")
	List<Medication> medications;
	
	
	@OneToMany(
			   cascade = CascadeType.ALL, 
			   orphanRemoval = true, 
			   fetch = FetchType.EAGER)
				@JoinColumn(name = "medicalRecord_id")
	List<Allergie> allergies;
}
