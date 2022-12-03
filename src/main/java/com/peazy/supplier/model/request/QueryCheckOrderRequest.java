package com.peazy.supplier.model.request;

import lombok.Data;

@Data
public class QueryCheckOrderRequest {
    private String productName;
    private String sku;
}
