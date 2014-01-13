package com.amunna.negotiator.sdk.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class TargetClient {

    @Valid
    @NotNull
    @JsonProperty
    private String clientUrl;

    @Valid @NotNull @JsonProperty
    private String categoryId;

    public TargetClient() {}

    public TargetClient(String clientUrl, String categoryId) {
        this.clientUrl = clientUrl;
        this.categoryId = categoryId;
    }

    public String getClientUrl() {
        return clientUrl;
    }

    public String getCategoryId() {
        return categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TargetClient)) {
            return false;
        }
        TargetClient that = (TargetClient) o;

        return this.clientUrl.equals(that.clientUrl) &&
                this.categoryId.equals(that.categoryId);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(clientUrl, categoryId);
    }
}
