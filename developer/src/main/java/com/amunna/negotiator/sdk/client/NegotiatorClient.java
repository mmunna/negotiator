package com.amunna.negotiator.sdk.client;

import com.amunna.negotiator.sdk.core.ClientProductPrice;
import com.amunna.negotiator.sdk.core.TargetClient;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.UniformInterfaceException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;
import java.util.Map;

public class NegotiatorClient implements Negotiator{

    private final Client jersey;
    private final UriBuilder baseUrl;

    public NegotiatorClient(String baseUrl, Client jersey) {
        this.baseUrl = UriBuilder.fromUri(baseUrl);
        this.jersey = jersey;
    }

    public Map<String, String> getPriceForProduct(String clientUrl, String categoryId) {
        return internalGetPriceForProduct(clientUrl, categoryId);
    }

    public List<ClientProductPrice> batchGetPriceForProduct(List<TargetClient> targetClientList) {
        return internalBatchGetPriceForProduct(targetClientList);
    }

    private Map<String, String> internalGetPriceForProduct(String clientUrl, String categoryId) {
        try {
            URI uri = baseUrl.clone()
                    .segment("price")
                    .queryParam("clientUrl", clientUrl)
                    .queryParam("categoryId", categoryId)
                    .build();
            return jersey.resource(uri)
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .get(Map.class);
        } catch (UniformInterfaceException e) {
            throw e;
        }
    }

    private List<ClientProductPrice> internalBatchGetPriceForProduct(List<TargetClient> targetClientList) {
        try {
            URI uri = baseUrl.clone()
                    .segment("price", "batch")
                    .build();
            return jersey.resource(uri)
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .post(new JsonObjectType(), targetClientList);
        } catch (UniformInterfaceException e) {
            throw e;
        }
    }

    private static class JsonObjectType extends GenericType<List<ClientProductPrice>> {
    }
}
