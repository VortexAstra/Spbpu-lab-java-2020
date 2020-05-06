package com.company.astra.repo;

import com.company.astra.models.Groups;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface IGroupRepository extends CrudRepository<Groups, Long> {
	List<Groups> findByName(String name);
}
