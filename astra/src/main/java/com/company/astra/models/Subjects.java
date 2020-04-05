package com.company.astra.models;


import javax.persistence.*;

@Entity
public class Subjects {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn
	private Marks marks;


	private String nameOfSubjects;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNameOfSubjects() {
		return nameOfSubjects;
	}

	public void setNameOfSubjects(String nameOfSubjects) {
		this.nameOfSubjects = nameOfSubjects;
	}
}
