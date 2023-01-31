package com.safetynet.alert.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
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
	
	@ManyToMany(
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
					joinColumns = @JoinColumn(name = "medicalRecord_id"),
					inverseJoinColumns = @JoinColumn(name = "allergie_id")
			)
	List<Allergie> allergies = new ArrayList<>();
}
