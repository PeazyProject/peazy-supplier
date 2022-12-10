package com.peazy.supplier.service.interfaces;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.peazy.supplier.model.bean.BlobDocumentBean;
import com.peazy.supplier.model.bean.DropDownBean;
import com.peazy.supplier.model.request.QueryProductRequest;
import com.peazy.supplier.model.response.QueryProductBySeqNoParam;
import com.peazy.supplier.model.response.QueryProductResponse;

public interface ProductService {
	QueryProductResponse queryProduct(QueryProductRequest queryProductRequest) throws JsonProcessingException;

	BlobDocumentBean getImgUrl(String snCode) throws JsonProcessingException;

	List<DropDownBean> getProductSizeOption() throws JsonProcessingException;

	List<DropDownBean> getProductColorOption() throws JsonProcessingException;

	List<DropDownBean> getProductCategoryOption() throws JsonProcessingException;

	List<DropDownBean> getProductVendorOption() throws JsonProcessingException;

	QueryProductBySeqNoParam queryProductBySeqNo(Long seqNo) throws JsonProcessingException;

	void editProduct(QueryProductBySeqNoParam queryProductBySeqNoParam, boolean isNeedUpdatePic);

}
