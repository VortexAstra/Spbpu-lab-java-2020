package com.company.astra.repo;

import com.company.astra.models.People;
import org.springframework.data.repository.CrudRepository;

public interface IPeopleRepository extends CrudRepository<People, Long> {
}
