package com.ws.wslogin.util;

import java.io.InputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import org.springframework.stereotype.Component;

@Component
public class CertificateUtil {
    
    public X509Certificate generaCertificate( String stringCertificate ) throws Exception {
        
        String pemCertificate = "-----BEGIN CERTIFICATE-----\n"
        + stringCertificate.replace(" ", "\n")
        + "\n-----END CERTIFICATE-----\n";
        System.out.println(pemCertificate);

        InputStream inputStream = new java.io.ByteArrayInputStream(pemCertificate.getBytes());
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        X509Certificate certificate = (X509Certificate) certificateFactory.generateCertificate(inputStream);

        return certificate;
    }
}
