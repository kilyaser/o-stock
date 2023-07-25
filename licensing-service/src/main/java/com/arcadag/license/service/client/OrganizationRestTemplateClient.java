package com.arcadag.license.service.client;


import com.arcadag.license.model.Organization;
import com.arcadag.license.repository.OrganizationRedisRepository;
import com.arcadag.license.utils.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrganizationRestTemplateClient {

    private final KeycloakRestTemplate restTemplate;
    private final OrganizationRedisRepository redisRepository;
    @Value("${gateway.host}")
    private String host;

    public Organization getOrganization(String organizationId) {
        log.debug("In Licensing Service.getOrganization: {}", UserContext.getCorrelationId());

        Organization organization = checkRedisCache(organizationId);
        if (organization != null) {
            log.debug("I have successfully retrieved an organization {} from the redis cache: {}",
                    organizationId, organization);
            return organization;
        }
        log.debug("Unable to locate organization from the redis cache: {}.", organizationId);

        ResponseEntity<Organization> restExchange = restTemplate.exchange(
                host + "/organization/v1/organization/{organizationId}",
                HttpMethod.GET,
                null,
                Organization.class,
                organizationId);
        organization = restExchange.getBody();

        if (organization != null) {
            cacheOrganizationObject(organization);
        }
        return restExchange.getBody();
    }

    public Organization checkRedisCache(String organizationId) {
        try {
            return redisRepository.findById(organizationId)
                    .orElse(null);
        } catch (Exception ex) {
            log.error("Error encountered while trying to retrieve organization {} check Redis Cache. Exception {}",
                    organizationId, ex);
            return null;
        }
    }

    private void cacheOrganizationObject(Organization organization) {
        try {
            redisRepository.save(organization);
        } catch (Exception ex) {
            log.error("Unable to cache organization {} in Redis. Exception {}",
                    organization.getId(), ex);
        }
    }
}
