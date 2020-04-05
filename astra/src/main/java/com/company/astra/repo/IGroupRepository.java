package com.company.astra.repo;

import com.company.astra.models.Groups;
import org.springframework.data.repository.CrudRepository;

public interface IGroupRepository extends CrudRepository<Groups, Long> {
}
