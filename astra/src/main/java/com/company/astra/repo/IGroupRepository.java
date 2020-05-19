package com.company.astra.repo;

import com.company.astra.models.Groups;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IGroupRepository extends CrudRepository<Groups, Long> {
	List<Groups> findByName(String name);
	Optional<Groups> findById(Long id);
}
