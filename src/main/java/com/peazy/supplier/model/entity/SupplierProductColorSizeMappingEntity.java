package com.peazy.supplier.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "Supplier_ProductColorSizeMapping", schema = "alanlee")
@Data
public class SupplierProductColorSizeMappingEntity {
    @Id
    @Column(name = "SeqNo", unique = true, nullable = false)
    private long seqNo;

    @Column(name = "ProductSeqNo")
    private long productSeqNo;
    @Column(name = "ColorSeqNo")
    private long colorSeqNo;
    @Column(name = "SizeSeqNo")
    private long sizeSeqNo;
    private String notOrderCnt;
    private String orderedCnt;
    private String checkOrderCnt;
    private String allocatedCnt;
    private String readyDeliveryCnt;
    private String finishCnt;
    private String createUser;
    private Date createDt;
    private String updateUser;
    private Date updateDt;
}
