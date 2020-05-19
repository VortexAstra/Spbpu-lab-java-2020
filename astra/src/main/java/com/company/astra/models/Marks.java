package com.company.astra.models;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
public class Marks {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Long studentId, subjectId, teacher_id, value;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "marks.subject_id", referencedColumnName = "id")
	private Subjects subjects;


	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "marks.student_id", referencedColumnName = "id")
	private Subjects subjectsMarks;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "marks.teacher_id", referencedColumnName = "id")
	private Subjects subjectsMarksTeacher;

public Marks(){}

	public Marks(Long rating) {
		this.value = rating;
	}


	public Subjects getSubjects() {
		return subjects;
	}

	public void setSubjects(Subjects subjects) {
		this.subjects = subjects;
	}

	public Subjects getSubjectsMarks() {
		return subjectsMarks;
	}

	public void setSubjectsMarks(Subjects subjectsMarks) {
		this.subjectsMarks = subjectsMarks;
	}

	public Subjects getSubjectsMarksTeacher() {
		return subjectsMarksTeacher;
	}

	public void setSubjectsMarksTeacher(Subjects subjectsMarksTeacher) {
		this.subjectsMarksTeacher = subjectsMarksTeacher;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long student_id) {
		this.studentId = student_id;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subject_id) {
		this.subjectId = subject_id;
	}

	public Long getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(Long teacher_id) {
		this.teacher_id = teacher_id;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}
}
