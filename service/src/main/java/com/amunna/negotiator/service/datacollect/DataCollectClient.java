package com.amunna.negotiator.service.datacollect;

import com.amunna.negotiator.service.datacollect.response.CategoryResponse;
import com.amunna.negotiator.service.datacollect.response.ProductResponse;

public interface DataCollectClient {
    public CategoryResponse getCategoryResponse(String clientUrl, String categoryId);
    public ProductResponse getProductResponse(String clientUrl, String product);
}
