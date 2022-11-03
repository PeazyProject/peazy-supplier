package com.peazy.supplier.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "Supplier_Product", schema = "alanlee")
@Data
public class SupplierProductEntity {

	@Id	
	@Column(name = "SeqNo", unique = true, nullable = false)
	private long seqNo;
	private long mainPicSeqNo;
	private String productName;
	private String cost;
	private String price;
	private String ProductStatus;
	private String ProductCategorySeqNo;
	private String ProductDesc;
	private String MainSkuSeqNo;
	private String createUser;
	private Date createDt;
	private String updateUser;
	private Date updateDt;
	
}
