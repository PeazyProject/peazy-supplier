package com.peazy.supplier.model.dto;

public interface QueryUserDto {
	String getUUID();
	String getLoginAccount();
	String getUserName();
	String getEmail();
	String getStatus();
	String getUserType();
	Integer getIsActivated();
	String getEmployCode();
}
