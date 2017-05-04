package com.manywho.services.example.rbackhouse.email;

import com.manywho.sdk.api.run.elements.config.ServiceRequest;
import com.manywho.sdk.api.security.AuthenticatedWho;

public class SendEmailRequest {
    private AuthenticatedWho authenticatedWho;
    private ServiceRequest serviceRequest;

    public SendEmailRequest(AuthenticatedWho authenticatedWho, ServiceRequest serviceRequest) {
        this.authenticatedWho = authenticatedWho;
        this.serviceRequest = serviceRequest;
    }

    public AuthenticatedWho getAuthenticatedWho() {
        return authenticatedWho;
    }

    public ServiceRequest getServiceRequest() {
        return serviceRequest;
    }
}
