package com.peazy.supplier.model.dto;

import java.util.Date;

public interface GetProductByFilterDto  {
	String getSeqNo();
	String getProductName();
	String getSnCode();
	int getPrice();
	String getCategory();
	String getSku();
	Date getCreateDt();
	String getProductStatus();
	int getProductQty();
}
