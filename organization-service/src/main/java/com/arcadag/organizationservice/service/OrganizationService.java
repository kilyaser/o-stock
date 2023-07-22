package com.arcadag.organizationservice.service;

import java.util.Optional;
import java.util.UUID;

import com.arcadag.organizationservice.events.source.SimpleSourceBean;
import com.arcadag.organizationservice.model.ActionEnum;
import com.arcadag.organizationservice.model.Organization;
import com.arcadag.organizationservice.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final SimpleSourceBean simpleSourceBean;

    public Organization findById(String organizationId) {
        Optional<Organization> opt = organizationRepository.findById(organizationId);
        simpleSourceBean.publishOrganizationChange(ActionEnum.GET, organizationId);
        return (opt.isPresent()) ? opt.get() : null;
    }

    public Organization create(Organization organization) {
        organization.setId(UUID.randomUUID().toString());
        organization = organizationRepository.save(organization);

        simpleSourceBean.publishOrganizationChange(ActionEnum.CREATED, organization.getId());

        return organization;

    }

    public void update(Organization organization) {
        organizationRepository.save(organization);
        simpleSourceBean.publishOrganizationChange(ActionEnum.UPDATED, organization.getId());
    }

    public void delete(String organizationId) {
        organizationRepository.deleteById(organizationId);
        simpleSourceBean.publishOrganizationChange(ActionEnum.DELETED, organizationId);
    }
}