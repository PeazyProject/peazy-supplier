package com.peazy.supplier.model.bean;

import java.math.BigDecimal;

import com.peazy.supplier.model.entity.SupplierProductViewEntity;

import lombok.Data;

@Data
public class PlaceOrderDetailBean {
    private String productName;
    private String color;
    private String size;
    private BigDecimal productCnt;

    public PlaceOrderDetailBean(SupplierProductViewEntity entity) {
        this.setProductName(entity.getProductName());
        this.setColor(entity.getColor());
        this.setSize(entity.getSize());
    }
}
