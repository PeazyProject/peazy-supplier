package com.peazy.supplier.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.peazy.supplier.model.bean.BlobDocumentBean;
import com.peazy.supplier.model.bean.QueryProductBean;
import com.peazy.supplier.model.dto.GetProductByFilterDto;
import com.peazy.supplier.model.entity.CommonPictureEntity;
import com.peazy.supplier.model.request.QueryProductRequest;
import com.peazy.supplier.model.response.QueryProductResponse;
import com.peazy.supplier.repository.CommonPictureRepository;
import com.peazy.supplier.repository.SupplierProductRepository;
import com.peazy.supplier.service.interfaces.ProductService;


@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private SupplierProductRepository supplierProductRepository;

	@Autowired
	private CommonPictureRepository commonPictureRepository;

	@Override
	public QueryProductResponse queryProduct(QueryProductRequest queryProductRequest) throws JsonProcessingException {

		if (CollectionUtils.isEmpty(queryProductRequest.getSkuList())) {
			queryProductRequest.setSkuList(null);
		}

		if (CollectionUtils.isEmpty(queryProductRequest.getInStockList())) {
			queryProductRequest.setInStockList(null);
		}

		List<GetProductByFilterDto> supplierProductEntity = supplierProductRepository.queryProduct(
			queryProductRequest.getProductName(), queryProductRequest.getSkuList(), queryProductRequest.getIsAvailable());
		
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

			if (isProductInStock(queryProductRequest.getInStockList(), queryProductBean.getProductQty())) {
				queryProductList.add(queryProductBean);
			}
			

		}

		QueryProductResponse queryProductResponse = new QueryProductResponse();
		queryProductResponse.setQueryProductList(queryProductList);
		return queryProductResponse;
	}

	private boolean isProductInStock(List<String> inStockList, int productQty) {


		if (CollectionUtils.isEmpty(inStockList)) {
			return true;
		}

		if (inStockList.contains("Y")) {
			if (productQty > 0) {
				return true;
			}
		}

		if (inStockList.contains("N")) {
			if (productQty == 0) {
				return true;
			}
		}

		return false;
	}

	@Override
	public BlobDocumentBean getImgUrl(String snCode) throws JsonProcessingException {
		Optional<CommonPictureEntity> commonPicOptional = commonPictureRepository.findById(snCode);
		if (commonPicOptional.isPresent()) {
			BlobDocumentBean blobDocumentBean = new BlobDocumentBean();
			blobDocumentBean.setName(commonPicOptional.get().getPictureName() + commonPicOptional.get().getPictureExtension());
			blobDocumentBean.setContent(commonPicOptional.get().getPicture());
			return blobDocumentBean;
		}
		return null;
	}

}
