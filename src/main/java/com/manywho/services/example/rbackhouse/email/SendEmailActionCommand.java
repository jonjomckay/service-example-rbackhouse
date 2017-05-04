package com.manywho.services.example.rbackhouse.email;

import com.manywho.sdk.api.InvokeType;
import com.manywho.sdk.api.run.elements.config.ServiceRequest;
import com.manywho.sdk.services.actions.ActionCommand;
import com.manywho.sdk.services.actions.ActionResponse;
import com.manywho.services.example.rbackhouse.ApplicationConfiguration;
import com.manywho.services.example.rbackhouse.email.SendEmailAction.Input;
import com.manywho.services.example.rbackhouse.email.SendEmailAction.Output;

import javax.inject.Inject;

public class SendEmailActionCommand implements ActionCommand<ApplicationConfiguration, SendEmailAction, Input, Output> {
    private final SendEmailManager manager;

    @Inject
    public SendEmailActionCommand(SendEmailManager manager) {
        this.manager = manager;
    }

    @Override
    public ActionResponse<Output> execute(ApplicationConfiguration configuration, ServiceRequest serviceRequest, Input input) {
        // Do something here that sends an email using a 3rd party provider, and when a reply is received, it'll call back
        manager.sendEmail(serviceRequest, input.getTo(), input.getMessage());

        // Here, we send back a WAIT response to ManyWho, which will tell it to expect an asynchronous response to progress the flow at some point in the future
        return new ActionResponse<>(InvokeType.Wait, "Waiting for a reply to the email");
    }
}
