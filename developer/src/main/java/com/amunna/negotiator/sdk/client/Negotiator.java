package com.amunna.negotiator.sdk.client;

import com.amunna.negotiator.sdk.core.ClientProductPrice;
import com.amunna.negotiator.sdk.core.TargetClient;

import java.util.List;
import java.util.Map;

public interface Negotiator {
    public Map<String, String> getPriceForProduct(String clientUrl, String categoryId);
    public List<ClientProductPrice> batchGetPriceForProduct(List<TargetClient> targetClientList);
}
