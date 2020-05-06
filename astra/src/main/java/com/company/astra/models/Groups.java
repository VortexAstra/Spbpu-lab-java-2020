package com.company.astra.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Groups implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id ;

	private String name;


	public Groups(String name) {
		this.name = name;
	}

	public Groups() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}