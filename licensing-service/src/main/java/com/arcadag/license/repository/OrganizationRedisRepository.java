package com.arcadag.license.repository;

import com.arcadag.license.model.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRedisRepository  extends CrudRepository<Organization, String> {
}
