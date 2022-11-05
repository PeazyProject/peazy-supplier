package com.peazy.supplier.model.request;

import java.util.List;

import lombok.Data;

@Data
public class QueryProductRequest {
    private String productName;
    private List<String> skuList;
    private List<String> inStockList;
    private String isAvailable;
}
