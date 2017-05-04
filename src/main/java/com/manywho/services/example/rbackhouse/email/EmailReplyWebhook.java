package com.manywho.services.example.rbackhouse.email;

import com.google.common.collect.Lists;
import com.manywho.sdk.api.ContentType;
import com.manywho.sdk.api.InvokeType;
import com.manywho.sdk.api.run.EngineValue;
import com.manywho.sdk.api.run.elements.config.ServiceResponse;
import com.manywho.sdk.client.run.RunClient;
import com.manywho.sdk.services.identity.AuthorizationEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/webhook/email-reply")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EmailReplyWebhook {
    private final static Logger LOGGER = LoggerFactory.getLogger(EmailReplyWebhook.class);

    private final RunClient runClient;
    private final SendEmailRepository sendEmailRepository;
    private final AuthorizationEncoder authorizationEncoder;

    @Inject
    public EmailReplyWebhook(RunClient runClient, SendEmailRepository sendEmailRepository, AuthorizationEncoder authorizationEncoder) {
        this.runClient = runClient;
        this.sendEmailRepository = sendEmailRepository;
        this.authorizationEncoder = authorizationEncoder;
    }

    @POST
    public void receiveReply(Incoming incoming) {
        // We load the previous request from our storage, using the message ID that was send in the webhook to us
        SendEmailRequest sendEmailRequest = sendEmailRepository.loadRequest(incoming.getId());

        // Create the list of outputs for the message action that we want to send back to the flow
        List<EngineValue> outputs = Lists.newArrayList();
        outputs.add(new EngineValue("Reply", ContentType.String, incoming.getMessage()));

        // Create the response that we call back to ManyWho with
        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.setInvokeType(InvokeType.Forward); // We tell the flow to move forward
        serviceResponse.setOutputs(outputs);
        serviceResponse.setTenantId(sendEmailRequest.getServiceRequest().getTenantId());
        serviceResponse.setToken(sendEmailRequest.getServiceRequest().getToken());

        LOGGER.info("Calling back to ManyWho for the action with the token {}", sendEmailRequest.getServiceRequest().getToken());

        String authorization = authorizationEncoder.encode(sendEmailRequest.getAuthenticatedWho());

        // Call back to ManyWho with the result
        runClient.callback(authorization, sendEmailRequest.getServiceRequest().getTenantId(), serviceResponse).enqueue(new Callback<InvokeType>() {
            @Override
            public void onResponse(Call<InvokeType> call, Response<InvokeType> response) {
                if (!response.isSuccessful()) {
                    LOGGER.error("Unable to send a callback to ManyWho for the token {}. The response status was: {}", sendEmailRequest.getServiceRequest().getToken(), response.message());
                }
            }

            @Override
            public void onFailure(Call<InvokeType> call, Throwable t) {
                LOGGER.error("Unable to send a callback to ManyWho for the token {}. The errorc was: {}", sendEmailRequest.getServiceRequest().getToken(), t.getMessage());
            }
        });

    }

    public static class Incoming {
        private String id;
        private String from;
        private String message;

        public String getId() {
            return id;
        }

        public String getFrom() {
            return from;
        }

        public String getMessage() {
            return message;
        }
    }
}
