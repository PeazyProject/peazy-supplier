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
@Table(name = "Common_Picture", schema = "alanlee")
@Data
public class CommonPictureEntity {

	@Id	
	@Column(name = "SnCode", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long snCode;
	private byte[] picture;
	private String pictureName;
	private String pictureExtension;
	private String createUser;
	private Date createDt;
	private String updateUser;
	private Date updateDt;
	
}
