package com.peazy.supplier.model.bean;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductColorSizeBean {
    String colorSeqNo;
    String sizeSeqNo;
    BigDecimal notOrderCnt;
    BigDecimal orderedCnt;
    BigDecimal checkOrderCnt;
    BigDecimal allocatedCnt;
    BigDecimal readyDeliveryCnt;
    BigDecimal finishCnt;
}
