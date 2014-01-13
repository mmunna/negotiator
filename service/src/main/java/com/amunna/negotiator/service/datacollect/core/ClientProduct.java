package com.amunna.negotiator.service.datacollect.core;

import com.google.common.base.Objects;

public class ClientProduct {
    private final String clientUrl;
    private final String categoryId;
    private final String productId;

    public ClientProduct(String clientUrl, String categoryId, String productId) {
        this.clientUrl = clientUrl;
        this.categoryId = categoryId;
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientProduct)) {
            return false;
        }
        ClientProduct that = (ClientProduct) o;

        return this.clientUrl.equals(that.clientUrl) &&
               this.categoryId.equals(that.categoryId) &&
               this.productId.equals(that.productId);

    }

    public String getClientUrl() {
        return clientUrl;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getProductId() {
        return productId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(clientUrl, categoryId, productId);
    }
}
