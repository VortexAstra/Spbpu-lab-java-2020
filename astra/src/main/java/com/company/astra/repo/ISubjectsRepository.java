package com.company.astra.repo;

import com.company.astra.models.Subjects;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISubjectsRepository extends CrudRepository<Subjects, Long> {
	Subjects findByNameOfSubjects(String subjects);
	void deleteByNameOfSubjects(String s);
	Subjects findSubjectsById(Long in);


}
