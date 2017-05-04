package com.manywho.services.example.rbackhouse.email;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manywho.sdk.api.run.elements.config.ServiceRequest;
import com.manywho.sdk.api.security.AuthenticatedWho;

import javax.inject.Inject;

public class SendEmailRepository {
    private final ObjectMapper objectMapper;

    @Inject
    public SendEmailRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public SendEmailRequest loadRequest(String messageId) {
        // Load the serialized request back from the storage, using the message ID as the key

        return null;
    }

    public void saveRequest(String messageId, AuthenticatedWho authenticatedWho, ServiceRequest serviceRequest) {
        SendEmailRequest sendEmailRequest = new SendEmailRequest(authenticatedWho, serviceRequest);

        try {
            String serializedRequest = objectMapper.writeValueAsString(sendEmailRequest);

            // Save this serialized request thing to storage somewhere (Redis, database, etc.), using the message ID as the key
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to serialize the request", e);
        }
    }
}
