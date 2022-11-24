package com.peazy.supplier.model.dto;

import java.util.Date;

public interface CheckOrderItemDto {
    String getProductSeqNo();
	String getProductName();
	String getSnCode();
	String getCost();
	String getCategory();
	String getSku();
	Date getCreateDt();
	String getProductStatus();
	int getProductOrderedCnt();
}
