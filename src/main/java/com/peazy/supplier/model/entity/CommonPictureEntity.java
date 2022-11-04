package com.peazy.supplier.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "Common_Picture", schema = "alanlee")
@Data
public class CommonPictureEntity {

	@Id	
	@Column(name = "SnCode", unique = true, nullable = false)
	private String snCode;
	private byte[] picture;
	private String pictureName;
	private String pictureExtension;
	private String createUser;
	private Date createDt;
	private String updateUser;
	private Date updateDt;
	
}
