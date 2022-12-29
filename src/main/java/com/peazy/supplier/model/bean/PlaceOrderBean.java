package com.peazy.supplier.model.bean;

import java.math.BigDecimal;

import com.peazy.supplier.model.entity.SupplierProductViewEntity;

import lombok.Data;

@Data
public class PlaceOrderBean {
    private Long productSeqNo;
    private String productName;
    private String sku;
    private String category;
    private BigDecimal notOrderCnt;

    public PlaceOrderBean(SupplierProductViewEntity entity) {
        this.setProductSeqNo(entity.getProductSeqNo());
        this.setProductName(entity.getProductName());
        this.setSku(entity.getSku());
        this.setCategory(entity.getCategory());
        this.setNotOrderCnt(entity.getNotOrderCnt());
    }
}
