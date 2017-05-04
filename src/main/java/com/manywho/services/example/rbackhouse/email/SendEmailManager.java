package com.manywho.services.example.rbackhouse.email;

import com.google.inject.Provider;
import com.manywho.sdk.api.run.elements.config.ServiceRequest;
import com.manywho.sdk.api.security.AuthenticatedWho;

import javax.inject.Inject;

public class SendEmailManager {
    private final Provider<AuthenticatedWho> authenticatedWhoProvider;
    private final SendEmailRepository repository;

    @Inject
    public SendEmailManager(Provider<AuthenticatedWho> authenticatedWhoProvider, SendEmailRepository repository) {
        this.authenticatedWhoProvider = authenticatedWhoProvider;
        this.repository = repository;
    }

    public void sendEmail(ServiceRequest serviceRequest, String to, String message) {
        // Blah blah, send the email, etc. and get back a message ID

        // Save the request, as we'll need to fetch it later when we want to call back to ManyWho
        repository.saveRequest("12345", authenticatedWhoProvider.get(), serviceRequest);
    }
}
