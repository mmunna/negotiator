package com.amunna.negotiator.service.datacollect;

import com.google.inject.Inject;
import com.sun.jersey.api.client.Client;

public class DataCollectClientManager implements DataCollector {

    @Inject Client jerseyClient;

    public SimpleDataCollectClient getClient() {
        return new SimpleDataCollectClient(jerseyClient);
    }
}
