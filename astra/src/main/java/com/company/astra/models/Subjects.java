package com.company.astra.models;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Subjects {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nameOfSubjects;


	@OneToMany(
			mappedBy = "subjectsMarks",
			cascade = CascadeType.PERSIST,
			fetch = FetchType.LAZY
	)
	private Set<Marks> marks;

	public Subjects(String nameOfSubjects, Set<Marks> mark){
		this.nameOfSubjects = nameOfSubjects;
		this.marks = mark;
	}

	public Subjects(){
		marks = new HashSet<>();
	}

	public Subjects(String name) {
		this.nameOfSubjects = name;
		marks = new HashSet<>();
	}


	public Set<Marks> getMarks() {

		for (Marks mark : marks) {
			System.out.println(mark);
		}

		return marks;
	}

	public void setMarks(Set<Marks> marks) {
		this.marks = marks;
	}



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
