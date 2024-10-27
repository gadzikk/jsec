package com.company.suplementary.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
@Getter
public class TLSProperties {
    @Value("${server.ssl.trust-store-type}")
    private String trustStoreType;
    @Value("${server.ssl.trust-store}")
    private Resource trustStore;
    @Value("${server.ssl.trust-store-password}")
    private String trustStorePassword;
    @Value("${server.ssl.key-store-type}")
    private String keyStoreType;
    @Value("${server.ssl.key-store}")
    private Resource keyStore;
    @Value("${server.ssl.key-password}")
    private String keyPassword;
    @Value("${server.ssl.key-store-password}")
    private String keyStorePassword;
    @Value("${server.ssl.key-alias}")
    private String keyAlias;
}
