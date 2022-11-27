package com.peazy.supplier.model.dto;

import java.math.BigDecimal;
import java.util.Date;

public interface GetProductBySeqNoDto  {
	String getProductSeqNo();
	String getProductName();
	String getSnCode();
	BigDecimal getCost();
	BigDecimal getPrice();
	String getCategory();
	String getMpn();
	String getSku();
	String getProductDesc();
	Date getCreateDt();
	String getProductStatus();
	int getProductQty();
}
