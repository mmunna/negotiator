package com.amunna.negotiator.service.datacollect;

import com.amunna.negotiator.service.datacollect.response.CategoryResponse;
import com.amunna.negotiator.service.datacollect.response.Jsons;
import com.amunna.negotiator.service.datacollect.response.ProductResponse;
import com.google.common.base.Throwables;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;

public class FakeDataCollectClient implements DataCollectClient {

    private final Client jerseyClient;

    public FakeDataCollectClient(Client jerseyClient) {
        this.jerseyClient = jerseyClient;
    }


    public CategoryResponse getCategoryResponse(String clientUrl, String categoryId) {
        try {
            final String categoryResponse = "{"+
                    "\"id\": \"/categories/1\","+
                    "\"name\": \"chairs\","+
                    "\"client\": \"/clients/1\","+
                    "\"products\": ["+
                        "\"/products/1\","+
                        "\"/products/2\","+
                        "\"/products/3\","+
                        "\"/products/4\","+
                        "\"/products/5\""+
                    "]"+
            "}";

            return Jsons.fromJson(categoryResponse, CategoryResponse.class);

        } catch (UniformInterfaceException e) {
            throw Throwables.propagate(e);
        }
    }

    public ProductResponse getProductResponse(String clientUrl, String product) {
        try {
            final String productResponse = "{"+
                    "\"id\": \"/products/1\","+
                    "\"category\": \"/categories/1\","+
                    "\"internalName\": \"axisLeatherSwivelChair\","+
                    "\"displayName\": \"Axis Leather Swivel Chair\","+
                    "\"price\": \"1799.00\","+
                    "\"active\": \"true\""+
            "}";
            return Jsons.fromJson(productResponse, ProductResponse.class);

        } catch (UniformInterfaceException e) {
            throw Throwables.propagate(e);
        }
    }
}
