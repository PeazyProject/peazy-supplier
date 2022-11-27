package com.peazy.supplier.model.bean;

import java.util.Date;

import lombok.Data;

@Data
public class QueryCheckOrderItemBean {
    private String SeqNo;
    private String productName;
    private String snCode;
    private String cost;
    private String category;
    private String sku;
    private Date createDt;
    private String productStatus;
    private int productOrderedCnt;
}
