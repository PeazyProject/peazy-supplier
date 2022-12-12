package com.peazy.supplier.model.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "Supplier_ProductColorSizeMapping", schema = "alanlee")
@Data
public class SupplierProductColorSizeMappingEntity {
    @Id
    @Column(name = "SeqNo", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seqNo;

    @Column(name = "ProductSeqNo")
    private long productSeqNo;
    @Column(name = "ColorSeqNo")
    private long colorSeqNo;
    @Column(name = "SizeSeqNo")
    private long sizeSeqNo;
    private BigDecimal notOrderCnt;
    private BigDecimal orderedCnt;
    private BigDecimal checkOrderCnt;
    private BigDecimal allocatedCnt;
    private BigDecimal readyDeliveryCnt;
    private BigDecimal finishCnt;
    private String createUser;
    private Date createDt;
    private String updateUser;
    private Date updateDt;
}
