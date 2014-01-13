package com.amunna.negotiator.service.datacollect;

import com.amunna.negotiator.service.config.NegotiatorConfiguration;
import com.google.inject.Inject;
import com.sun.jersey.api.client.Client;
import com.yammer.dropwizard.config.Environment;

public class FakeDataCollectClientManager implements DataCollector {

    @Inject
    Environment dropwizardEnvironment;
    @Inject
    NegotiatorConfiguration negotiatorConfiguration;
    @Inject
    Client jerseyClient;

    public DataCollectClient getClient() {
        return new FakeDataCollectClient(jerseyClient);
    }
}
