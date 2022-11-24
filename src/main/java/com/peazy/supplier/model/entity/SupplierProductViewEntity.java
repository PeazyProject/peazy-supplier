package com.peazy.supplier.model.entity;

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
    private String cost;
    private String category;
    private String sku;
    private String color;
    private Date createDt;
    private String productStatus;
    private String size;
    @Id
    @Column(name = "PCSMSeqNo")
    private Long pcsmSeqNo;
    private Long notOrderCnt;
    private Long vendorSeqNo;
    private String vendor;
}
