package com.amunna.negotiator.service.datacollect.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;

public final class BoundedExecutor {

    private static final Logger logger = LoggerFactory.getLogger(BoundedExecutor.class);

    private final Executor executor;
    private final Semaphore semaphore;

    public BoundedExecutor(Executor executor, int bound) {
        this.executor = executor;
        this.semaphore = new Semaphore(bound);
    }

    public void submit(final Runnable command) throws InterruptedException, RejectedExecutionException {
        semaphore.acquire();
        try {
            executor.execute(new Runnable() {
                public void run() {
                    try {
                        command.run();
                    } finally {
                        semaphore.release();
                    }
                }
            });
        } catch (RejectedExecutionException e) {
            semaphore.release();
            throw e;
        }
    }

}