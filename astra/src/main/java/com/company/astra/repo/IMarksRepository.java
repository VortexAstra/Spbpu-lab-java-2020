package com.company.astra.repo;

import com.company.astra.models.Marks;
import com.company.astra.models.People;
import com.company.astra.models.Subjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMarksRepository extends JpaRepository<Marks, Long> {
	List<Marks> findAll();
	List<Marks> findByStudentId(Long id);
	Long findSubjectIDByStudentId(Long id);
//	void deleteSubjectsByValue(Long value);


}
