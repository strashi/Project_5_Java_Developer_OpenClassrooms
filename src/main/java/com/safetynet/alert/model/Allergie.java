package com.safetynet.alert.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Allergie {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String allergie;
	
	
	
	
	public Allergie(String allergie) {
		this.allergie = allergie;
	}
}
