package com.amunna.negotiator.service.datacollect;

import com.amunna.negotiator.sdk.core.TargetClient;
import com.amunna.negotiator.service.datacollect.core.ClientProduct;
import com.amunna.negotiator.sdk.core.ClientProductPrice;
import com.amunna.negotiator.service.datacollect.response.ProductResponse;
import com.amunna.negotiator.service.datacollect.support.BoundedExecutor;
import com.amunna.negotiator.service.datacollect.wiring.PriceCollectExecutor;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Singleton
public final class ProductPriceManager {

    private LoadingCache<ClientProduct, String /*price*/> productPriceCache;
    private final DataCollector dataCollector;
    private final BoundedExecutor executor;

    @Inject
    public ProductPriceManager(DataCollector dataCollector,
                               @PriceCollectExecutor BoundedExecutor executor) {
        this.dataCollector = dataCollector;
        this.executor = executor;
        createProductPriceCache();
    }

    public Map<String, String> getPriceForClientAndCategory(String clientUrl, String categoryId) throws Exception {
        /*Map<String, String> productPrice = Maps.newHashMap();
        CategoryResponse categoryResponse = dataCollector.getClient()
                .getCategoryResponse(clientUrl, categoryId);
        for(String productId : categoryResponse.getProducts()) {
            ClientProduct clientProduct = new ClientProduct(clientUrl, categoryId, productId);
            try {
                productPrice.put(productId, productPriceCache.get(clientProduct));
            } catch (Exception e) {
                Throwables.propagate(e);
            }
        }
        return productPrice;*/
        List<TargetClient> targetClientList = Lists.newArrayList(new TargetClient(clientUrl, categoryId));
        return getPriceForClientAndCategoryInBatch(targetClientList).get(0).getPrice();
    }

    public List<ClientProductPrice> getPriceForClientAndCategoryInBatch(List<TargetClient> targetClientList) throws Exception {
        return new ProductPriceWorker(executor, dataCollector, productPriceCache).getClientProductPrice(targetClientList);
    }

    private void createProductPriceCache() {
        productPriceCache = CacheBuilder.newBuilder()
                .maximumSize(100000)
                .expireAfterWrite(30, TimeUnit.MINUTES)
                .build(new CacheLoader<ClientProduct, String /*price*/>() {
                    public String load(ClientProduct key) throws Exception{
                        return internalGetProductPrice(key);
                    }
                });
    }

    private String internalGetProductPrice(ClientProduct clientProduct) {
        ProductResponse productResponse = dataCollector.getClient()
                .getProductResponse(clientProduct.getClientUrl(), clientProduct.getProductId());
        return productResponse.getPrice();
    }

    private class DataCollectThread implements Runnable {
        public void run() {

        }
    }

}
