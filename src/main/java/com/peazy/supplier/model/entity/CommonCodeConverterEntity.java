package com.peazy.supplier.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "Common_CodeConverter", schema = "alanlee")
@Data
public class CommonCodeConverterEntity {

	@Id	
	@Column(name = "SeqNo", unique = true, nullable = false)
	private long seqNo;
	private String mainCategory;
	private String subCategory;
	private String codeKey;
	private String codeDesc;
	private String lang;
	private String remark;
	private int ordinal;
	private String isActivated;
	private String createUser;
	private Date createDt;
	private String updateUser;
	private Date updateDt;
	
}
