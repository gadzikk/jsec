package com.company.suplementary.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;

@Configuration
@AllArgsConstructor
public class ClientConfiguration {

    private final ObjectMapper objectMapper;
    private final TLSProperties properties;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(new CustomRequestFactory(sslContext()));
    }

    @SneakyThrows
    private SSLContext sslContext() {
        var sslContext = SSLContext.getInstance("TLSv1.2");

        // Load client certificate and private key
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        char[] keyStorePasswordArray = properties.getKeyStorePassword().toCharArray();
        Resource keyStoreResource = properties.getKeyStore();
        URL keyStoreUrl = keyStoreResource.getURL();
        if (keyStoreUrl == null) {
            throw new FileNotFoundException("Keystore file not found on classpath");
        }
        keyStore.load(keyStoreUrl.openStream(), keyStorePasswordArray);

        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, keyStorePasswordArray);

        // Load trust store
        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        Resource trustStoreResource = properties.getTrustStore();
        URL trustStoreUrl = trustStoreResource.getURL();
        if (trustStoreUrl == null) {
            throw new FileNotFoundException("Truststore file not found on classpath");
        }
        trustStore.load(trustStoreUrl.openStream(), keyStorePasswordArray);

        // Initialize TrustManagerFactory with the trust store
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore);

        // Initialize SSL context
        sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());
        return sslContext;
    }

    private static class CustomRequestFactory extends SimpleClientHttpRequestFactory {
        private final SSLContext sslContext;

        public CustomRequestFactory(SSLContext sslContext) {
            this.sslContext = sslContext;
        }

        @Override
        protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
            if (connection instanceof javax.net.ssl.HttpsURLConnection) {
                ((javax.net.ssl.HttpsURLConnection) connection).setHostnameVerifier((hostname, session) -> true);
//                ((javax.net.ssl.HttpsURLConnection) connection).setSSLSocketFactory(sslContext.getSocketFactory());
            }
            super.prepareConnection(connection, httpMethod);
        }
    }
}
