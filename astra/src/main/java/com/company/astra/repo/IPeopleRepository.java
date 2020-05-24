package com.company.astra.repo;

import com.company.astra.models.Groups;
import com.company.astra.models.People;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPeopleRepository extends JpaRepository<People, Long> {
	List<People> findAll();
//	List<People> findPeopleBySubjectName();
	List<People> findPeopleByFirstName(String  name);
	List<People> getAllByGroups(Optional<Groups> groups);
//	List<People> findPeopleByFirst_name(String name);


//	List<Groups> addByID(Optional<Groups> groups);
}
