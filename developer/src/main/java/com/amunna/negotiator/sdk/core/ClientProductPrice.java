package com.amunna.negotiator.sdk.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ClientProductPrice {

    @JsonProperty
    private String clientUrl;

    @JsonProperty
    private String categoryId;

    @JsonProperty
    private Map<String, String> price;

    public ClientProductPrice(String clientUrl, String categoryId, Map<String, String> price) {
        this.clientUrl = clientUrl;
        this.categoryId = categoryId;
        this.price = price;
    }

    public String getClientUrl() {
        return clientUrl;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public Map<String, String> getPrice() {
        return price;
    }
}
