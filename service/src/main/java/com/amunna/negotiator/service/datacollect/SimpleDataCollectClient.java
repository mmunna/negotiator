package com.amunna.negotiator.service.datacollect;

import com.amunna.negotiator.service.datacollect.response.CategoryResponse;
import com.amunna.negotiator.service.datacollect.response.Jsons;
import com.amunna.negotiator.service.datacollect.response.ProductResponse;
import com.google.common.base.Throwables;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class SimpleDataCollectClient implements DataCollectClient {

    private final Client jerseyClient;

    public SimpleDataCollectClient(Client jerseyClient) {
        this.jerseyClient = jerseyClient;
    }


    public CategoryResponse getCategoryResponse(String clientUrl, String categoryId) {
        try {
            final UriBuilder baseUrl = UriBuilder.fromUri(clientUrl);
            final URI uri = baseUrl.clone()
                    .build();

            String categoryResponse =  jerseyClient.resource(uri)
                    .get(String.class);
            return Jsons.fromJson(categoryResponse, CategoryResponse.class);

        } catch (UniformInterfaceException e) {
            throw Throwables.propagate(e);
        }
    }

    public ProductResponse getProductResponse(String clientUrl, String product) {
        try {
            final UriBuilder baseUrl = UriBuilder.fromUri(clientUrl);
            final URI uri = baseUrl.clone()
                    .build();

            String productResponse =  jerseyClient.resource(uri)
                    .get(String.class);
            return Jsons.fromJson(productResponse, ProductResponse.class);

        } catch (UniformInterfaceException e) {
            throw Throwables.propagate(e);
        }
    }

}
