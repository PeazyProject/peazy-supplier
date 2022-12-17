package com.peazy.supplier.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "Supplier_ProductPic", schema = "alanlee")
@Data
public class SupplierProductPicEntity {

	@Id	
	@Column(name = "SeqNo", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long seqNo;
	private long productSeqNo;
	private String snCode;
	private String createUser;
	private Date createDt;
	private String updateUser;
	private Date updateDt;
	
}
