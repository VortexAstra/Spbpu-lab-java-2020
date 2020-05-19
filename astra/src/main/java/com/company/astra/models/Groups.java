package com.company.astra.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Groups implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;


	@OneToMany(
			mappedBy = "groups",
			cascade = CascadeType.PERSIST,
			fetch = FetchType.LAZY
	)
	private Set<People> people;

	public Groups() {
		people = new HashSet<>();
	}

	public Groups(Long id) {
		this.id = id;
	}

	public Groups(String name) {
		this.name = name;
		people = new HashSet<>();
	}

	public Set<People> getPeople() {
		return people;
	}

	public void setPeople(Set<People> people) {
		this.people = people;
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