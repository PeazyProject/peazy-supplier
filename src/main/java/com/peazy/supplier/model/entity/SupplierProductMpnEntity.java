package com.peazy.supplier.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "Supplier_ProductMpn", schema = "alanlee")
@Data
public class SupplierProductMpnEntity {

	@Id	
	@Column(name = "SeqNo", unique = true, nullable = false)
	private long seqNo;
	private long productSeqNo;
	private String mpn;
	private String createUser;
	private Date createDt;
	private String updateUser;
	private Date updateDt;
	
}
