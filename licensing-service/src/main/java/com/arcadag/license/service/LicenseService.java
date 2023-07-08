package com.arcadag.license.service;

import com.arcadag.license.config.ServiceConfig;
import com.arcadag.license.model.License;
import com.arcadag.license.repository.LicenseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class LicenseService {
    private final LicenseRepository licenseRepository;
    private final MessageSource messages;
    private final ServiceConfig config;

    public License getLicense(String licenseId, String organizationId) {
        log.info("licenseId: {}, organizationId: {}", licenseId, organizationId);
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        log.info("license: {}", license);
        if (null == license) {
            throw new IllegalArgumentException(
                    String.format(
                            messages.getMessage("license.search.error.message", null, null),
                            licenseId, organizationId));
        }

        return license.withComment(config.getProperty());
    }

    public License createLicense(License license) {
        license.setLicenseId(UUID.randomUUID().toString());
        licenseRepository.save(license);
        return license.withComment(config.getProperty());
    }

    public String deleteLicense(String licenseId, String organizationId) {
        String responseMessage;
        License license = new License();
        license.setLicenseId(licenseId);
        licenseRepository.delete(license);
        responseMessage = String.format(
                messages.getMessage("license.delete.message", null, null), licenseId, organizationId);
        return responseMessage;

    }

    public License updateLicense(License license) {
        licenseRepository.save(license);
        return license.withComment(config.getProperty());
    }
}
