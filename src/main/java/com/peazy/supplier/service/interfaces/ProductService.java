package com.peazy.supplier.service.interfaces;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.peazy.supplier.model.bean.BlobDocumentBean;
import com.peazy.supplier.model.request.QueryProductRequest;
import com.peazy.supplier.model.response.QueryCheckOrderResponse;
import com.peazy.supplier.model.response.QueryProductBySeqNoResponse;
import com.peazy.supplier.model.response.QueryProductResponse;

public interface ProductService {
	QueryProductResponse queryProduct(QueryProductRequest queryProductRequest) throws JsonProcessingException;

	BlobDocumentBean getImgUrl(String snCode) throws JsonProcessingException;

	List<String> getProductSizeOption() throws JsonProcessingException;

	List<String> getProductColorOption() throws JsonProcessingException;

	List<String> getProductCategoryOption() throws JsonProcessingException;

	QueryProductBySeqNoResponse queryProductBySeqNo(String seqNo) throws JsonProcessingException;

	QueryCheckOrderResponse queryCheckOrder() throws JsonProcessingException;
}
