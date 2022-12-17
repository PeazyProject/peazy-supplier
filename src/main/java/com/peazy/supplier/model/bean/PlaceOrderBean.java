package com.peazy.supplier.model.bean;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PlaceOrderBean {
    private Long productSeqNo;
    private String productName;
    private String sku;
    private String category;
    private BigDecimal notOrderCnt;
}
