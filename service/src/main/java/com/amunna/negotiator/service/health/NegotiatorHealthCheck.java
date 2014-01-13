package com.amunna.negotiator.service.health;

import com.google.inject.Singleton;
import com.yammer.metrics.core.HealthCheck;

@Singleton
public final class NegotiatorHealthCheck extends HealthCheck {

    public NegotiatorHealthCheck() {
        super("negotiationservice");
    }

    @Override
    protected Result check() throws Exception {
        return Result.healthy("service is alive"); //TODO check health of all dependencies.
    }
}
