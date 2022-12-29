package com.peazy.supplier.model.request;

import java.util.List;

import lombok.Data;

@Data
public class OrderProductsRequest {
    private List<Long> seqList;
}