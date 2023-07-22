package com.arcadag.license.events.handler;


import com.arcadag.events.model.OrganizationChangeModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrganizationChangeHandler {


    @KafkaListener(topics = "orgChangeTopic")
    public void loggerSink(OrganizationChangeModel organization) {

        log.debug("Received a message of type " + organization.getType());
        log.debug("organization: {}", organization);

        switch(organization.getAction()){
            case "GET":
                log.debug("Received a GET event from the organization service for organization id {}", organization.getOrganizationId());
                break;
            case "CREATED":
                log.debug("Received a CREATED event from the organization service for organization id {}", organization.getOrganizationId());
                break;
            case "UPDATE":
                log.debug("Received a UPDATE event from the organization service for organization id {}", organization.getOrganizationId());
                break;
            case "DELETE":
                log.debug("Received a DELETE event from the organization service for organization id {}", organization.getOrganizationId());
                break;
            default:
                log.error("Received an UNKNOWN event from the organization service of type {}", organization.getType());
                break;
        }
    }


}