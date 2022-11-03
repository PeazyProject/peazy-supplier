package com.peazy.supplier.service.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.peazy.supplier.model.response.QueryProductResponse;

public interface ProductService {
	public QueryProductResponse queryProduct() throws JsonProcessingException;
}
