package com.peazy.supplier.model.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "supplier_product_view", schema = "alanlee")
@Data
public class SupplierProductViewEntity {
    @Column(name = "ProductSeqNo")
    private Long productSeqNo;
    private String productName;
    private String snCode;
    private BigDecimal cost;
    private BigDecimal price;
    private String productDesc;
    private String category;
    private String sku;
    private Long colorSeqNo;
    private String color;
    private Date createDt;
    private String productStatus;
    private Long sizeSeqNo;
    private String size;
    @Id
    @Column(name = "PCSMSeqNo")
    private Long pcsmSeqNo;
    private Long vendorSeqNo;
    private String vendor;
    private BigDecimal notOrderCnt;
    private BigDecimal orderedCnt;
    private BigDecimal checkOrderCnt;
    private BigDecimal allocatedCnt;
    private BigDecimal readyDeliveryCnt;
    private BigDecimal finishCnt;
}
