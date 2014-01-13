package com.amunna.negotiator.service.datacollect;

import com.amunna.negotiator.sdk.core.TargetClient;
import com.amunna.negotiator.service.datacollect.core.ClientProduct;
import com.amunna.negotiator.sdk.core.ClientProductPrice;
import com.amunna.negotiator.service.datacollect.response.CategoryResponse;
import com.amunna.negotiator.service.datacollect.support.BoundedExecutor;
import com.google.common.base.Throwables;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.eclipse.jetty.util.ConcurrentHashSet;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductPriceWorker {

    private AtomicInteger modified = new AtomicInteger();
    private final BoundedExecutor executor;
    private final DataCollector dataCollector;

    private final LoadingCache<ClientProduct, String /*price*/> productPriceCache;

    public ProductPriceWorker(BoundedExecutor executor,
                              DataCollector dataCollector,
                              LoadingCache<ClientProduct, String> productPriceCache) {
        this.executor = executor;
        this.dataCollector = dataCollector;
        this.productPriceCache = productPriceCache;
    }

    public List<ClientProductPrice> getClientProductPrice(final List<TargetClient> targetClientList) throws Exception {
        final ConcurrentLinkedQueue<TargetClient> concurClientList = new ConcurrentLinkedQueue<TargetClient>();
        concurClientList.addAll(targetClientList);
        final ConcurrentHashSet<ClientProductPrice> clientProductPriceSet = new ConcurrentHashSet<ClientProductPrice>();
        int numberOfClients = targetClientList.size();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < numberOfClients; i++) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Map<String, String> price = Maps.newHashMap();
                        TargetClient targetClient;
                        if ((targetClient = concurClientList.poll()) != null) {
                            CategoryResponse categoryResponse = dataCollector.getClient()
                                    .getCategoryResponse(targetClient.getClientUrl(), targetClient.getCategoryId());

                            for (String productId : categoryResponse.getProducts()) {
                                ClientProduct clientProduct = new ClientProduct(targetClient.getClientUrl(), targetClient.getCategoryId(), productId);
                                try {
                                    price.put(productId, productPriceCache.get(clientProduct));
                                } catch (Exception e) {
                                    Throwables.propagate(e);
                                }
                            }
                        }
                        ClientProductPrice clientProductPrice = new ClientProductPrice(targetClient.getClientUrl(), targetClient.getCategoryId(), price);
                        clientProductPriceSet.add(clientProductPrice);
                    } finally {
                        modified.incrementAndGet();
                    }
                }
            });
        }
        while (modified.get() != numberOfClients) {
            Thread.sleep(5);
            if (System.currentTimeMillis()-startTime > 30000) {
                throw new RuntimeException("taking too long to process request");
            }
        }
        return Lists.newArrayList(clientProductPriceSet);
    }

}
