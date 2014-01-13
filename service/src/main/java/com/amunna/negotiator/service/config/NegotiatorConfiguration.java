package com.amunna.negotiator.service.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.internal.NotNull;
import com.yammer.dropwizard.client.JerseyClientConfiguration;
import com.yammer.dropwizard.config.Configuration;

import javax.validation.Valid;

public class NegotiatorConfiguration extends Configuration {

    @Valid
    @NotNull
    @JsonProperty("serviceName")
    private String serviceName;

    @Valid @NotNull @JsonProperty("httpClient")
    private JerseyClientConfiguration httpClientConfiguration = new JerseyClientConfiguration();

    public String getServiceName() {
        return serviceName;
    }

    public JerseyClientConfiguration getHttpClientConfiguration() {
        return httpClientConfiguration;
    }
}
