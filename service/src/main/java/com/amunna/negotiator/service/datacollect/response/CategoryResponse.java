package com.amunna.negotiator.service.datacollect.response;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CategoryResponse {

    /*{
        "id": "/categories/1",
        "name": "chairs",
        "client": "/clients/1",
        "products": [
            "/products/1",
            "/products/2",
            "/products/3",
            "/products/4",
            "/products/5"
        ]
    }*/
    @JsonProperty
    private String id;

    @JsonProperty
    private String name;

    @JsonProperty
    private String client;

    @JsonProperty
    private List<String> products;

    public List<String> getProducts() {
        return this.products;
    }

}
