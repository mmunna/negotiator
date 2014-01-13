package com.amunna.negotiator.service.datacollect.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductResponse {

    /*
    {
	    "id": "/products/5",
	    "category": "/categories/1",
	    "internalName": "ankaraChairwithLeatherCushion",
	    "displayName": "Ankara Chair with Leather Cushion",
	    "price": "799.00",
	    "active": "true"
    }
     */

    @JsonProperty
    private String id;

    @JsonProperty
    private String category;

    @JsonProperty
    private String internalName;

    @JsonProperty
    private String displayName;

    @JsonProperty
    private String price;

    @JsonProperty
    private String active;

    public String getPrice() {
        return price;
    }
}
