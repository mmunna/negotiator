package com.amunna.negotiator.service;

import com.amunna.negotiator.service.config.NegotiatorConfiguration;
import com.amunna.negotiator.service.health.NegotiatorHealthCheck;
import com.amunna.negotiator.service.resources.NegotiatorResource;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NegotiatorService extends Service<NegotiatorConfiguration> {
    private static final Logger logger = LoggerFactory.getLogger(NegotiatorService.class);

    public static void main(String args[]) throws Exception {
        new NegotiatorService().run(args);
    }

    @Override
    public void initialize(Bootstrap<NegotiatorConfiguration> bootstrap) {
        bootstrap.setName("negotiator");
    }

    @Override
    public void run(final NegotiatorConfiguration configuration, final Environment environment) throws Exception {
        logger.info("initializing ...");

        Module weatherproModule = buildNegotiatorModule(configuration, environment);
        Injector injector = Guice.createInjector(weatherproModule);

        //resources
        environment.addResource(injector.getInstance(NegotiatorResource.class));

        //health
        environment.addHealthCheck(injector.getInstance(NegotiatorHealthCheck.class));
    }


    protected Module buildNegotiatorModule(final NegotiatorConfiguration NegotiatorConfiguration, final Environment environment) {
        return new NegotiatorModuleBuilder()
                .setConfiguration(NegotiatorConfiguration)
                .setDropWizardEnvironment(environment)
                .build();

    }
}