package com.peazy.supplier.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "Supplier_ProductSku", schema = "alanlee")
@Data
public class SupplierProductSkuEntity {

	@Id	
	@Column(name = "SeqNo", unique = true, nullable = false)
	private long seqNo;
	private long productSeqNo;
	private String sku;
	private String createUser;
	private Date createDt;
	private String updateUser;
	private Date updateDt;
	
}
