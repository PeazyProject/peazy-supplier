package com.peazy.supplier.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Entity
@Table(name = "Auth_CustomerUser", schema = "alanlee")
@Data
public class CustomerUserEntity {

	@Id
    // @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    // @GeneratedValue(generator = "system-uuid")
	
	@Column(name = "UserUUID", unique = true, nullable = false)
	private String userUUid;
	private String userAccount;
	private String userName;
	private String userPassword;
	private String userEmail;
	private String userPhoneNumber;
	private String userStoreName;
	private String userAddress;
	private String loginErrorCnt;
	private String activatedStatus;
	private String isFinishWholesale;
	private String isPaidDeposit;
	private Date LastLoginDt;
	private String createUser;
	private Date createDt;
	private String updateUser;
	private Date updateDt;
	
}
