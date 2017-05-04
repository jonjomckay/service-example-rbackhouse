package com.manywho.services.example.rbackhouse.email;

import com.manywho.sdk.api.ContentType;
import com.manywho.sdk.services.actions.Action;

@Action.Metadata(name = "Send Email", summary = "Send an email and wait for a reply", uri = "send-email")
public class SendEmailAction {
    public static class Input {
        @Action.Input(name = "To", contentType = ContentType.String)
        private String to;

        @Action.Input(name = "Message", contentType = ContentType.String)
        private String message;

        public String getTo() {
            return to;
        }

        public String getMessage() {
            return message;
        }
    }

    public static class Output {
        @Action.Output(name = "Reply", contentType = ContentType.String)
        private String reply;

        public Output(String reply) {
            this.reply = reply;
        }
    }
}
