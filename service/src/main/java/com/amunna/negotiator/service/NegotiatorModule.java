package com.amunna.negotiator.service;

import com.amunna.negotiator.service.config.NegotiatorConfiguration;
import com.amunna.negotiator.service.datacollect.DataCollectClientManager;
import com.amunna.negotiator.service.datacollect.DataCollector;
import com.amunna.negotiator.service.datacollect.support.BoundedExecutor;
import com.amunna.negotiator.service.datacollect.wiring.PriceCollectExecutor;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.sun.jersey.api.client.Client;
import com.yammer.dropwizard.config.Environment;
import com.yammer.metrics.guice.InstrumentationModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NegotiatorModule extends InstrumentationModule {
    private static final Logger logger = LoggerFactory.getLogger(NegotiatorModule.class);

    private Client defaultJerseyClient;
    protected NegotiatorConfiguration negotiatorConfiguration;
    private Environment dropwizardEnvironment;
    private BoundedExecutor priceCollectExecutor;

    protected NegotiatorModule() {}

    public NegotiatorModule(NegotiatorConfiguration negotiatorConfiguration) {
        this.negotiatorConfiguration = negotiatorConfiguration;
    }

    public NegotiatorModule setDefaultJerseyClient(Client defaultJerseyClient) {
        this.defaultJerseyClient = defaultJerseyClient;
        return this;
    }

    public NegotiatorModule setDropWizardEnvironment(Environment dropwizardEnvironment) {
        this.dropwizardEnvironment = dropwizardEnvironment;
        return this;
    }

    public NegotiatorModule setPriceCollectExecutor(BoundedExecutor executor) {
        this.priceCollectExecutor = executor;
        return this;
    }

    @Override
    protected void configure() {
        bind(Client.class).toInstance(defaultJerseyClient);
        bind(com.yammer.dropwizard.config.Environment.class).toInstance(dropwizardEnvironment);
        bind(DataCollector.class).to(DataCollectClientManager.class).asEagerSingleton();

        bind(BoundedExecutor.class).annotatedWith(PriceCollectExecutor.class).toInstance(priceCollectExecutor);
    }

    @Provides
    @Singleton
    private NegotiatorConfiguration createNegotiatorConfiguration() {
        return negotiatorConfiguration;
    }


}
