package com.safetynet.alert.model;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "persons")
public class Person {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	private String address;
	private String city;
	private int zip;
	private String phone;
	private String email;

}
