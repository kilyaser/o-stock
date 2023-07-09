package com.arcadag.organizationservice.controller;

import com.arcadag.organizationservice.model.Organization;
import com.arcadag.organizationservice.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(value="v1/organization")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;


    @RequestMapping(value="/{organizationId}",method = RequestMethod.GET)
    public ResponseEntity<Organization> getOrganization(@PathVariable("organizationId") String organizationId) {
        return ResponseEntity.ok(organizationService.findById(organizationId));
    }

    @RequestMapping(value="/{organizationId}",method = RequestMethod.PUT)
    public void updateOrganization( @PathVariable("organizationId") String id, @RequestBody Organization organization) {
        organizationService.update(organization);
    }

    @PostMapping
    public ResponseEntity<Organization>  saveOrganization(@RequestBody Organization organization) {
    	return ResponseEntity.ok(organizationService.create(organization));
    }

    @RequestMapping(value="/{organizationId}",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganization( @PathVariable("organizationId") String id,  @RequestBody Organization organization) {
        organizationService.delete(organization);
    }

}
