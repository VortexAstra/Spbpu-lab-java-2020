package com.company.astra.models;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


@Entity
public class People implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String firstName, last_name, pather_name;
	private Long group_id;
	private String type; //  Char S/P/ Student Prepodavatel'

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "people.group_id", referencedColumnName = "id")
	public Groups groups;


	public Set<Marks> getMarksPeople() {
		return marksPeople;
	}

	public void setMarksPeople(Set<Marks> marksPeople) {
		this.marksPeople = marksPeople;
	}

	public Set<Marks> getMarksTeacher() {
		return marksTeacher;
	}

	public void setMarksTeacher(Set<Marks> marksTeacher) {
		this.marksTeacher = marksTeacher;
	}

	@OneToMany(
			mappedBy = "subjectsMarks",
			cascade = CascadeType.PERSIST,
			fetch = FetchType.LAZY
	)
	private Set<Marks> marksPeople;

	@OneToMany(
			mappedBy = "subjectsMarksTeacher",
			cascade = CascadeType.PERSIST,
			fetch = FetchType.LAZY
	)
	private Set<Marks> marksTeacher;



	public People() {
	}

	public People(String first_name, String last_name, String pather_name, String type, Long group_id, Groups groups) {
		this.firstName = first_name;
		this.last_name = last_name;
		this.pather_name = pather_name;
		this.type = type;
		this.group_id = group_id;
		this.groups = groups;
	}

	public People(String name1) {
		this.firstName = name1;
	}


	public Groups getGroups() {
		return groups;
	}

	public void setGroups(Groups groups) {
		this.groups = groups;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String first_name) {
		this.firstName = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getPather_name() {
		return pather_name;
	}

	public void setPather_name(String pather_name) {
		this.pather_name = pather_name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
