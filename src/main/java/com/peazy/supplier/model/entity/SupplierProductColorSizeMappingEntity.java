package com.peazy.supplier.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "supplier_ProductColorSizeMapping", schema = "alanlee")
@Data
public class SupplierProductColorSizeMappingEntity {
    @Id
    @Column(name = "SeqNo", unique = true, nullable = false)
    private long seqNo;
    private long ProductSeqNo;
    private long ColorSeqNo;
    private long SizeSeqNo;
    private String NotOrderCnt;
    private String OrderedCnt;
    private String CheckOrderCnt;
    private String AllocatedCnt;
    private String ReadyDeliveryCnt;
    private String FinishCnt;
    private String CreateUser;
    private Date CreateDt;
    private String UpdateUser;
    private Date UpdateDt;
}
