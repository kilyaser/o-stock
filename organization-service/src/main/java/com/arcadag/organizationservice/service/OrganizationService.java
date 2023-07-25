package com.arcadag.organizationservice.service;

import java.util.Optional;
import java.util.UUID;

import brave.ScopedSpan;
import brave.Tracer;
import com.arcadag.organizationservice.events.source.SimpleSourceBean;
import com.arcadag.organizationservice.model.ActionEnum;
import com.arcadag.organizationservice.model.Organization;
import com.arcadag.organizationservice.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final SimpleSourceBean simpleSourceBean;

    public Organization findById(String organizationId) {
        Optional<Organization> opt = null;
            opt = organizationRepository.findById(organizationId);
            simpleSourceBean.publishOrganizationChange(ActionEnum.GET, organizationId);
            if (!opt.isPresent()) {
                String message = String.format("Unable to find an organization with theOrganization id %s", organizationId);
                log.error(message);
                throw new IllegalArgumentException(message);
            }
            log.debug("Retrieving Organization Info: {}", opt.get());
        return opt.get();
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