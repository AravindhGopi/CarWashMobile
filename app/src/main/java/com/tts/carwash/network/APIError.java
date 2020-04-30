package com.tts.carwash.network;

import java.util.ArrayList;

public class APIError {

    private boolean success;

    private ArrayList messages;



    public static class Builder {
        private boolean success;

        private ArrayList messages;
        public Builder() {}



        public Builder success(final boolean success) {

            this.success = success;

            return this;

        }



        public Builder messages(final ArrayList messages) {

            this.messages = messages;

            return this;

        }



        public Builder defaultError() {

            this.messages.add("Something error");

            return this;

        }



        public APIError build() { return new APIError(this); }

    }



    private APIError(final Builder builder) {

        success = builder.success;

        messages = builder.messages;

    }

}