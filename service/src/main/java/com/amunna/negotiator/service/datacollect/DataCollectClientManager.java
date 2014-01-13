package com.amunna.negotiator.service.datacollect;

import com.amunna.negotiator.service.config.NegotiatorConfiguration;
import com.google.inject.Inject;
import com.sun.jersey.api.client.Client;
import com.yammer.dropwizard.config.Environment;

public class DataCollectClientManager implements DataCollector {

    @Inject Client jerseyClient;

    public SimpleDataCollectClient getClient() {
        return new SimpleDataCollectClient(jerseyClient);
    }
}
