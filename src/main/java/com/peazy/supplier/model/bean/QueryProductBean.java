package com.peazy.supplier.model.bean;

import java.util.Date;

import lombok.Data;

@Data
public class QueryProductBean {
    private String productName;
    private String snCode;
    private int price;
    private String category;
    private String sku;
    private Date createDt;
    private String productStatus;
    private int productQty;
    private byte[] mainPic;

    @Override
    public String toString() {
        return "QueryProductBean [productName=" + productName + ", snCode=" + snCode + ", price=" + price
                + ", category=" + category + ", sku=" + sku + ", createDt=" + createDt + ", productStatus="
                + productStatus + ", productQty=" + productQty + "]";
    }
    
}