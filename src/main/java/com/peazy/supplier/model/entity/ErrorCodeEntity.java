package com.peazy.supplier.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "Common_ErrorCode", schema = "alanlee")
@Data
public class ErrorCodeEntity {

	@Id
	@GeneratedValue
	private Long seqNo;
	private String category;
	private String errorCode;
	private String errorMsg;
	private String lang;

}