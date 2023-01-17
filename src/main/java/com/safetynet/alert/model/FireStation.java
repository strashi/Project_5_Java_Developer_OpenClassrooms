package com.safetynet.alert.model;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "firestations")
public class FireStation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String address;
	private int station;
}
