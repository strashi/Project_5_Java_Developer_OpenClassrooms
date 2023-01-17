package com.safetynet.alert.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;

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
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date birthdate;
	

	@ManyToMany
	(
			fetch = FetchType.LAZY,
				cascade = { 
						CascadeType.PERSIST, 
						CascadeType.MERGE 
						}	
				)
				@JoinTable(
						name = "medicalRecord_medication",
						joinColumns = @JoinColumn(name = "medicalRecord_id"), 	
						inverseJoinColumns = @JoinColumn(name = "medication_id")
				)
		List<Medication> medications = new ArrayList<>();
	
	
	@ManyToMany(
			fetch = FetchType.LAZY,
			cascade = { 
					CascadeType.PERSIST, 
					CascadeType.MERGE 
					}	
			)
			@JoinTable(
					name = "medicalRecord_allergie",
					joinColumns = @JoinColumn(name = "medicalRecord_id")
				)
	List<Allergie> allergies = new ArrayList<>();
}
