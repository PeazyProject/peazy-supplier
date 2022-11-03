package com.peazy.supplier.service.Impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.peazy.supplier.model.bean.QueryProductBean;
import com.peazy.supplier.model.dto.GetProductByFilterDto;
import com.peazy.supplier.model.response.QueryProductResponse;
import com.peazy.supplier.repository.SupplierProductRepository;
import com.peazy.supplier.service.interfaces.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private SupplierProductRepository supplierProductRepository;

	@Override
	public QueryProductResponse queryProduct() throws JsonProcessingException {
		List<GetProductByFilterDto> supplierProductEntity = supplierProductRepository.queryProduct();

		List<QueryProductBean> queryProductList = new ArrayList<>();
		for (GetProductByFilterDto getProductByFilterDto : supplierProductEntity) {
			QueryProductBean queryProductBean = new QueryProductBean();
			queryProductBean.setProductName(getProductByFilterDto.getProductName());
			queryProductBean.setSnCode(getProductByFilterDto.getSnCode());
			queryProductBean.setPrice(getProductByFilterDto.getPrice());
			queryProductBean.setCategory(getProductByFilterDto.getCategory());
			queryProductBean.setSku(getProductByFilterDto.getSku());
			queryProductBean.setCreateDt(getProductByFilterDto.getCreateDt());
			queryProductBean.setProductStatus(getProductByFilterDto.getProductStatus());
			queryProductBean.setProductQty(getProductByFilterDto.getProductQty());
			queryProductList.add(queryProductBean);
		}

		QueryProductResponse queryProductResponse = new QueryProductResponse();
		queryProductResponse.setQueryProductList(queryProductList);
		return queryProductResponse;
	}

}
