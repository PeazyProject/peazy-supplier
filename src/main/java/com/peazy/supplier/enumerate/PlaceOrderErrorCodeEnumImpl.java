package com.peazy.supplier.enumerate;

public enum PlaceOrderErrorCodeEnumImpl implements ErrorCodeEunm {
	ORDER_PRODUCTS_FAIL("0001");

	private String code;

	private PlaceOrderErrorCodeEnumImpl(String errorCode) {
		this.code = errorCode;
	}

	@Override
	public String getCategory() {
		return "PlaceOrder";
	}

	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	public String getLang() {
		// TODO Auto-generated method stub
		return null;
	}

}
