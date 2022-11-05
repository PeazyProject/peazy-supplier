package com.peazy.supplier.service.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.peazy.supplier.model.bean.BlobDocumentBean;
import com.peazy.supplier.model.response.QueryProductResponse;

public interface ProductService {
	QueryProductResponse queryProduct() throws JsonProcessingException;

	BlobDocumentBean getImgUrl(String snCode) throws JsonProcessingException;

}
