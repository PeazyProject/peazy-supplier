package com.peazy.supplier.model.bean;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductColorSizeBean {
    Long colorSeqNo;
    Long sizeSeqNo;
    BigDecimal notOrderCnt;
    BigDecimal orderedCnt;
    BigDecimal checkOrderCnt;
    BigDecimal allocatedCnt;
    BigDecimal readyDeliveryCnt;
    BigDecimal finishCnt;
}
