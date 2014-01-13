package com.amunna.negotiator.service;

import com.amunna.negotiator.service.config.NegotiatorConfiguration;
import com.amunna.negotiator.service.datacollect.support.BoundedExecutor;
import com.yammer.dropwizard.client.JerseyClientBuilder;
import com.yammer.dropwizard.config.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class NegotiatorModuleBuilder {
    private static final Logger logger = LoggerFactory.getLogger(NegotiatorModuleBuilder.class);

    private NegotiatorConfiguration negotiatorConfiguration;
    private Environment dropWizardEnvironment;

    public NegotiatorModuleBuilder setConfiguration(NegotiatorConfiguration NegotiatorConfiguration) {
        this.negotiatorConfiguration = NegotiatorConfiguration;
        return this;
    }

    public NegotiatorModuleBuilder setDropWizardEnvironment(Environment dropWizardEnvironment) {
        this.dropWizardEnvironment = dropWizardEnvironment;
        return this;
    }

    public NegotiatorModule build() {
        assertNoneNull(negotiatorConfiguration, dropWizardEnvironment);
        return new NegotiatorModule(negotiatorConfiguration)
                .setPriceCollectExecutor(createPriceCollectExecutor())
                .setDefaultJerseyClient(createJerseyClient())
                .setDropWizardEnvironment(dropWizardEnvironment);
    }

    private com.sun.jersey.api.client.Client createJerseyClient() {
        return new JerseyClientBuilder()
                .using(dropWizardEnvironment)
                .using(negotiatorConfiguration.getHttpClientConfiguration())
                .build();
    }

    private BoundedExecutor createPriceCollectExecutor() {
        final int numWorkerThreads = 10; //TODO make configurable
        final ExecutorService executor = dropWizardEnvironment.managedExecutorService("price-worker-%d",
                numWorkerThreads, numWorkerThreads, 5, TimeUnit.SECONDS);
        return new BoundedExecutor(executor, numWorkerThreads);
    }

    private static void assertNoneNull(Object... values) {
        for (Object value : values) {
            if (value == null) {
                throw new IllegalStateException();
            }
        }
    }


}
