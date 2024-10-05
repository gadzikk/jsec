package com.company.suplementary.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
@Getter
public class ConnectionProperties {
    @Value("${secure-connection.trust-store-type}")
    private String trustStoreType;
    @Value("${secure-connection.trust-store}")
    private Resource trustStore;
    @Value("${secure-connection.trust-store-password}")
    private String trustStorePassword;
    @Value("${secure-connection.key-store-type}")
    private String keyStoreType;
    @Value("${secure-connection.key-store}")
    private Resource keyStore;
    @Value("${secure-connection.key-password}")
    private String keyPassword;
    @Value("${secure-connection.key-store-password}")
    private String keyStorePassword;
    @Value("${secure-connection.key-alias}")
    private String keyAlias;
}
