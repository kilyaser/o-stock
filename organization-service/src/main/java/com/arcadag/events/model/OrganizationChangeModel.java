package com.arcadag.events.model;

import lombok.Data;

import java.io.Serializable;


@Data
public class OrganizationChangeModel implements Serializable {
    private String type;
    private String action;
    private String organizationId;
    private String correlationId;

    public OrganizationChangeModel(String type, String action, String organizationId, String correlationId) {
        this.type = type;
        this.action = action;
        this.organizationId = organizationId;
        this.correlationId = correlationId;
    }
}
