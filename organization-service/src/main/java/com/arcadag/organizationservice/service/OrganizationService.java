package com.arcadag.organizationservice.service;

import java.util.Optional;
import java.util.UUID;

import com.arcadag.organizationservice.model.Organization;
import com.arcadag.organizationservice.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OrganizationService {
	
    @Autowired
    private final OrganizationRepository organizationRepository;

    public Organization findById(String organizationId) {
    	Optional<Organization> opt = organizationRepository.findById(organizationId);
        return (opt.isPresent()) ? opt.get() : null;
    }

    public Organization create(Organization organization){
    	organization.setId( UUID.randomUUID().toString());
        organization = organizationRepository.save(organization);
        return organization;

    }

    public void update(Organization organization){
    	organizationRepository.save(organization);
    }

    public void delete(String id){
    	organizationRepository.deleteById(id);
    }
}